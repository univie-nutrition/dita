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

import org.springframework.lang.Nullable;

import org.apache.causeway.applib.graph.tree.TreeNode;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.graph.GraphUtils;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Casts;
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

    // -- AS GRAPH

    public GraphUtils.Graph<RecallNode24> asGraph(final InterviewSet24.Dto interviewSet) {
        final var stack = new int[4];

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
                        stack[0] = memIndex;
                        mem.topLevelRecords().forEach(topLevelRec->{
                            topLevelRec.visitDepthFirst(0, (level, rec)->{
                                gBuilder.addNode(rec);
                                final int recIndex = gBuilder.nodeCount()-1;
                                stack[level + 1] = recIndex;
                                gBuilder.addEdge(stack[level], recIndex);
                            });
                        });
                    });
                });
            });
        });

        return gBuilder.build();
    }


    // -- TRANSFORM

    /**
     * Returns a new tree with the transformed nodes.
     * @param transformer - transforms fields only (leave parent child relations untouched)
     */
    public UnaryOperator<InterviewSet24.Dto> transform(
            final @NonNull RecallNode24.Transfomer transformer) {

        return (final InterviewSet24.Dto interviewSet) -> {

            var graph = asGraph(interviewSet).map(node->node.asBuilder());
            var helper = new BuilderCastHelper(graph);

            // apply the transformer
            graph.nodes().forEach(transformer);

            final int interviewSetIndex = 0; // root

            var setBuilder = helper.interviewSet(interviewSetIndex);
            graph.kernel().streamNeighbors(interviewSetIndex).forEach(respIndex->{
                var respBuilder = helper.respondent(respIndex);
                graph.kernel().streamNeighbors(respIndex).forEach(intvIndex->{
                    var intvBuilder = helper.interview(intvIndex);
                    graph.kernel().streamNeighbors(intvIndex).forEach(mealIndex->{
                        var mealBuilder = helper.meal(mealIndex);
                        graph.kernel().streamNeighbors(mealIndex).forEach(memIndex->{
                            var memBuilder = helper.memorizedFood(memIndex);
                            graph.kernel().streamNeighbors(memIndex).forEach(topLevelRecordIndex->{
                                var recBuilder0 = helper.record(topLevelRecordIndex);
                                graph.kernel().streamNeighbors(topLevelRecordIndex).forEach(rec1->{
                                    var recBuilder1 = helper.record(rec1);
                                    graph.kernel().streamNeighbors(rec1).forEach(rec2->{
                                        var recBuilder2 = helper.record(rec2);
                                        _Assert.assertEquals(0L, graph.kernel().streamNeighbors(rec2).count(), ()->
                                                "record nesting overflow");
                                        recBuilder1.subRecords().add(helper.build(recBuilder2));
                                    });
                                    recBuilder0.subRecords().add(helper.build(recBuilder1));
                                });
                                memBuilder.topLevelRecords().add(helper.build(recBuilder0));
                            });
                            mealBuilder.memorizedFood().add(helper.build(memBuilder));
                        });
                        intvBuilder.meals().add(helper.build(mealBuilder));
                    });
                    respBuilder.interviews().add(helper.build(intvBuilder));
                });
                setBuilder.respondents().add(helper.build(respBuilder));
            });

            var transformedInterviewSet = helper.build(setBuilder);
            return transformedInterviewSet;

        };
    }

    public UnaryOperator<InterviewSet24.Dto> correct(final @Nullable Correction24 correction24) {
        return correction24!=null
                ? transform(correction24.asTransformer())
                : UnaryOperator.identity();
    }

    // -- HELPER

    record BuilderCastHelper(
            GraphUtils.Graph<RecallNode24.Builder24<RecallNode24>> builderGraph) {

        public InterviewSet24.Builder interviewSet(final int nodeIndex) {
            return _Casts.uncheckedCast(builderGraph.nodes().getElseFail(nodeIndex));
        }
        public Respondent24.Builder respondent(final int nodeIndex) {
            return _Casts.uncheckedCast(builderGraph.nodes().getElseFail(nodeIndex));
        }
        public Interview24.Builder interview(final int nodeIndex) {
            return _Casts.uncheckedCast(builderGraph.nodes().getElseFail(nodeIndex));
        }
        public Meal24.Builder meal(final int nodeIndex) {
            return _Casts.uncheckedCast(builderGraph.nodes().getElseFail(nodeIndex));
        }
        public MemorizedFood24.Builder memorizedFood(final int nodeIndex) {
            return _Casts.uncheckedCast(builderGraph.nodes().getElseFail(nodeIndex));
        }
        public Record24.Builder record(final int nodeIndex) {
            return _Casts.uncheckedCast(builderGraph.nodes().getElseFail(nodeIndex));
        }

        public InterviewSet24.Dto build(final InterviewSet24.Builder builder) {
            var node = builder.build();
            return node;
        }
        public Respondent24.Dto build(final Respondent24.Builder builder) {
            var node = builder.build();
            node.interviews().forEach(child->child.parentRespondentRef().setValue(node));
            return node;
        }
        public Interview24.Dto build(final Interview24.Builder builder) {
            var node = builder.build();
            node.meals().forEach(child->child.parentInterviewRef().setValue(node));
            return node;
        }
        public Meal24.Dto build(final Meal24.Builder builder) {
            var node = builder.build();
            node.memorizedFood().forEach(child->child.parentMealRef().setValue(node));
            return node;
        }
        public MemorizedFood24.Dto build(final MemorizedFood24.Builder builder) {
            var node = builder.build();
            node.topLevelRecords().forEach(child->child.parentMemorizedFoodRef().setValue(node));
            return node;
        }
        public Record24.Dto build(final Record24.Builder builder) {
            var node = builder.build();
            switch (node) {
                case Record24.Composite composite -> {
                    composite.subRecords().stream()
                    .map(Record24.Dto.class::cast)
                    .forEach(child->child.parentMemorizedFoodRef().setValue(node.parentMemorizedFood()));
                }
                default -> {}
            }
            return node;
        }

    }

}
