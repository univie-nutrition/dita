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
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.dom.params.setting;

import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import org.causewaystuff.domsupport.decorate.CollectionTitleDecorator;
import org.causewaystuff.domsupport.services.lookup.DependantLookupService;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodConsumptionOccasionDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(FoodConsumptionOccasion_dependentFoodConsumptionOccasionDisplayItemMappedByFoodConsumptionOccasion.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class FoodConsumptionOccasion_dependentFoodConsumptionOccasionDisplayItemMappedByFoodConsumptionOccasion {
        @Inject
        DependantLookupService dependantLookup;

        private final FoodConsumptionOccasion mixee;

        @MemberSupport
        public List<FoodConsumptionOccasionDisplayItem> coll() {
            return dependantLookup.findDependants(
                FoodConsumptionOccasionDisplayItem.class,
                FoodConsumptionOccasionDisplayItem_foodConsumptionOccasion.class,
                FoodConsumptionOccasionDisplayItem_foodConsumptionOccasion::prop,
                mixee);
        }
    }
}
