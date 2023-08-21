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

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import lombok.RequiredArgsConstructor;

@Property(snapshot = Snapshot.EXCLUDED)
@PropertyLayout(
        sequence = "2.1",
        describedAs = "Top Level Food Group"
    )
@RequiredArgsConstructor
public class FoodOrProductOrAlias_foodGroup {

    @Inject RepositoryService repositoryService;

    private final FoodOrProductOrAlias mixee;

    public FoodGroup prop() {
        return repositoryService
            .uniqueMatch(FoodGroup.class,
                group->Objects.equals(group.getFoodGroupCode(), mixee.getFoodGroupCode()))
            .orElse(null);
    }

}
