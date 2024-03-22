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
package dita.globodiet.manager.editing.recipe;

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

import org.causewaystuff.domsupport.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.recipe_description.RecipeFacet;
import dita.globodiet.dom.params.recipe_list.Recipe;
import dita.globodiet.manager.util.AsciiDocUtils;
import dita.globodiet.manager.util.QuantificationMethodPathwayKey;

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
        describedAs = "Preview what happens during an interview when selecting this recipe.")
@RequiredArgsConstructor
public class Recipe_inspectPathway {

    @Inject private FactoryService factoryService;
    @Inject private ForeignKeyLookupService foreignKeyLookupService;

    private final static String PHYSICAL_STATE_FACET_CODE = "02"; //TODO convert to configuration option

    protected final Recipe mixee;

    @MemberSupport
    public PathwayView act() {

        return new PathwayView(
                "Pathway View for Recipe - " + mixee.title(),
                AsciiDocUtils.yamlBlock("Facet/Descriptor Pathway", "Constraints: none", facetDescriptorPathwayAsYaml()),
                AsciiDocUtils.yamlBlock("Quantification Pathway", "Constraints: none", quantificationPathwayAsYaml()));
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
        yaml.append("recipe: " + mixee.title()).append("\n");

        var effectiveGrouping = factoryService.mixin(Recipe_effectiveGroupingUsedForFacetDescriptorPathway.class, mixee).prop();
        if(effectiveGrouping==null) {
            yaml.append("effectiveGrouping: not found").append("\n");
            return yaml.toString();
        }
        yaml.append("effectiveGrouping: ")
            .append(effectiveGrouping.title())
            .append("\n");

        var effectiveRecipeDescriptors = factoryService.mixin(Recipe_effectiveRecipeDescriptors.class, mixee).coll();
        var facetsSeenBefore = new HashSet<String>();
        effectiveRecipeDescriptors.forEach(fd->{
            if(facetsSeenBefore.add(fd.getRecipeFacetCode())) {
                var facet = foreignKeyLookupService.unique(new RecipeFacet.SecondaryKey(fd.getRecipeFacetCode()));
                yaml.append(" -facet: ").append(facet.title()).append("\n");
            }
            yaml.append("   -descriptor: ").append(fd.title()).append("\n");
        });
        return yaml.toString();
    }

    private String quantificationPathwayAsYaml() {
        var yaml = new StringBuilder();
        yaml.append("Recipe: " + mixee.title()).append("\n");

        var effectiveGrouping = factoryService.mixin(Recipe_effectiveGroupingUsedForQuantificationPathway.class, mixee).prop();
        if(effectiveGrouping==null) {
            yaml.append("effectiveGrouping: not found").append("\n");
            return yaml.toString();
        }
        yaml.append("effectiveGrouping: ")
        .append(effectiveGrouping.title())
        .append("\n");

        var effectiveQuantificationMethodPathways = factoryService.mixin(Recipe_effectiveQuantificationMethodPathways.class, mixee).coll();
        effectiveQuantificationMethodPathways.stream()
        .forEach(qmPathway->{
            yaml.append("   -quantificationMethod: ").append(qmPathway.getQuantificationMethod()).append("\n");
            yaml.append("    comment: ").append(qmPathway.getComment()).append("\n");
            var qmpKey = QuantificationMethodPathwayKey.valueOf(qmPathway);
            if(qmpKey.quantificationMethod().isPhotoOrShape()) {
                yaml.append("    photoOrShape: ").append(qmPathway.getPhotoOrShapeCode()).append("\n");
            }
        });

        return yaml.toString();
    }

}
