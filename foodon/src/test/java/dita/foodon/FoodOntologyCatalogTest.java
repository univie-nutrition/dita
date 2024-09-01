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
package dita.foodon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FoodOntologyCatalogTest {

    //@Test
    void dump() {
        var foodOntologyCatalog = FoodOntologyCatalog.instance();
        assertNotNull(foodOntologyCatalog);
        assertNotNull(foodOntologyCatalog.rootOntology());
        //assertEquals(1, foodOntologyCatalog.getOntologies().size());
        foodOntologyCatalog.dump("CDNO", System.out);
    }

    @Test
    void search() {
        var foodOntologyCatalog = FoodOntologyCatalog.instance();
        foodOntologyCatalog.searchInAnnotationLabels("FOODON", "NON-ALCOHOLIC BEVERAGES")
        .forEach(owlClass->{
            System.err.printf("%s%n",
                    foodOntologyCatalog.toFoodonClassRecord(owlClass));
        });
    }
}
