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
 * 
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.quantif;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.value.Markup;
import org.apache.causeway.commons.collections.Can;

@Property(
        snapshot = org.apache.causeway.applib.annotation.Snapshot.EXCLUDED
)
@PropertyLayout(
        sequence = "4.1",
        describedAs = "For the food items, the food (sub)groups for which this thickness has to be proposed.\n"
                        + "These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)\n"
                        + "When this field is empty, that means that this thickness has always to be proposed\n"
                        + "whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1\n"
                        + "and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)"
)
@RequiredArgsConstructor
public class ThicknessForShapeMethod_foodSubgroups {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final ThicknessForShapeMethod mixee;

    @MemberSupport
    public Markup prop() {
        return foreignKeyLookup
            .plural(
                mixee, mixee.getFoodSubgroupsLookupKey(),
                // foreign
                FoodSubgroup.class,
                Can.of(FoodSubgroup::getFoodGroupCode, FoodSubgroup::getFoodSubgroupCode, FoodSubgroup::getFoodSubSubgroupCode));
    }
}
