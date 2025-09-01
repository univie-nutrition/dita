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
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
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
import dita.commons.util.JoinUtils;
import dita.commons.util.NumberUtils;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Consumption;
import dita.recall24.dto.Record24.Food;
import dita.recall24.dto.Record24.FryingFat;
import dita.recall24.dto.Record24.Product;
import io.github.causewaystuff.commons.base.types.NamedPath;

/**
 * Models interview data corrections. WIP
 */
@Slf4j
public record Correction24(
        List<RespondentCorr> respondents,
        List<FoodByNameCorr> foodByName,
        List<CompositeCorr> composites) {

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
    public record FoodByNameCorr(
        @NonNull String name,
        SemanticIdentifier sid) {
    }

    @Builder
    public record CompositeCorr(
            @NonNull Coordinates coordinates,
            @Nullable String rename,
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
                @Nullable String name,
                NamedPath source) implements Comparable<Coordinates>{

            private static Comparator<Coordinates> COMPARATOR = Comparator.comparing(Coordinates::sid)
                    .thenComparing(Coordinates::respondentId)
                    .thenComparing(Coordinates::interviewOrdinal)
                    .thenComparing(Coordinates::mealHourOfDay)
                    .thenComparing((a, b)->_Strings.compareNullsFirst(a.name(), b.name()))
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
                    composite.name(),
                    iv.dataSource().orElseThrow());
            }
            public static Coordinates ofRelaxed(final Composite composite) {
                var mem = composite.parentMemorizedFood();
                var meal = mem.parentMeal();
                var iv = meal.parentInterview();
                var resp = iv.parentRespondent();
                return new CompositeCorr.Coordinates(
                    composite.sid(),
                    resp.alias(),
                    iv.interviewOrdinal(),
                    meal.hourOfDay(),
                    null, // is used as a wildcard
                    iv.dataSource().orElseThrow());
            }
        }
        public record Addition(
            @NonNull SemanticIdentifier sid,
            @NonNull BigDecimal amountGrams,
            @NonNull SemanticIdentifierSet facets) {
            // canonical constructor
            public Addition(
                @NonNull final SemanticIdentifier sid,
                @NonNull final BigDecimal amountGrams,
                @Nullable final SemanticIdentifierSet facets) {
                this.sid = sid;
                this.amountGrams = amountGrams;
                this.facets = facets!=null ? facets : SemanticIdentifierSet.empty();
            }
        }
        public record Deletion(
            @NonNull SemanticIdentifier sid) {
        }
    }

    public static Correction24 empty() {
        return new Correction24(List.of(), List.of(), List.of());
    }

    @JsonIgnore
    public boolean isEmpty() {
        return respondents().isEmpty()
                && foodByName().isEmpty()
                && composites().isEmpty();
    }

    // -- SERIALIZATION

    public String toYaml() {
        return YamlUtils.toStringUtf8(this, FormatUtils.yamlOptions());
    }

    public static Try<Correction24> tryFromYaml(final String yaml) {
        return YamlUtils.tryRead(Correction24.class, yaml, FormatUtils.yamlOptions())
                .mapSuccessAsNullable(corr->corr!=null
                    ? corr.sorted()
                    : null);
    }

    private Correction24 sorted() {
        return join(Correction24.empty());
    }

    // -- JOIN

    public Correction24 join(final Correction24 other) {
        if(other.isEmpty()) return this;
        if(this.isEmpty()) return other;

        return new Correction24(
                JoinUtils.joinAndSortFailOnDuplicates(this.respondents(), other.respondents(), Comparator.comparing(RespondentCorr::alias)),
                JoinUtils.joinAndSortFailOnDuplicates(this.foodByName(), other.foodByName(), Comparator.comparing(FoodByNameCorr::name)),
                JoinUtils.joinAndSortFailOnDuplicates(this.composites(), other.composites(), Comparator.comparing(CompositeCorr::coordinates)));
    }

    // -- CORRECTION APPLICATION

    public RecallNode24.Transfomer asRespondentTransformer() {
        return new RespondentCorrectionTransformer(respondents);
    }

    public RecallNode24.Transfomer asFoodByNameTransformer() {
        return new FoodByNameCorrectionTransformer(foodByName);
    }

    public CompositeCorrectionAmbiguityChecker asCompositeAmbiguityCheckingTransformer() {
        return new CompositeCorrectionAmbiguityChecker(composites);
    }

    public RecallNode24.Transfomer asCompositeTransformer(final Function<SemanticIdentifier, String> nameBySidLookup) {
        return new CompositeCorrectionTransformer(composites, nameBySidLookup);
    }

    // -- HELPER

    private record RespondentCorrectionTransformer(
            /**
             * Aliases that are marked for withdrawal.
             */
            Set<String> withdrawnAliases,
            Map<String, RespondentCorr> respCorrByAlias)
        implements RecallNode24.Transfomer {

        RespondentCorrectionTransformer(final List<RespondentCorr> respondentCorrs){
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

    private record FoodByNameCorrectionTransformer(
            Map<String, FoodByNameCorr> foodByNameCorrs)
        implements RecallNode24.Transfomer {

        public FoodByNameCorrectionTransformer(final List<FoodByNameCorr> foodByName) {
            this(
                foodByName
                    .stream()
                    .collect(Collectors.toMap(FoodByNameCorr::name, UnaryOperator.identity())));
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecallNode24> T transform(final T node) {
            return switch(node) {
                case Food food -> {
                    var foodByNameCorr = foodByNameCorrs.get(food.name());
                    if(foodByNameCorr==null) yield node;

                    var builder = (Food.Builder)food.asBuilder();

                    log.info("about to correct {}", foodByNameCorr);

                    builder.modifyNotes(notesModifiable->{
                        notesModifiable.add("CORR REPLACE SID: %s %s".formatted(
                                food.sid().toStringNoBox(),
                                foodByNameCorr.sid().toStringNoBox()));
                    });

                    builder.sid(foodByNameCorr.sid());

                    yield (T)builder.build();
                }
                default -> node;
            };
        }

    }

    public record CompositeCorrectionAmbiguity(
        CompositeCorr.Coordinates releaxedCoors,
        List<Record24.Composite> candidates) {

        @Override public final String toString() {
            return "%s {%s}".formatted(
                releaxedCoors.toString(),
                candidates.stream()
                    .map(composite->{
                        var trueCoors = CompositeCorr.Coordinates.of(composite);
                        return "%s(%d)".formatted(composite.name(), trueCoors.name());
                    })
                    .collect(Collectors.joining("|")));
        }
    }

    public record CompositeCorrectionAmbiguityChecker(
        Map<CompositeCorr.Coordinates, CompositeCorr> compCorrByCoors,
        List<CompositeCorrectionAmbiguity> ambiguities)
    implements RecallNode24.Transfomer {

        CompositeCorrectionAmbiguityChecker(final List<CompositeCorr> composites){
            this(
                _NullSafe.stream(composites)
                    .collect(Collectors.toMap(CompositeCorr::coordinates, UnaryOperator.identity())),
                new ArrayList<>());
        }

        @Override
        public <T extends RecallNode24> T transform(final T node) {
            if(node instanceof Composite composite) {
                Optional.ofNullable(compCorrByCoors.get(CompositeCorr.Coordinates.of(composite)))
                .orElseGet(()->{
                    var releaxedCoors = CompositeCorr.Coordinates.ofRelaxed(composite);
                    var relaxedMatch = compCorrByCoors.get(CompositeCorr.Coordinates.ofRelaxed(composite));
                    // we only allow this, if there is no ambiguity, that is the meal this composite belongs to
                    // must only have a single memorized food entry
                    if(relaxedMatch!=null) {
                        var mem = composite.parentMemorizedFood();
                        var meal = mem.parentMeal();
                        var composites = collectComposites(meal, composite.sid());
                        if(composites.size()!=1) {
                            ambiguities.add(new CompositeCorrectionAmbiguity(releaxedCoors, composites));
                        }
                    }
                    return relaxedMatch;
                });
            }
            return node;
        }

        private List<Record24.Composite> collectComposites(final Meal24 meal, final SemanticIdentifier sid) {
            return meal.memorizedFood().stream()
                .flatMap(mem->mem.topLevelRecords().stream())
                .filter(rec->rec instanceof Composite)
                .map(Composite.class::cast)
                .filter(composite->composite.sid().equals(sid))
                .toList();
        }

        public void validate() {
            _Assert.assertEquals(0, ambiguities.size(),
                ()->"memorized food ambiguity at coordintates\n"
                    + ambiguities.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
        }

    }

    private record CompositeCorrectionTransformer(
            Map<CompositeCorr.Coordinates, CompositeCorr> compCorrByCoors,
            Function<SemanticIdentifier, String> nameBySidLookup)
        implements RecallNode24.Transfomer {

        CompositeCorrectionTransformer(final List<CompositeCorr> composites, final Function<SemanticIdentifier, String> nameBySidLookup){
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
                    var compCorr = Optional.ofNullable(compCorrByCoors.get(CompositeCorr.Coordinates.of(composite)))
                        .orElseGet(()->compCorrByCoors.get(CompositeCorr.Coordinates.ofRelaxed(composite)));
                    if(compCorr==null) yield node;

                    var builder = (Composite.Builder)composite.asBuilder();

                    log.info("about to correct {}", compCorr);

                    builder.modifyNotes(notesModifiable->{
                        builder.name(new CompositeRenamer(compCorr, notesModifiable).apply(composite.name()));
                        builder.replaceSubRecords(new SubRecordDeleter(compCorr, nameBySidLookup, notesModifiable));
                        new SubRecordAdder(compCorr, nameBySidLookup, notesModifiable).addTo(builder.subRecords());
                    });

                    // re-calc so total amount consumed stays the same

                    var origAmountConsumed = composite.subRecords().stream()
                        .filter(Consumption.class::isInstance)
                        .map(Consumption.class::cast)
                        .map(Consumption::amountConsumed)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                    var newAmountConsumed = builder.subRecords().stream()
                        .filter(Consumption.class::isInstance)
                        .map(Consumption.class::cast)
                        .map(Consumption::amountConsumed)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                    if(origAmountConsumed.compareTo(newAmountConsumed)!=0) {
                        var correctionFactor = BigDecimal.valueOf(origAmountConsumed.doubleValue() / newAmountConsumed.doubleValue());
                        builder.replaceSubRecords(sr->{
                            if(sr instanceof Consumption consumption) {
                                return switch (consumption) {
                                    case Food food -> {
                                        var foodBuilder = (Food.Builder)(food.asBuilder());
                                        var amountConsumed = NumberUtils.reducedPrecision(foodBuilder.amountConsumed().multiply(correctionFactor), 2);
                                        foodBuilder.amountConsumed(amountConsumed);
                                        yield foodBuilder.build();
                                    }
                                    case FryingFat fryingFat -> {
                                        var fryingFatBuilder = (FryingFat.Builder)(fryingFat.asBuilder());
                                        var amountConsumed = NumberUtils.reducedPrecision(fryingFatBuilder.amountConsumed().multiply(correctionFactor), 2);
                                        fryingFatBuilder.amountConsumed(amountConsumed);
                                        yield fryingFatBuilder.build();
                                    }
                                    case Product product -> {
                                        var productBuilder = (Product.Builder)(product.asBuilder());
                                        var amountConsumed = NumberUtils.reducedPrecision(productBuilder.amountConsumed().multiply(correctionFactor), 2);
                                        productBuilder.amountConsumed(amountConsumed);
                                        yield productBuilder.build();
                                    }
                                };
                            }
                            return sr;
                        });
                    }

                    yield (T)builder.build();
                }

                default -> node;
            };
        }

    }

    private record CompositeRenamer(CompositeCorr compCorr, List<String> notesModifiable) implements UnaryOperator<String> {
        @Override
        public String apply(final String origName) {
            var rename = _Strings.blankToNullOrTrim(compCorr.rename());
            if(rename==null) return origName;
            notesModifiable.add("RENAME %s -> %s".formatted(origName, rename));
            return rename;
        }
    }

    /// deletes composite sub records (ingredient consumptions) based on correction
    private record SubRecordDeleter(CompositeCorr compCorr, Function<SemanticIdentifier, String> nameBySidLookup, List<String> notesModifiable) implements UnaryOperator<Record24> {

        @Override
        public Record24 apply(final Record24 orig) {
            if(compCorr.deletions()!=null) for(var deletion : compCorr.deletions()) {
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
            if(compCorr.additions()!=null) for(var addition : compCorr.additions()) {
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