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
package dita.globodiet.manager.editing.food;

import java.util.HashSet;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.LabelPosition;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.RequiredArgsConstructor;

import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodFacet;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.food_list.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodSubgroup;
import dita.globodiet.manager.editing.recipe.Recipe_addStandardUnit;
import dita.globodiet.manager.util.AsciiDocUtils;
import dita.globodiet.manager.util.GroupingUtils;

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

    protected final Food mixee;

    @MemberSupport
    public PathwayView act() {
        return new PathwayView(
                "Pathway View for Food - " + mixee.title(),
                AsciiDocUtils.yamlBlock("Facet/Descriptor Pathway", "YAML format", facetDescriptorPathwayAsYaml()),
                AsciiDocUtils.yamlBlock("Quantification Pathway", "YAML format", quantificationPathwayAsYaml()));
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
            .append(GroupingUtils.foodClassification(effectiveGrouping)
                    .fold(FoodGroup::title, FoodSubgroup::title))
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

    private String quantificationPathwayAsYaml() {
        var yaml = new StringBuilder();
        yaml.append("Food: " + mixee.title()).append("\n");

        var effectiveGrouping = factoryService.mixin(Food_effectiveGroupingUsedForQuantificationPathway.class, mixee).prop();
        if(effectiveGrouping==null) {
            yaml.append("effectiveGrouping: not found").append("\n");
            return yaml.toString();
        }
        yaml.append("effectiveGrouping: ")
        .append(GroupingUtils.foodClassification(effectiveGrouping)
                .fold(FoodGroup::title, FoodSubgroup::title))
        .append("\n");



        return yaml.toString();
    }


}
