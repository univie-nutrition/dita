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
package dita.globodiet.manager.services.idgen;

import java.util.ListIterator;
import java.util.Optional;

import jakarta.inject.Inject;

import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import org.springframework.stereotype.Service;

import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.persistence.jdo.applib.services.JdoSupportService;

import dita.commons.services.idgen.IdGeneratorService;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.dom.params.nutrient.NutrientForFoodOrGroup;
import dita.globodiet.manager.util.FoodUtils;
import lombok.NonNull;
import lombok.SneakyThrows;

@Service
public class IdGeneratorGdParams
implements IdGeneratorService {

    @Inject JdoSupportService jdoSupport;

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> next(@NonNull final Class<T> idType, @NonNull final Class<?> entityType) {
        if(Food.class.equals(entityType)) {
            _Assert.assertEquals(String.class, idType);
            int foodMax = FoodUtils.foodCodeToInt(foodMax());
            return Optional.of((T)FoodUtils.foodCodeFromInt(foodMax + 1));
        }
        if(NutrientForFoodOrGroup.class.equals(entityType)) {
            _Assert.assertEquals(int.class, idType);
            final int next = nutrientForFoodOrGroupMax() + 1;
            return _Casts.uncheckedCast(Optional.of(next));
        }
        return Optional.empty();
    }

    // -- HELPER

    @SneakyThrows
    private String foodMax() {
        var pm = jdoSupport.getPersistenceManager();

        try(var query = pm.newQuery(
                "javax.jdo.query.SQL",
                "SELECT max(FOODNUM) FROM dita_gd_params.FOODS")){
            return Optional.ofNullable(query.execute())
                    .map(ForwardQueryResult.class::cast)
                    .map(ForwardQueryResult::listIterator)
                    .filter(ListIterator::hasNext)
                    .map(ListIterator::next)
                    .map(String.class::cast)
                    .orElse(null);
        }
    }

    @SneakyThrows
    private int nutrientForFoodOrGroupMax() {
        var pm = jdoSupport.getPersistenceManager();

        try(var query = pm.newQuery(
                "javax.jdo.query.SQL",
                "SELECT max(ITEM_SEQ) FROM dita_gd_params.ITEMS_DEF")){
            return Optional.ofNullable(query.execute())
                    .map(ForwardQueryResult.class::cast)
                    .map(ForwardQueryResult::listIterator)
                    .filter(ListIterator::hasNext)
                    .map(ListIterator::next)
                    .map(Integer.class::cast)
                    .orElse(null);
        }
    }

}
