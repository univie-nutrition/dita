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
package dita.globodiet.dom.params.food_coefficient;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.globodiet.dom.params.classification.FoodGroup;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;

@Property(
        snapshot = Snapshot.EXCLUDED
)
@PropertyLayout(
        sequence = "5.1",
        describedAs = "Fat group code for F/S/S",
        hidden = Where.REFERENCES_PARENT
)
@RequiredArgsConstructor
public class PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood_fssFatGroup {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final PercentOfFatOrSauceOrSweetenerAddedAfterCookingForFood mixee;

    @MemberSupport
    public FoodGroup prop() {
        return foreignKeyLookup
            .unary(
                this,
                // local
                mixee, mixee.getFssFatGroupCode(),
                // foreign
                FoodGroup.class, FoodGroup::getCode)
            .orElse(null);
    }
}
