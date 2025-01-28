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
package dita.globodiet.params.food_list.mixin;

import java.util.HashSet;
import java.util.Objects;

import jakarta.inject.Inject;

import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.LabelPosition;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Refs;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import org.jspecify.annotations.NonNull;
import lombok.RequiredArgsConstructor;

import dita.commons.util.AsciiDocUtils;
import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_descript.FoodFacet;
import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup.RawOrCookedAsConsumed;
import dita.globodiet.params.recipe_list.mixin.Recipe_addStandardUnit;
import dita.globodiet.params.services.thickness.ThicknessLookupService;
import dita.globodiet.params.util.QuantificationMethodPathwayKey;

/**
 * @see Recipe_addStandardUnit
 */
@Action
@ActionLayout(
        fieldSetId="details",
        sequence = "1",
        position = Position.PANEL,
        cssClass = "btn-success",
        cssClassFa = "solid fa-binoculars",
        describedAs = "Preview what happens during an interview when selecting this food.")
@RequiredArgsConstructor
public class Food_inspectPathway {

    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;
    @Inject private ThicknessLookupService thicknessLookupService;

    private final static String PHYSICAL_STATE_FACET_CODE = "02"; //TODO convert to configuration option

    protected final Food mixee;

    @MemberSupport
    public PathwayView act(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(describedAs = "The quantification method pathway "
                    + "is potentially bound to a chosen physical-state facet/descriptor.\n\n"
                    + "Choices provided are those that are effective for the current food.")
            final FoodDescriptor physicalState,
            @Parameter
            @ParameterLayout(describedAs = "The quantification method pathway "
                    + "is potentially bound to whether the food was cooked or not.")
            final RawOrCookedAsConsumed rawOrCookedAsConsumed
            ) {

        var physicalState4DigitKey = physicalState!=null
                ? physicalState.secondaryKey().facetCode() + physicalState.secondaryKey().code()
                : null;

        var constraints = String.format("Constraints: physicalState=%s, consumed=%s",
                physicalState!=null
                    ? physicalState.getName()
                    : "none",
                rawOrCookedAsConsumed);

        return new PathwayView(
                "Pathway View for Food - " + mixee.title(),
                AsciiDocUtils.yamlBlock("Facet/Descriptor Pathway", "Constraints: none", facetDescriptorPathwayAsYaml()),
                AsciiDocUtils.yamlBlock("Quantification Pathway", constraints, quantificationPathwayAsYaml(
                        physicalState4DigitKey,
                        rawOrCookedAsConsumed)));
    }

    @MemberSupport
    private Can<FoodDescriptor> choicesPhysicalState() {
        var effectiveFoodDescriptors = Can.ofCollection(factoryService.mixin(Food_effectiveFoodDescriptors.class, mixee).coll());
        return effectiveFoodDescriptors.filter(fd->PHYSICAL_STATE_FACET_CODE.equals(fd.getFacetCode()));
    }

    public static record PathwayView(
        String titleString,
        @PropertyLayout(labelPosition = LabelPosition.NONE) AsciiDoc facetDescriptorPathway,
        @PropertyLayout(labelPosition = LabelPosition.NONE) AsciiDoc quantificationPathway) {
        @ObjectSupport String title() {
            return titleString();
        }
    }

    // -- HELPER

    private String facetDescriptorPathwayAsYaml() {
        var yaml = new StringBuilder();
        yaml.append("food: " + mixee.title()).append("\n");

        var effectiveGrouping = factoryService.mixin(Food_effectiveGroupingUsedForFacetDescriptorPathway.class, mixee).prop();
        if(effectiveGrouping==null) {
            yaml.append("effectiveGrouping: not found").append("\n");
            return yaml.toString();
        }
        yaml.append("effectiveGrouping: ")
            .append(effectiveGrouping.title())
            .append("\n");

        var effectiveFoodDescriptors = factoryService.mixin(Food_effectiveFoodDescriptors.class, mixee).coll();
        var facetsSeenBefore = new HashSet<String>();
        effectiveFoodDescriptors.forEach(fd->{
            if(facetsSeenBefore.add(fd.getFacetCode())) {
                var facet = foreignKeyLookupService.unique(new FoodFacet.SecondaryKey(fd.getFacetCode()));
                yaml.append(" -facet: ").append(facet.title()).append("\n");
            }
            yaml.append("   -descriptor: ").append(fd.title()).append("\n");
        });
        return yaml.toString();
    }

    private String quantificationPathwayAsYaml(
            @Nullable final String physicalState4DigitKey,
            final RawOrCookedAsConsumed rawOrCookedAsConsumed) {
        var yaml = new StringBuilder();
        yaml.append("Food: " + mixee.title()).append("\n");

        var effectiveGrouping = factoryService.mixin(Food_effectiveGroupingUsedForQuantificationPathway.class, mixee).prop();
        if(effectiveGrouping==null) {
            yaml.append("effectiveGrouping: not found").append("\n");
            return yaml.toString();
        }
        yaml.append("effectiveGrouping: ")
        .append(effectiveGrouping.title())
        .append("\n");

        var hasAnyShapes = _Refs.booleanRef(false);

        var effectiveQuantificationMethodPathways = factoryService.mixin(Food_effectiveQuantificationMethodPathways.class, mixee).coll();
        effectiveQuantificationMethodPathways.stream()
        .filter(qmPathway->matches(rawOrCookedAsConsumed, qmPathway))
        .filter(qmPathway->matches(physicalState4DigitKey, qmPathway))
        .sorted((a, b)->{
            var keyA = QuantificationMethodPathwayKey.valueOf(a);
            var keyB = QuantificationMethodPathwayKey.valueOf(b);
            final int c = Integer.compare(
                    keyA.quantificationMethod().ordinal(),
                    keyB.quantificationMethod().ordinal());
            if(c!=0) return c;
            return _Strings.compareNullsLast(keyA.photoOrShapeCode(), keyB.photoOrShapeCode());
        })
        .forEach(qmPathway->{
            yaml.append("   -quantificationMethod: ").append(qmPathway.getQuantificationMethod()).append("\n");
            yaml.append("    comment: ").append(qmPathway.getComment()).append("\n");
            yaml.append("    physicalStateFacetDescriptor: ").append(qmPathway.getPhysicalStateFacetDescriptorLookupKey()).append("\n");
            yaml.append("    rawOrCookedAsConsumed: ").append(qmPathway.getRawOrCookedAsConsumed()).append("\n");
            var qmpKey = QuantificationMethodPathwayKey.valueOf(qmPathway);
            if(qmpKey.quantificationMethod().isPhoto()) {
                yaml.append("    photoCode: ").append(qmPathway.getPhotoOrShapeCode()).append("\n");
            } else if(qmpKey.quantificationMethod().isShape()) {
                yaml.append("    shapeCode: ").append(qmPathway.getPhotoOrShapeCode()).append("\n");
                hasAnyShapes.setValue(true);
            }
        });

        if(hasAnyShapes.isTrue()) {
            yaml.append("    thicknessOptionsForShape:\n");
            //TODO which group to use (food's grouping or effective grouping for QMP)?
            thicknessLookupService.lookupThicknessForFood(effectiveGrouping.groupingKey())
            .forEach(thickness->{
                yaml.append("     -thickness: ").append(thickness.getCode()).append("\n");
                yaml.append("      cm: ").append(String.format("%.3f", thickness.getThickness())).append("\n");
                yaml.append("      comment: ").append(thickness.getComment()).append("\n");
            });
        }

        return yaml.toString();
    }

    private boolean matches(
            final @NonNull RawOrCookedAsConsumed rawOrCookedAsConsumed,
            final QuantificationMethodPathwayForFoodGroup qmPathway) {
        var constraint = qmPathway.getRawOrCookedAsConsumed();
        if(constraint==null) {
            return true; // if no constraint is set, we always match
        }
        return Objects.equals(constraint, rawOrCookedAsConsumed);
    }

    private boolean matches(@Nullable final String physicalState4DigitKey, final QuantificationMethodPathwayForFoodGroup qmPathway) {
        var physicalStateFacetDescriptorLookupKey = qmPathway.getPhysicalStateFacetDescriptorLookupKey();
        if(_Strings.isEmpty(physicalStateFacetDescriptorLookupKey)) {
            return true; // if no constraint is set, we always match
        }
        if(physicalState4DigitKey==null) {
            return false; // can never satisfy non-empty constraint
        }
        return physicalStateFacetDescriptorLookupKey.contains(physicalState4DigitKey);
    }

}
