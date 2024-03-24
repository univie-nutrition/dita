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
package dita.globodiet.manager.editing.food;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;

import lombok.RequiredArgsConstructor;

import dita.globodiet.params.food_list.Food;
import dita.globodiet.manager.services.food.FoodQuantificationHelperService;
import dita.globodiet.params.classification.FoodGrouping;

@Property(
        snapshot = Snapshot.EXCLUDED
)
@PropertyLayout(
        fieldSetId = "details",
        sequence = "91.0",
        describedAs = "Indicates the FoodGroup or FoodSub(Sub)group, "
                + "that is used to calculate the 'Effective Quantification' list.",
        hidden = Where.ALL_TABLES
)
@RequiredArgsConstructor
public class Food_effectiveGroupingUsedForQuantificationPathway {

    @Inject private FoodQuantificationHelperService foodQuantificationHelperService;

    private final Food mixee;

    @MemberSupport
    public FoodGrouping prop() {
        return foodQuantificationHelperService.effectiveGroupingUsedForQuantificationPathway(mixee);
    }
}
