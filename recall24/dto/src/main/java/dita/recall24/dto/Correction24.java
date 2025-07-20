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
package dita.recall24.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.commons.util.FormatUtils;
import dita.recall24.dto.Annotated.Annotation;
import dita.recall24.dto.Record24.Composite;
import io.github.causewaystuff.commons.base.types.NamedPath;

/**
 * Models interview data corrections. WIP
 */
@Slf4j
public record Correction24(List<RespondentCorr> respondents, List<CompositeCorr> composites) {

    @Builder
    public record RespondentCorr(
            @NonNull String alias,
            /**
             * Whether respondent has requested withdrawal from study.
             */
            @Nullable Boolean withdraw,
            @Nullable String newAlias,
            @Nullable LocalDate dateOfBirth,
            @Nullable Sex sex) {
    }

    @Builder
    public record CompositeCorr(
            @NonNull Coordinates coordinates,
            @Singular @NonNull List<Addition> additions,
            @Singular @NonNull List<Deletion> deletions
            ) {
        /// Assumes that a composite consumption can be uniquely found by its {@link SemanticIdentifier} within a specific {@link Meal24}.
        /// In other words: we assume there are no duplicated composite consumptions per meal
        public record Coordinates(
                SemanticIdentifier sid,
                String respondentId,
                int interviewOrdinal,
                LocalTime mealHourOfDay,
                NamedPath source) implements Comparable<Coordinates>{

            private static Comparator<Coordinates> COMPARATOR = Comparator.comparing(Coordinates::sid)
                    .thenComparing(Coordinates::respondentId)
                    .thenComparing(Coordinates::interviewOrdinal)
                    .thenComparing(Coordinates::mealHourOfDay)
                    .thenComparing(Coordinates::source, NamedPath.comparator());

            @Override
            public int compareTo(final Coordinates other) {
                return COMPARATOR.compare(this, other);
            }

            public static Coordinates of(final Composite composite) {
                var mem = composite.parentMemorizedFood();
                var meal = mem.parentMeal();
                var iv = meal.parentInterview();
                var resp = iv.parentRespondent();
                return new CompositeCorr.Coordinates(
                        composite.sid(),
                        resp.alias(),
                        iv.interviewOrdinal(),
                        meal.hourOfDay(),
                        iv.dataSource().orElseThrow());
            }
        }
        public record Addition(SemanticIdentifier sid, BigDecimal amountGrams, SemanticIdentifierSet facets) {
        }
        public record Deletion(SemanticIdentifier sid) {
        }
    }

    public static Correction24 empty() {
        return new Correction24(List.of(), List.of());
    }

    // -- CONSTRUCTION

    public Correction24() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    private Correction24 sort() {
        respondents().sort((a, b)->a.alias().compareTo(b.alias()));
        composites().sort((a, b)->a.coordinates().compareTo(b.coordinates()));
        return this;
    }

    // -- SERIALIZATION

    public String toYaml() {
        return YamlUtils.toStringUtf8(this.sort(), FormatUtils.yamlOptions());
    }

    public static Try<Correction24> tryFromYaml(final String yaml) {
        return YamlUtils.tryRead(Correction24.class, yaml, FormatUtils.yamlOptions())
                .mapSuccessAsNullable(corr->corr!=null?corr.sort():null);
    }

    // -- CORRECTION APPLICATION

    public RecallNode24.Transfomer asRespondentTransformer() {
        return new RespondentCorrectionTranformer(respondents);
    }

    public RecallNode24.Transfomer asCompositeTransformer(final Function<SemanticIdentifier, String> nameBySidLookup) {
        return new CompositeCorrectionTranformer(composites, nameBySidLookup);
    }

    // -- HELPER

    private record RespondentCorrectionTranformer(
            /**
             * Aliases that are marked for withdrawal.
             */
            Set<String> withdrawnAliases,
            Map<String, RespondentCorr> respCorrByAlias)
        implements RecallNode24.Transfomer {

        RespondentCorrectionTranformer(final List<RespondentCorr> respondentCorrs){
            this(
                    respondentCorrs.stream()
                        .filter(corr->Boolean.TRUE.equals(corr.withdraw()))
                        .map(RespondentCorr::alias)
                        .collect(Collectors.toSet()),
                    respondentCorrs
                        .stream()
                        .collect(Collectors.toMap(RespondentCorr::alias, UnaryOperator.identity())));
        }

        @Override
        public <T extends RecallNode24> boolean filter(final T node) {
            return switch(node) {
                case Respondent24 resp -> !withdrawnAliases().contains(resp.alias());
                default -> true;
            };
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecallNode24> T transform(final T node) {
            return switch(node) {
                case Respondent24 resp -> {
                    var respCorr = respCorrByAlias.get(resp.alias());
                    if(respCorr==null) yield node;

                    var builder = resp.asBuilder();

                    log.info("about to correct {}", respCorr);

                    if(Boolean.TRUE.equals(respCorr.withdraw())) {
                        throw _Exceptions.illegalState("withdrawn respondents should already be filtered yet got %s",
                                resp.alias());
                    }

                    if(respCorr.newAlias()!=null) {
                        builder.alias(respCorr.newAlias());
                        var associatedCorr = respCorrByAlias.get(respCorr.newAlias());
                        if(associatedCorr!=null) {
                            if(associatedCorr.dateOfBirth()!=null) {
                                builder.dateOfBirth(associatedCorr.dateOfBirth());
                            }
                            if(associatedCorr.sex()!=null) {
                                builder.sex(associatedCorr.sex());
                            }
                        }
                    }
                    if(respCorr.dateOfBirth()!=null) {
                        _Assert.assertNull(respCorr.newAlias(), ()->"dateOfBirth correction not allowed on node that has newAlias");
                        builder.dateOfBirth(respCorr.dateOfBirth());
                    }
                    if(respCorr.sex()!=null) {
                        _Assert.assertNull(respCorr.newAlias(), ()->"sex correction not allowed on node that has newAlias");
                        builder.sex(respCorr.sex());
                    }
                    yield (T)builder.build();
                }
                default -> node;
            };
        }

    }

    private record CompositeCorrectionTranformer(
            Map<CompositeCorr.Coordinates, CompositeCorr> compCorrByCoors,
            Function<SemanticIdentifier, String> nameBySidLookup)
        implements RecallNode24.Transfomer {

        CompositeCorrectionTranformer(final List<CompositeCorr> composites, final Function<SemanticIdentifier, String> nameBySidLookup){
            this(
                composites
                    .stream()
                    .collect(Collectors.toMap(CompositeCorr::coordinates, UnaryOperator.identity())),
                nameBySidLookup);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecallNode24> T transform(final T node) {
            return switch(node) {
                case Composite composite -> {
                    var compCorr = compCorrByCoors.get(CompositeCorr.Coordinates.of(composite));
                    if(compCorr==null) yield node;

                    var builder = (Composite.Builder)composite.asBuilder();

                    log.info("about to correct {}", compCorr);

                    var notesModifiable = builder.annotations().stream()
                            .filter(annot->annot.key().equals(Annotated.NOTES))
                            .map(annot->(List<String>)annot.value())
                            .findFirst()
                            .map(ArrayList::new)
                            .orElseGet(ArrayList::new);

                    builder.replaceSubRecords(new SubRecordDeleter(compCorr, nameBySidLookup, notesModifiable));
                    new SubRecordAdder(compCorr, nameBySidLookup, notesModifiable).addTo(builder.subRecords());

                    if(!notesModifiable.isEmpty()) {
                        builder.addAnnotation(new Annotation(Annotated.NOTES, notesModifiable));
                    }

                    //TODO re-calc so total amount consumed stays the same

                    yield (T)builder.build();
                }

                default -> node;
            };
        }

    }

    /// deletes composite sub records (ingredient consumptions) based on correction
    private record SubRecordDeleter(CompositeCorr compCorr, Function<SemanticIdentifier, String> nameBySidLookup, List<String> notesModifiable) implements UnaryOperator<Record24> {

        @Override
        public Record24 apply(final Record24 orig) {
            for(var deletion : compCorr.deletions()) {
                if(deletion.sid().equals(orig.sid())) {
                    notesModifiable.add("CORR DELETED: %s (%s)".formatted(
                            deletion.sid().objectId(),
                            nameBySidLookup.apply(deletion.sid())));
                    return null;
                }
            }
            return orig;
        }

    }

    /// adds composite sub records (ingredient consumptions) based on correction
    private record SubRecordAdder(CompositeCorr compCorr, Function<SemanticIdentifier, String> nameBySidLookup, List<String> notesModifiable) {

        public void addTo(final List<Record24> list) {
            for(var addition : compCorr.additions()) {
                var subRecord = new Record24.Food.Builder()
                    .type(Record24.Type.FOOD)
                    .name(nameBySidLookup.apply(addition.sid()))
                    .sid(addition.sid())
                    .facetSids(addition.facets())
                    .amountConsumed(addition.amountGrams())
                    .consumptionUnit(ConsumptionUnit.GRAM)
                    .rawToCookedCoefficient(BigDecimal.ONE) //TODO provide via addition model
                    .build();

                notesModifiable.add("CORR ADDED: %s (%s)".formatted(
                        addition.sid().objectId(),
                        nameBySidLookup.apply(addition.sid())));

                list.add(subRecord);
            }
        }

    }

}