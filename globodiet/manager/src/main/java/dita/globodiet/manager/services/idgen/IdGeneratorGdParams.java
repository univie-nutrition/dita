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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.inject.Inject;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.persistence.jpa.applib.services.JpaSupportService;

import lombok.SneakyThrows;

import dita.commons.services.idgen.IdGeneratorService;
import dita.globodiet.params.food_descript.FoodDescriptor;
import dita.globodiet.params.food_list.Food;
import dita.globodiet.params.nutrient.NutrientForFoodOrGroup;
import dita.globodiet.params.util.FoodUtils;

@Service
public class IdGeneratorGdParams
implements IdGeneratorService {

    @Inject JpaSupportService jpaSupport;
    @Inject RepositoryService repositoryService;

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> next(
            @NonNull final Class<T> idType,
            @NonNull final Class<?> entityType,
            @Nullable final Object options) {
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
        if(FoodDescriptor.class.equals(entityType)) {
            _Assert.assertEquals(String.class, idType);
            var p = (FoodDescriptor.Params)options;
            if(p.facet()==null) {
                return Optional.empty();
            }
            return _Casts.uncheckedCast(Optional.of(foodDescriptorNext(p.facet().getCode())));
        }
        return Optional.empty();
    }

    // -- HELPER

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private String foodMax() {
        var em = jpaSupport.getEntityManagerElseFail(Food.class);
        var query = em.createNativeQuery("SELECT max(FOODNUM) FROM FOODS");
        var list = query.getResultList();
        var first = list.stream()
                .findFirst()
                .orElse(null);
        return (String) first;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private int nutrientForFoodOrGroupMax() {
        var em = jpaSupport.getEntityManagerElseFail(NutrientForFoodOrGroup.class);

        var query = em.createNativeQuery("SELECT max(ITEM_SEQ) FROM ITEMS_DEF");
        var list = query.getResultList();
        var first = list.stream()
                .findFirst()
                .orElse(null);
        return (Integer) first;
    }

    @SneakyThrows
    private String foodDescriptorNext(final String foodCode) {
        final Set<String> codesInUse =
            repositoryService.allMatches(FoodDescriptor.class, fd->foodCode.equals(fd.getFacetCode()))
                .stream()
                .map(fd->fd.getCode())
                .collect(Collectors.toCollection(HashSet::new));

        return IntStream.range(1,256)
            .mapToObj(i->String.format("%02X", i).toUpperCase())
            .filter(code->!codesInUse.contains(code))
            .findFirst()
            .orElse("??");
    }

}
