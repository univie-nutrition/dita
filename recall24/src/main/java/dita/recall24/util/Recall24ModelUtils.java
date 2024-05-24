/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.recall24.util;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.collections._Multimaps;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import dita.commons.types.Message;
import dita.commons.types.Sex;
import dita.recall24.immutable.Ingredient;
import dita.recall24.immutable.Interview;
import dita.recall24.immutable.InterviewSet;
import dita.recall24.immutable.Meal;
import dita.recall24.immutable.MemorizedFood;
import dita.recall24.immutable.RecallNode;
import dita.recall24.immutable.Record;
import dita.recall24.immutable.Respondent;
import dita.recall24.immutable.corr.Correction24;
import io.github.causewaystuff.treeview.applib.factories.TreeNodeFactory;

@UtilityClass
public class Recall24ModelUtils {

    // -- WRAP

    public TreeNode<RecallNode> wrapAsTreeNode(
            final @NonNull InterviewSet interviewSet24,
            final @NonNull FactoryService factoryService) {
        return TreeNodeFactory.wrap(RecallNode.class, interviewSet24, factoryService);
    }

    // -- DATA JOINING

    /**
     * Returns a joined model of the models passed in.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public InterviewSet join(
            final @Nullable Iterable<Interview> iterable,
            final @Nullable Consumer<Message> messageConsumer) {

        if(iterable==null) return InterviewSet.empty();

        record Helper(
                String alias,
                LocalDate dateOfBirth,
                Sex sex) {
            static Helper helper(final Interview interview) {
                var respondent = interview.parentRespondent();
                return new Helper(respondent.alias(), respondent.dateOfBirth(), respondent.sex());
            }
            Respondent createRespondent(final Can<Interview> interviews, final Consumer<Message> messageConsumer) {
                var respondent = new Respondent(alias, dateOfBirth, sex, interviews);
                interviews.forEach(iv->{
                    _Assert.assertEquals(alias, iv.parentRespondent().alias()); // unexpected
                    if(!Objects.equals(dateOfBirth, iv.parentRespondent().dateOfBirth())) {
                        messageConsumer.accept(
                                Message.error("dateOfBirth mismatch joining data for alias %s", alias));
                    }
                    if(!Objects.equals(sex, iv.parentRespondent().sex())) {
                        messageConsumer.accept(
                                Message.error("sex mismatch joining data for alias %s", alias));
                    }
                    iv.parentRespondentRef().setValue(respondent);
                });
                return respondent;
            }
        }

        var messageConsumerOrFallback = Optional.ofNullable(messageConsumer)
                .orElseGet(Message::consumerWritingToSyserr);

        var interviewsByRespondentAlias = _Multimaps.<String, Interview>newListMultimap();
        iterable.forEach(interview->
            interviewsByRespondentAlias
                    .putElement(interview.parentRespondent().alias(), interview));

        final Can<Respondent> respondents = interviewsByRespondentAlias.entrySet()
            .stream()
            .map(entry->{
                var interviews = entry.getValue();
                var helper = Helper.helper(interviews.get(0));
                var respondent = helper.createRespondent(Can.ofCollection(interviews), messageConsumerOrFallback);
                return respondent;
            })
            .collect(Can.toCan());

        return InterviewSet.of(respondents).normalized();
    }

    // -- TRANSFORM

    /**
     * Returns a new tree with the transformed nodes.
     * @param transformer - transforms fields only (leave parent child relations untouched)
     * @implNote we convert the immutable {@link InterviewSet} into a mutable {@link InterviewSet},
     *      then transform the mutable nodes and then convert back to immutable {@link InterviewSet}
     */
    public UnaryOperator<InterviewSet> transform(
            final @NonNull UnaryOperator<RecallNode> transformer) {
        return (final InterviewSet interviewSet24) -> {
            final dita.recall24.mutable.InterviewSet mutableRoot = Recall24DtoUtils.toDto(interviewSet24);
            interviewSet24.respondents().zip(mutableRoot.getRespondents(), (resp, respDto)->{
                Recall24DtoUtils.updateDtoFromModelFields(respDto,
                        (Respondent) invokeWithRuturnTypeChecked(transformer, resp));
                resp.interviews().zip(respDto.getInterviews(), (intv, intvDto)->{
                    Recall24DtoUtils.updateDtoFromModelFields(intvDto,
                            (Interview) invokeWithRuturnTypeChecked(transformer, intv));
                    intv.meals().zip(intvDto.getMeals(), (meal, mealDto)->{
                        Recall24DtoUtils.updateDtoFromModelFields(mealDto,
                                (Meal) invokeWithRuturnTypeChecked(transformer, meal));
                        meal.memorizedFood().zip(mealDto.getMemorizedFood(), (mem, memDto)->{
                            Recall24DtoUtils.updateDtoFromModelFields(memDto,
                                    (MemorizedFood) invokeWithRuturnTypeChecked(transformer, mem));
                            mem.topLevelRecords().zip(memDto.getTopLevelRecords(), (rec, recDto)->{
                                Recall24DtoUtils.updateDtoFromModelFields(recDto,
                                        (Record) invokeWithRuturnTypeChecked(transformer, rec));
                                rec.ingredients().zip(recDto.getIngredients(), (ingr, ingrDto)->{
                                    Recall24DtoUtils.updateDtoFromModelFields(ingrDto,
                                            (Ingredient) invokeWithRuturnTypeChecked(transformer, ingr));
                                });
                            });
                        });
                    });
                });
            });
            return Recall24DtoUtils.fromDto(mutableRoot);
        };
    }

    public static UnaryOperator<InterviewSet> correct(final @Nullable Correction24 correction24) {
        return correction24!=null
                ? transform(correction24.asOperator())
                : UnaryOperator.identity();
    }

    // -- HELPER

    private RecallNode invokeWithRuturnTypeChecked(
            final @NonNull UnaryOperator<RecallNode> transformer, final @NonNull RecallNode node){
        var transformedNode = transformer.apply(node);
        _Assert.assertNotNull(transformedNode);
        _Assert.assertEquals(node.getClass(), transformedNode.getClass());
        return transformedNode;
    }

}
