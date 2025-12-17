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
package dita.recall24.dto.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.collections._Multimaps;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.experimental.UtilityClass;

import dita.commons.types.Message;
import dita.commons.types.Sex;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Food;
import dita.recall24.dto.Record24.FryingFat;
import dita.recall24.dto.Respondent24;
import dita.recall24.dto.RespondentSupplementaryData24;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
public class Recall24DtoUtils {

    // -- WRAP

    public TreeNode<RecallNode24> wrapAsTreeNode(
            final @NonNull FactoryService factoryService,
            final @NonNull InterviewSet24 interviewSet24) {
        return factoryService.treeNode((RecallNode24)interviewSet24);
    }

    // -- DATA JOINING

    private record RespondentJoiner(
            String alias,
            LocalDate dateOfBirth,
            Sex sex) {
        RespondentJoiner(final Respondent24 respondent) {
            this(respondent.alias(),
                    respondent.dateOfBirth(),
                    respondent.sex());
        }
        Respondent24 createRespondent(final Can<Interview24> interviews, final Consumer<Message> messageConsumer) {
            // check data consistency pre merge
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
            });
            return new Respondent24(alias, dateOfBirth, sex, interviews); // has side effects on the passed in interviews
        }
    }

    /**
     * Returns a joined model of the models passed in.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public InterviewSet24 join(
            final @Nullable List<Interview24> interviews,
            final @Nullable Consumer<Message> messageConsumer) {

        if(interviews==null
            || interviews.isEmpty()) return InterviewSet24.empty();

        var messageConsumerOrFallback = Optional.ofNullable(messageConsumer)
                .orElseGet(Message::consumerWritingToSyserr);

        var interviewsByRespondentAlias = _Multimaps.<String, Interview24>newListMultimap();
        interviews.forEach(interview->
            interviewsByRespondentAlias
                    .putElement(interview.parentRespondent().alias(), interview));

        final Can<Respondent24> respondents = interviewsByRespondentAlias.entrySet()
            .stream()
            .map(entry->{
                var interviewList = Can.ofCollection(entry.getValue());
                var respondent = new RespondentJoiner(interviewList.getFirstElseFail().parentRespondent())
                    .createRespondent(interviewList, messageConsumerOrFallback);
                _Assert.assertEquals(respondent.alias(), entry.getKey());
                return respondent;
            })
            .collect(Can.toCan());

        return InterviewSet24.of(respondents);
    }

    // -- SAMPLE

    public InterviewSet24 interviewSetSample(final Can<MemorizedFood24> memorizedFoods) {
        var interview = interviewSample(memorizedFoods);
        var interviewSet = join(List.of(interview), null);
        return interviewSet;
    }

    public Interview24 interviewSample(final Can<MemorizedFood24> memorizedFoods) {
        var interview = new Interview24(
            LocalDate.of(2025, 01, 03),
            LocalDate.of(2025, 01, 02),
            new RespondentSupplementaryData24(
                    ObjectRef.empty(), "01", "02", new BigDecimal("175"), new BigDecimal("75"),
                    LocalTime.of(6, 30),
                    LocalTime.of(7, 45)),
            Can.of(new Meal24(LocalTime.of(8,0), "at.gd/2.0:fco/03", "at.gd/2.0:poc/02", memorizedFoods)));
        return interview;
    }

    public Respondent24 respondentSample(final Can<Interview24> interviews) {
        return new Respondent24("SAMPLE_MALE", LocalDate.of(1975, 04, 03), Sex.MALE, interviews);
    }

    // -- TRANSFORM

    public UnaryOperator<InterviewSet24> correctRespondents(final List<Correction24> corrections) {
        return corrections!=null
                && corrections.size()>0
                ? toOperator(corrections.getFirst().asRespondentTransformer()) //TODO assumes all respondent corrections are in first step
                : UnaryOperator.identity();
    }

    public UnaryOperator<InterviewSet24> toOperator(
            final RecallNode24.Transfomer transformer) {
        return set->transform(set, transformer).orElse(null);
    }

    /**
     * Returns a new tree with the transformed nodes.
     * @param transformer - transforms fields only (leave parent child relations untouched)
     */
    public Optional<InterviewSet24> transform(
            final InterviewSet24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) {
            transformer.finish();
            return Optional.empty();
        }
        var transformedRespondents = input.respondents().stream()
            .flatMap(resp->transform(resp, transformer))
            .collect(Can.toCan());
        var prepared = new InterviewSet24(transformedRespondents, input.annotations());
        var transformed = transformer.transform(prepared);
        transformer.finish();
        return Optional.ofNullable(transformed);
    }

    // -- MISC

    public boolean isTopLevelFood(final Food food) {
        return food.parentMemorizedFood()
            .topLevelRecords()
            .contains(food);
    }

    /**
     * frying fat - if any - is always the next sibling node of a food or ingredient node
     *
     * <p>variant to use for top level food
     */
    public Optional<FryingFat> fryingFat(final Food food) {
        return fryingFat(null, food);
    }

    /**
     * frying fat - if any - is always the next sibling node of a food or ingredient node
     *
     * <p>if composite is present, food must be and ingredient of that composite,
     * otherwise the food is considered top levelS
     */
    public Optional<FryingFat> fryingFat(final @Nullable Composite composite, final Food food) {
        var siblings = composite!=null
            ? composite.subRecords()
            : food.parentMemorizedFood().topLevelRecords();
        var siblingIndex = siblings.indexOf(food);
        if(siblingIndex==-1) {
            if(composite!=null)
                // composite is not the parent of food
                throw _Exceptions.illegalArgument("given composite %s is not the parent of given food %s",
                    composite.name(), food.name());
            else
                throw _Exceptions.illegalArgument("given food %s is not a top level record",
                    food.name());
        }
        var fryingFat = siblings.get(siblingIndex + 1)
            .filter(FryingFat.class::isInstance)
            .map(FryingFat.class::cast);
        return fryingFat;
    }

    public Record24 recordForFryingFat(final @Nullable Composite composite, final FryingFat fryingFat) {
        var siblings = composite!=null
            ? composite.subRecords()
            : fryingFat.parentMemorizedFood().topLevelRecords();
        var siblingIndex = siblings.indexOf(fryingFat);
        if(siblingIndex==-1) {
            if(composite!=null)
                // composite is not the parent of food
                throw _Exceptions.illegalArgument("given composite %s is not the parent of given fryingFat %s",
                    composite.name(), fryingFat.name());
            else
                throw _Exceptions.illegalArgument("given fryingFat %s is not a top level record",
                    fryingFat.name());
        }
        return siblings.getElseFail(siblingIndex - 1);
    }

    // -- HELPER

    private Stream<Respondent24> transform(
            final Respondent24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        var transformedInterviews = input.interviews().stream()
                .flatMap(intv->transform(intv, transformer))
                .toList();
        var builder = input.asBuilder();
        builder.interviews().clear();
        builder.interviews().addAll(transformedInterviews);
        return Stream.of(transformer.transform(builder.build()));
    }
    private Stream<Interview24> transform(
            final Interview24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        var transformedSubNodes = input.meals().stream()
                .flatMap(meal->transform(meal, transformer))
                .toList();
        var builder = (Interview24.Builder)input.asBuilder();
        builder.meals().clear();
        builder.meals().addAll(transformedSubNodes);
        return Stream.of(transformer.transform(builder.build()));
    }
    private Stream<Meal24> transform(
            final Meal24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        var transformedSubNodes = input.memorizedFood().stream()
                .flatMap(subNode->transform(subNode, transformer))
                .toList();
        var builder = (Meal24.Builder)input.asBuilder();
        builder.memorizedFood().clear();
        builder.memorizedFood().addAll(transformedSubNodes);
        return Stream.of(transformer.transform(builder.build()));
    }
    private Stream<MemorizedFood24> transform(
            final MemorizedFood24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        var transformedSubNodes = input.topLevelRecords().stream()
                .flatMap(subNode->transform(subNode, transformer))
                .toList();
        var builder = (MemorizedFood24.Builder)input.asBuilder();
        builder.topLevelRecords().clear();
        builder.topLevelRecords().addAll(transformedSubNodes);
        return Stream.of(transformer.transform(builder.build()));
    }
    private Stream<Record24> transform(
            final Record24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        // not yet supporting transformation of sub records, this is delegated to the transformer
        var output = transformer.transform(input);
        return output!=null
                ? Stream.of(output)
                : Stream.empty();
    }

}
