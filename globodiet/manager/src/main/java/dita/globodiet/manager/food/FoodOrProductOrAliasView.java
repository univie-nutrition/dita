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
package dita.globodiet.manager.food;

import java.util.Objects;

import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".FoodOrProductOrAliasView")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_FOOD)
public class FoodOrProductOrAliasView {

    public static FoodOrProductOrAliasView wrap(
            final RepositoryService repositoryService,
            final FoodOrProductOrAlias entity) {
        val view = new FoodOrProductOrAliasView();
        view.setFoodIdNumber(entity.getFoodIdNumber());
        view.setFoodNativeName(entity.getFoodNativeName());

        // TOP LEVEL GROUP
        repositoryService.uniqueMatch(FoodGroup.class,
                group->Objects.equals(group.getFoodGroupCode(), entity.getFoodGroupCode()))
        .ifPresent(group->{
            view.setFoodGroup(FoodGroupView.wrap(repositoryService, group));
        });

        if(entity.getFoodSubgroupCode()!=null) {

            // SUB GROUP
            repositoryService.uniqueMatch(FoodSubgroup.class,
                    group->Objects.equals(group.getFoodGroupCode(), entity.getFoodGroupCode())
                        && Objects.equals(group.getFoodSubGroupCode(), entity.getFoodSubgroupCode())
                        && Objects.equals(group.getFoodSubSubGroupCode(), null))
            .ifPresent(group->{
                view.setFoodSubgroup(FoodSubgroupView.wrap(repositoryService, group));
            });

            // SUB-SUB GROUP
            if(entity.getFoodSubSubgroupCode()!=null) {
                repositoryService.uniqueMatch(FoodSubgroup.class,
                        group->Objects.equals(group.getFoodGroupCode(), entity.getFoodGroupCode())
                            && Objects.equals(group.getFoodSubGroupCode(), entity.getFoodSubgroupCode())
                            && Objects.equals(group.getFoodSubSubGroupCode(), entity.getFoodSubSubgroupCode()))
                .ifPresent(group->{
                    view.setFoodSubSubgroup(FoodSubgroupView.wrap(repositoryService, group));
                });

            }
        }

        return view;
    }

    @ObjectSupport
    public String title() {
        return String.format("Food or Product or Alias [type=%s, id=%s])",
                typeOfItem,
                foodIdNumber);
    }

    /**
     * Food/C.R. Identification Code
     */
    @Property
    @PropertyLayout(
        sequence = "1",
        describedAs = "Food/C.R. Identification Code"
    )
    @Getter @Setter
    private String foodIdNumber;

    /**
     * Food Group code
     */
    @Property
    @PropertyLayout(
        sequence = "3",
        describedAs = "Food Group"
    )
    @Getter @Setter
    private FoodGroupView foodGroup;

    /**
     * Food Subgroup code
     */
    @Property
    @PropertyLayout(
        sequence = "3",
        describedAs = "Food Subgroup"
    )
    @Getter @Setter
    private FoodSubgroupView foodSubgroup;

    /**
     * Food Sub(sub)group code
     */
    @Property
    @PropertyLayout(
        sequence = "4",
        describedAs = "Food Sub-subgroup"
    )
    @Getter @Setter
    private FoodSubgroupView foodSubSubgroup;

    /**
     * Food/C.R. Name (Country name)
     */
    @Property
    @PropertyLayout(
        sequence = "5",
        describedAs = "Food/C.R. Name (Country name)"
    )
    @Getter @Setter
    private String foodNativeName;

    /**
     * Type of item:<br>
     * {} -> Normal Food Item<br>
     * GI -> Generic Food Item<br>
     * SH -> Shadow Item<br>
     * CR -> Composed Recipe (a.huber: does not appear to be used anywhere)<br>
     * Definition: its different ingredients can be identified and<br>
     * quantified separately after preparation<br>
     * (e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)<br>
     * or just before mixing (e.g. coffee with milk).<br>
     * Composed recipes are built during the interview: there is no a priori list of composed recipes.<br>
     * They are made from items listed below/linked to a quick list item.<br>
     * Example: Salad<br>
     * - Lettuce<br>
     * - Tomato<br>
     * - Cucumber<br>
     * - Salad dressing (can be a recipe in some projects where all sauces are in recipes)
     */
    @Property
    @PropertyLayout(
        sequence = "6",
        describedAs = "Type of item:\n"
                + "-> Normal Food Item (a.huber: or product?)\n"
                + "GI -> Generic Food Item\n"
                + "SH -> Shadow Item\n"
                + "CR -> Composed Recipe (a.huber: does not appear to be used anywhere)\n"
                + "Definition: its different ingredients can be identified and\n"
                + "quantified separately after preparation\n"
                + "(e.g. meat balls in sauce, rice with sauce, couscous dish, mixed salad)\n"
                + "or just before mixing (e.g. coffee with milk).\n"
                + "Composed recipes are built during the interview: there is no a priori list of composed recipes.\n"
                + "They are made from items listed below/linked to a quick list item.\n"
                + "Example: Salad\n"
                + "- Lettuce\n"
                + "- Tomato\n"
                + "- Cucumber\n"
                + "- Salad dressing (can be a recipe in some projects where all sauces are in recipes)"
    )
    @Getter @Setter
    private String typeOfItem;

    /**
     * Auxiliary field to force an internal order within each subgroup<br>
     * (if GI then 1 otherwise 2, this forces the GI at the top)
     */
    @Property
    @PropertyLayout(
        sequence = "7",
        describedAs = "Auxiliary field to force an internal order within each subgroup\n"
                + "(if GI then 1 otherwise 2, this forces the GI at the top)"
    )
    @Getter @Setter
    private String groupOrdinal;

    /**
     * 0=food 1=food & dietary supplement
     */
    @Property
    @PropertyLayout(
        sequence = "8",
        describedAs = "0=food 1=food & dietary supplement"
    )
    @Getter @Setter
    private Integer dietarySupplementQ;

}
