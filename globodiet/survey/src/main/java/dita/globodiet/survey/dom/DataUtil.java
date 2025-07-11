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
package dita.globodiet.survey.dom;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.measure.MetricPrefix;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodComponent;
import dita.commons.food.composition.FoodComponentCatalog;
import dita.commons.sid.SemanticIdentifier;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Respondent24;
import io.github.causewaystuff.commons.base.listing.Listing.ListingHandler;

@UtilityClass
class DataUtil {

    // -- CORRECTION

    Correction24 correction(final String correctionYaml) {
        var yaml = _Strings.blankToNullOrTrim(correctionYaml);
        return StringUtils.hasLength(yaml)
                ? Correction24.tryFromYaml(yaml)
                        .valueAsNonNullElseFail()
                : Correction24.empty();
    }

    // -- FCDB

    /**
     * Stringify to a single line.
     * @see FoodComponentCatalog#destringify(String)
     */
    String stringify(final FoodComponent foodComponent) {
        var str = String.format("[%s] unit=%s%s attr=%s",
                //1
                foodComponent.componentId().toStringNoBox(),
                //2
                Optional.ofNullable(foodComponent.metricPrefix())
                    .map(MetricPrefix::getSymbol)
                    .orElse(""),
                //3
                _Strings.nullToEmpty(foodComponent.componentUnit().title()),
                //4
                Optional.ofNullable(foodComponent.attributes())
                    .map(attr->attr.fullFormat(", "))
                    .orElse(""));
        return str;
    }

    /**
     * Destringify from a single line.
     * @see FoodComponent#stringify()
     */
    Optional<FoodComponent> destringify(@NonNull final FoodComponentCatalog catalog, @Nullable final String input) {
        if(input==null) Optional.empty();
        var sid = extractKey(input);
        if(sid==null) Optional.empty();
        return Optional.of(new FoodComponent(sid, null, null, null));
    }

    FoodComponent destringifyElseFail(@NonNull final FoodComponentCatalog catalog, @Nullable final String input) {
        return destringify(catalog, input)
                .map(fc->catalog.lookupEntryElseFail(fc.componentId()))
                .orElseThrow(()->_Exceptions.illegalArgument("cannot parse FoodComponent from '%s'", input));
    }

    Can<FoodComponent> foodComponents(
            @NonNull final FoodComponentCatalog componentCatalog,
            @NonNull final ReportColumnDefinition reportColumnDefinition) {
        var listingHandler = DataUtil.listingHandlerForFoodComponents(
                str->DataUtil.destringifyElseFail(componentCatalog, str));
        var enabledFoodComponents = listingHandler.parseListing(reportColumnDefinition.getColumnListing());
        return enabledFoodComponents.streamEnabled().collect(Can.toCan());
    }

    // -- LISTINGS

    Set<String> enabledAliasesInListing(final String aliasListing) {
        return DataUtil.listingHandlerForRespondentProxy()
            .parseListing(aliasListing)
            .streamEnabled()
            .map(Respondent24::alias)
            .collect(Collectors.toSet());
    }

    ListingHandler<Respondent24> listingHandlerForRespondentProxy() {
        return listingHandlerForRespondents(alias->new Respondent24(alias, null, null, Can.empty()));
    }

    ListingHandler<Respondent24> listingHandlerForRespondents(
            final Function<String, Respondent24> factory) {
        return new ListingHandler<Respondent24>(
            Respondent24.class,
            Respondent24::alias,
            factory,
            Respondent24::alias);
    }

    ListingHandler<FoodComponent> listingHandlerForFoodComponents(
            final Function<String, FoodComponent> factory) {
        return new ListingHandler<FoodComponent>(
            FoodComponent.class,
            DataUtil::stringify,
            factory,
            FoodComponent::componentId);
    }

    // -- HELPER

    SemanticIdentifier extractKey(final String line) {
        int p = line.indexOf("]");
        return p>0
                ? SemanticIdentifier.parse(line.substring(1, p))
                : null; // simply ignore listing entry if its not a SID
    }

}
