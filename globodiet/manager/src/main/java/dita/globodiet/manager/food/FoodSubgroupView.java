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

import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".FoodSubgroupView")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_FOOD_GROUP)
public class FoodSubgroupView {

    public static FoodSubgroupView wrap(
            final RepositoryService repositoryService,
            final FoodSubgroup entity) {
        val view = new FoodSubgroupView();
        view.setFoodGroupCode(entity.getFoodGroupCode());
        view.setFoodSubGroupCode(entity.getFoodSubGroupCode());
        view.setFoodSubSubGroupCode(entity.getFoodSubSubGroupCode());
        view.setNameOfTheFoodSubSubGroup(entity.getNameOfTheFoodSubSubGroup());
        view.setShortNameOfTheFoodSubSubGroup(entity.getShortNameOfTheFoodSubSubGroup());
        return view;
    }

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s|%s)",
                nameOfTheFoodSubSubGroup,
                foodGroupCode,
                foodSubGroupCode,
                foodSubSubGroupCode
                );
    }

    /**
     * Food group code
     */
    @Property
    @PropertyLayout(
        sequence = "1",
        describedAs = "Food group code"
    )
    @Getter @Setter
    private String foodGroupCode;

    /**
     * Food sub-group code
     */
    @Property
    @PropertyLayout(
        sequence = "2",
        describedAs = "Food sub-group code"
    )
    @Getter @Setter
    private String foodSubGroupCode;

    /**
     * Food sub-sub-group code
     */
    @Property
    @PropertyLayout(
        sequence = "3",
        describedAs = "Food sub-sub-group code"
    )
    @Getter @Setter
    private String foodSubSubGroupCode;

    /**
     * Name of the food (sub-)(sub-)group
     */
    @Property
    @PropertyLayout(
        sequence = "4",
        describedAs = "Name of the food (sub-)(sub-)group"
    )
    @Getter @Setter
    private String nameOfTheFoodSubSubGroup;

    /**
     * 0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup
     */
    @Property
    @PropertyLayout(
        sequence = "5",
        describedAs = "0=non fat/sauce/sweetener subgroup 1= fat/sauce/sweetener subgroup"
    )
    @Getter @Setter
    private String fatOrSauceSweetenerSubgroupQ;

    /**
     * 0=non fat/sauce subgroup<br>
     * 1= fat/sauce subgroup that can be left over in the dish
     */
    @Property
    @PropertyLayout(
        sequence = "6",
        describedAs = "0=non fat/sauce subgroup\n"
                + "1= fat/sauce subgroup that can be left over in the dish"
    )
    @Getter @Setter
    private String fatOrSauceSubgroupThatCanBeLeftOverInTheDishQ;

    /**
     * 0=non fat during cooking subgroup<br>
     * 1= fat during cooking subgroup
     */
    @Property
    @PropertyLayout(
        sequence = "7",
        describedAs = "0=non fat during cooking subgroup\n"
                + "1= fat during cooking subgroup"
    )
    @Getter @Setter
    private String fatDuringCookingSubgroupQ;

    /**
     * Short Name of the food (sub-)(sub-)group
     */
    @Property
    @PropertyLayout(
        sequence = "8",
        describedAs = "Short Name of the food (sub-)(sub-)group"
    )
    @Getter @Setter
    private String shortNameOfTheFoodSubSubGroup;

}
