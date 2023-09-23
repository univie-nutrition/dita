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
package dita.globodiet.dom.params.quantif;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.collections.Can;

@Collection
@CollectionLayout(
        describedAs = "For the food items, the food (sub)groups for which this thickness has to be proposed.<br>These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)<br>When this field is empty, that means that this thickness has always to be proposed<br>whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1<br>and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)",
        hidden = Where.NOWHERE
)
@RequiredArgsConstructor
public class ThicknessForShapeMethod_foodSubgroups {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final ThicknessForShapeMethod mixee;

    @MemberSupport
    public Can<FoodSubgroup> coll() {
        return foreignKeyLookup.decodeLookupKeyList(FoodSubgroup.class, mixee.getFoodSubgroupsLookupKey())
            .map(foreignKeyLookup::unique);
    }
}
