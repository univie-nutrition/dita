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

import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".FoodGroupView")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_FOOD_GROUP)
public class FoodGroupView {

    public static FoodGroupView wrap(
            final RepositoryService repositoryService,
            final FoodGroup entity) {
        val view = new FoodGroupView();
        view.setFoodGroupCode(entity.getFoodGroupCode());
        view.setFoodGroupName(entity.getFoodGroupName());
        view.setFoodGroupShortName(entity.getFoodGroupShortName());
        return view;
    }

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)",
                foodGroupName,
                foodGroupCode);
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
     * Food group name
     */
    @Property
    @PropertyLayout(
        sequence = "2",
        describedAs = "Food group name"
    )
    @Getter @Setter
    private String foodGroupName;

    /**
     * Food group short name
     */
    @Property
    @PropertyLayout(
        sequence = "3",
        describedAs = "Food group short name"
    )
    @Getter @Setter
    private String foodGroupShortName;

}
