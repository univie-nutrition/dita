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
package dita.globodiet.manager.services.iconfa;

import io.github.causewaystuff.companion.applib.services.iconfa.IconFaService;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.fa.FontAwesomeLayers;

import dita.globodiet.params.food_list.FoodSubgroup;

@Service
public class IconFaServiceGdParams
implements IconFaService {

    @Override
    public FontAwesomeLayers iconFaLayers(final @Nullable Object entity) {
        if(entity instanceof FoodSubgroup foodSubgroup) {
            return icon(foodSubgroup);
        }
        return FontAwesomeLayers.singleIcon("fa-cube");
    }

    // -- HELPER

    private FontAwesomeLayers icon(final FoodSubgroup foodSubgroup) {
        if(foodSubgroup.getFoodSubSubgroupCode()==null) {
            return FontAwesomeLayers.fromQuickNotation("solid utensils .food-color,"
                    + "solid layer-group .food-color .ov-size-80 .ov-right-55 .ov-bottom-55,"
                    + "solid circle-chevron-down .food-color-em .ov-size-60 .ov-left-50 .ov-bottom-85");
        }
        return FontAwesomeLayers.fromQuickNotation("solid utensils .food-color,"
                + "solid layer-group .food-color .ov-size-80 .ov-right-55 .ov-bottom-55,"
                + "solid circle-chevron-down .food-color-em2 .ov-size-60 .ov-left-50 .ov-bottom-45,"
                + "solid circle-chevron-down .food-color-em2 .ov-size-60 .ov-left-50 .ov-bottom-85");
    }
}
