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

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.collections._Multimaps;

import lombok.NonNull;
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
import dita.recall24.dto.Respondent24;
import io.github.causewaystuff.treeview.applib.factories.TreeNodeFactory;

@UtilityClass
public class Recall24DtoUtils {

    // -- WRAP

    public TreeNode<RecallNode24> wrapAsTreeNode(
            final @NonNull InterviewSet24 interviewSet24) {
        return TreeNodeFactory.wrap(RecallNode24.class, interviewSet24);
    }

    // -- DATA JOINING

    /**
     * Returns a joined model of the models passed in.
     * @param messageConsumer join-algorithm might detect data inconsistencies
     */
    public InterviewSet24 join(
            final @Nullable Iterable<Interview24> interviews,
            final @Nullable Consumer<Message> messageConsumer) {

        if(interviews==null) return InterviewSet24.empty();

        record Helper(
                String alias,
                LocalDate dateOfBirth,
                Sex sex) {
            static Helper helper(final Interview24 interview) {
                var respondent = interview.parentRespondent();
                return new Helper(respondent.alias(), respondent.dateOfBirth(), respondent.sex());
            }
            Respondent24 createRespondent(final Can<Interview24> interviews, final Consumer<Message> messageConsumer) {
                var respondent = new Respondent24(alias, dateOfBirth, sex, interviews);
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

        var interviewsByRespondentAlias = _Multimaps.<String, Interview24>newListMultimap();
        interviews.forEach(interview->
            interviewsByRespondentAlias
                    .putElement(interview.parentRespondent().alias(), interview));

        final Can<Respondent24> respondents = interviewsByRespondentAlias.entrySet()
            .stream()
            .map(entry->{
                var interviewList = entry.getValue();
                var helper = Helper.helper(interviewList.get(0));
                var respondent = helper.createRespondent(Can.ofCollection(interviewList), messageConsumerOrFallback);
                return respondent;
            })
            .collect(Can.toCan());

        return InterviewSet24.normalized(respondents);
    }

    public UnaryOperator<InterviewSet24> correct(final @Nullable Correction24 correction24) {
        return correction24!=null
                ? toOperator(correction24.asTransformer())
                : UnaryOperator.identity();
    }

    // -- TRANSFORM

    public UnaryOperator<InterviewSet24> toOperator(
            final RecallNode24.Transfomer transformer) {
        return input->transform(input, transformer).orElse(null);
    }

    /**
     * Returns a new tree with the transformed nodes.
     * @param transformer - transforms fields only (leave parent child relations untouched)
     */
    public Optional<InterviewSet24> transform(
            final InterviewSet24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Optional.empty();
        var transformedSubNodes = input.respondents().stream()
            .flatMap(subNode->transform(subNode, transformer))
            .collect(Can.toCan());
        var prepared = new InterviewSet24(transformedSubNodes, input.annotations());
        var transformed = transformer.transform(prepared);
        return Optional.ofNullable(transformed);
    }

    // -- HELPER

    private Stream<Respondent24> transform(
            final Respondent24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        var transformedSubNodes = input.interviews().stream()
                .flatMap(subNode->transform(subNode, transformer))
                .toList();
        var builder = (Respondent24.Builder)input.asBuilder();
        builder.interviews().clear();
        builder.interviews().addAll(transformedSubNodes);
        return Stream.of(transformer.transform(builder.build()));
    }
    private Stream<Interview24> transform(
            final Interview24 input,
            final RecallNode24.Transfomer transformer) {
        if(transformer.filter(input)==false) return Stream.empty();
        var transformedSubNodes = input.meals().stream()
                .flatMap(subNode->transform(subNode, transformer))
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
