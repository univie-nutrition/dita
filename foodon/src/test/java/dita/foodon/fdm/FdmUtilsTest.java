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
package dita.foodon.fdm;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.foodon.fdm.Dtos.FoodDescriptionModelDto;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredient;
import dita.testing.ApprovalTestOptions;

class FdmUtilsTest {

    @Test
    @UseReporter(DiffReporter.class)
    void toYaml() {
        var dto = FoodDescriptionModelDto.builder()
                .classificationFacets(List.of(
                        new ClassificationFacet(SemanticIdentifier.parse("test:fd/A"), "Food Descriptor A"),
                        new ClassificationFacet(SemanticIdentifier.parse("test:fd/B"), "Food Descriptor B"),
                        new ClassificationFacet(SemanticIdentifier.parse("test:fg/01"), "Fruits"),
                        new ClassificationFacet(SemanticIdentifier.parse("test:fg/02"), "Vegetables"),
                        new ClassificationFacet(SemanticIdentifier.parse("test:rg/01"), "Common Recipes")
                        ))
                .food(List.of(
                        new Food(SemanticIdentifier.parse("test:food/0001"), "Banana",
                                SemanticIdentifier.parse("test:fg/01")),
                        new Food(SemanticIdentifier.parse("test:food/0002"), "Apple",
                                SemanticIdentifier.parse("test:fg/01"))))
                .recipes(List.of(
                        new Recipe(SemanticIdentifier.parse("test:recipe/0002"), "Spaghetti",
                                SemanticIdentifier.parse("test:rg/01"))
                        ))
                .ingredients(List.of(
                        new RecipeIngredient(SemanticIdentifier.parse("test:recipe/0002"),
                                SemanticIdentifier.parse("test:food/0002"),
                                SemanticIdentifierSet.ofStream(
                                        Stream.of(
                                                SemanticIdentifier.parse("test:fd/1234"),
                                                SemanticIdentifier.parse("test:fd/5678"))),
                                new BigDecimal("0.123"),
                                new BigDecimal("0.324"))
                        ))
                .build();
        var fdm = FdmUtils.fromDto(dto);
        var yaml = FdmUtils.toYaml(fdm);

        //debug
        //System.err.printf("%s%n", yaml);

        Approvals.verify(yaml, ApprovalTestOptions.yamlOptions());
        assertEquals(fdm, FdmUtils.fromYaml(yaml), ()->"roundtrip on yaml failed");
    }

}
