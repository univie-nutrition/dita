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
import org.apache.causeway.commons.graph.GraphUtils;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.collections._Multimaps;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import dita.commons.types.Message;
import dita.commons.types.Sex;
import dita.recall24.api.Correction24;
import dita.recall24.api.Interview24;
import dita.recall24.api.InterviewSet24;
import dita.recall24.api.RecallNode24;
import dita.recall24.api.Respondent24;
import dita.recall24.mutable.InterviewSet;
import io.github.causewaystuff.treeview.applib.factories.TreeNodeFactory;

@UtilityClass
public class Recall24ModelUtils {

    // -- WRAP

    public TreeNode<RecallNode24> wrapAsTreeNode(
            final @NonNull InterviewSet24 interviewSet24,
            final @NonNull FactoryService factoryService) {
        return TreeNodeFactory.wrap(RecallNode24.class, interviewSet24, factoryService);
    }

    // -- DATA JOINING

    /**
     * Returns a joined model of the models passed in.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public InterviewSet24.Dto join(
            final @Nullable Iterable<Interview24.Dto> iterable,
            final @Nullable Consumer<Message> messageConsumer) {

        if(iterable==null) return InterviewSet24.empty();

        record Helper(
                String alias,
                LocalDate dateOfBirth,
                Sex sex) {
            static Helper helper(final Interview24.Dto interview) {
                var respondent = interview.parentRespondent();
                return new Helper(respondent.alias(), respondent.dateOfBirth(), respondent.sex());
            }
            Respondent24.Dto createRespondent(final Can<Interview24.Dto> interviews, final Consumer<Message> messageConsumer) {
                var respondent = new Respondent24.Dto(alias, dateOfBirth, sex, interviews);
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

        var interviewsByRespondentAlias = _Multimaps.<String, Interview24.Dto>newListMultimap();
        iterable.forEach(interview->
            interviewsByRespondentAlias
                    .putElement(interview.parentRespondent().alias(), interview));

        final Can<Respondent24.Dto> respondents = interviewsByRespondentAlias.entrySet()
            .stream()
            .map(entry->{
                var interviews = entry.getValue();
                var helper = Helper.helper(interviews.get(0));
                var respondent = helper.createRespondent(Can.ofCollection(interviews), messageConsumerOrFallback);
                return respondent;
            })
            .collect(Can.toCan());

        return InterviewSet24.Dto.of(respondents).normalized();
    }




    // -- TRANSFORM

    /**
     * Returns a new tree with the transformed nodes.
     * @param transformer - transforms fields only (leave parent child relations untouched)
     * @implNote we convert the immutable {@link InterviewSet} into a mutable {@link InterviewSet},
     *      then transform the mutable nodes and then convert back to immutable {@link InterviewSet}
     */
    public UnaryOperator<InterviewSet24.Dto> transform(
            final @NonNull RecallNode24.Transfomer transformer) {
        return (final InterviewSet24.Dto interviewSet) -> {

            var gBuilder = GraphUtils.GraphBuilder.directed(RecallNode24.class);
            gBuilder.addNode(interviewSet);

            interviewSet.respondents().forEach(resp->{
                gBuilder.addNode(resp);
                final int respIndex = gBuilder.nodeCount()-1;
                gBuilder.addEdge(0, respIndex);

                resp.interviews().forEach(intv->{
                    gBuilder.addNode(intv);
                    final int intvIndex = gBuilder.nodeCount()-1;
                    gBuilder.addEdge(respIndex, intvIndex);

                    intv.meals().forEach(meal->{
                        gBuilder.addNode(meal);
                        final int mealIndex = gBuilder.nodeCount()-1;
                        gBuilder.addEdge(intvIndex, mealIndex);

                        meal.memorizedFood().forEach(mem->{
                            gBuilder.addNode(mem);
                            final int memIndex = gBuilder.nodeCount()-1;
                            gBuilder.addEdge(mealIndex, memIndex);

                            mem.topLevelRecords().forEach(topLevelRec->{
                                topLevelRec.visitDepthFirst(rec->{

                                    gBuilder.addNode(rec);
                                    final int recIndex = gBuilder.nodeCount()-1;
                                    gBuilder.addEdge(memIndex, recIndex); //FIXME[23]

                                });
                            });
                        });
                    });
                });
            });

            var graph = gBuilder.build();

            //var builderGraph = new GraphUtils.Graph(graph.kernel(), graph.nodes().map(x->x.builder()));

            var builderNodes = graph.nodes().map(node->node.builder());
            builderNodes.forEach(transformer);

            var setBuilder = _Casts.<InterviewSet24.Builder>uncheckedCast(builderNodes.getElseFail(0));
            var respondentBuilders = graph.kernel().streamNeighbors(0)
                    .mapToObj(builderNodes::getElseFail)
                    .collect(Can.toCan());

            respondentBuilders.forEach(respBuilder->{
                setBuilder.respondents().add((Respondent24.Dto) respBuilder.build());
            });

            //var transformedNodes = builderNodes.map(node->node.build());

            var transformedInterview = (InterviewSet24.Dto) setBuilder.build();

            //FIXME[23] remove debug code
            System.err.printf("transformedInterview %s%n", transformedInterview.toYaml());

            return transformedInterview;
        };
    }

    public static UnaryOperator<InterviewSet24.Dto> correct(final @Nullable Correction24 correction24) {
        return correction24!=null
                ? transform(correction24.asTransformer())
                : UnaryOperator.identity();
    }

}
