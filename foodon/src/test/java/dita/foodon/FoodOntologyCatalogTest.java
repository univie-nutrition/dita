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

import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.commons.io.TextUtils;

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
    void searchBeverage() {
        var foodOntologyCatalog = FoodOntologyCatalog.instance();
        var searchResult = foodOntologyCatalog.searchInAnnotationLabels("FOODON", "non-alcoholic beverages");
        searchResult.forEach(owlClassRec->{
            //debug
            System.err.printf("%s%n", owlClassRec);
        });
        assertEquals(2, searchResult.size());
    }

    @Test
    void searchSugar() {
        var foodOntologyCatalog = FoodOntologyCatalog.instance();
        var searchResult = foodOntologyCatalog.searchInAnnotationLabels("FOODON", "brown", "sugar", "efsa");
        searchResult.forEach(owlClassRec->{
            //debug
            System.err.printf("%s%n", owlClassRec);
        });
        //assertEquals(2, searchResult.size());
    }

    //TODO[dita-foodon-26] can we map at-gd groups to foodon?
    @Test
    void groupTermSearch()  {
        var ds = DataSource.ofResource(getClass(), "at-gd.groups.txt");
        var groups = TextUtils.readLinesFromDataSource(ds, StandardCharsets.UTF_8)
        .map(String::trim)
        .map(s->s.replace("/", " "))
        .map(s->s.replace(",", " "))
        .map(s->s.replace("(", " "))
        .map(s->s.replace(")", " "))
        .map(s->s.replace("  ", " "))
        .filter(_Strings::isNotEmpty)
        //.filter(line->!"TRANSLATE".equals(line))
        //.filter(line->!line.codePoints().anyMatch(Character::isLowerCase))
        ;

        var foodOntologyCatalog = FoodOntologyCatalog.instance();

        groups.forEach(group->{

            //debug
            //System.err.printf("%s%n", group);

            var words = Can.ofStream(
                    _NullSafe.stream(new StringTokenizer(group).asIterator())
                    .map(String.class::cast)
                )
                .toArray(new String[0]);

            var searchResult = foodOntologyCatalog.searchInAnnotationLabels("FOODON", words);
            searchResult.stream().limit(5).forEach(owlClassRec->{
                //debug
                //System.err.printf("  %s%n", owlClassRec);
            });

        });
    }
}
