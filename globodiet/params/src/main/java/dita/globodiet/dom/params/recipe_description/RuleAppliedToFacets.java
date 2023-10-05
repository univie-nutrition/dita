/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.recipe_description;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Rule applied to facets
 */
@Named("dita.globodiet.params.recipe_description.RuleAppliedToFacets")
@DomainObject
@DomainObjectLayout(
        describedAs = "Rule applied to facets"
)
@PersistenceCapable(
        table = "R_FACETRUL"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RuleAppliedToFacets {
    /**
     * Facet where the rule must be applied.
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Facet where the rule must be applied.",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String facetWhereTheRuleMustBeApplied;

    /**
     * Rule: Facet (facet_code) will be displayed only if descriptor in facdesc
     * is previously selected by the subject according or not to a food classification (group/subgroup1/subgroup2).
     * - When a group/subgroup1/subgroup2 is specified, the rule is applied only for these 3 levels of classification (e.g. 070101 only for foods from beef classification).
     * - When a group/subgroup1 is specified, the rule is applied for all the foods attached to these 2 or 3 levels of classification (e.g. 0701 so for foods classified under 070100, 070101, 070102, 070103, 070104, 070105 & 070106).
     * - When a group is specified, the rule is applied for all the foods attached to these 1, 2 or 3 levels of classification (e.g. 06 so for foods classified under 0601, 0602, 0603, 0604, 0605,  0606 and also for 060300, 060301 & 060302).
     * - When the group/subgroup1/subgroup2 is not specified (null values), the rule is applied to all foods, whatever its classification.
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Rule: Facet (facet_code) will be displayed only if descriptor in facdesc\n"
                            + "is previously selected by the subject according or not to a food classification (group/subgroup1/subgroup2).\n"
                            + "- When a group/subgroup1/subgroup2 is specified, the rule is applied only for these 3 levels of classification (e.g. 070101 only for foods from beef classification).\n"
                            + "- When a group/subgroup1 is specified, the rule is applied for all the foods attached to these 2 or 3 levels of classification (e.g. 0701 so for foods classified under 070100, 070101, 070102, 070103, 070104, 070105 & 070106).\n"
                            + "- When a group is specified, the rule is applied for all the foods attached to these 1, 2 or 3 levels of classification (e.g. 06 so for foods classified under 0601, 0602, 0603, 0604, 0605,  0606 and also for 060300, 060301 & 060302).\n"
                            + "- When the group/subgroup1/subgroup2 is not specified (null values), the rule is applied to all foods, whatever its classification.",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "R_FACDESC",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String matchOnPreviouslySelectedFacetDescriptorBySubject;

    /**
     * Recipe Group code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Recipe Group code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeGroupCode;

    /**
     * Recipe Subgroup code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Recipe Subgroup code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RSUBGROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String recipeSubgroupCode;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "RuleAppliedToFacets(" + "facetWhereTheRuleMustBeApplied=" + getFacetWhereTheRuleMustBeApplied() + ","
         +"matchOnPreviouslySelectedFacetDescriptorBySubject=" + getMatchOnPreviouslySelectedFacetDescriptorBySubject() + ","
         +"recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ")";
    }

    /**
     * Manager Viewmodel for @{link RuleAppliedToFacets}
     */
    @Named("dita.globodiet.params.recipe_description.RuleAppliedToFacets.Manager")
    @DomainObjectLayout(
            describedAs = "Rule applied to facets"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @PropertyLayout(
                fieldSetId = "searchBar"
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Rule Applied To Facets";
        }

        @Collection
        public final List<RuleAppliedToFacets> getListOfRuleAppliedToFacets() {
            return searchService.search(RuleAppliedToFacets.class, RuleAppliedToFacets::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
