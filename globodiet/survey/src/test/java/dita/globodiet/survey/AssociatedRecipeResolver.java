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
package dita.globodiet.survey;

import jakarta.inject.Inject;

import dita.recall24.dto.RecallNode24.Builder24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Food;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

public class AssociatedRecipeResolver implements Transfomer {

    @Inject private ForeignKeyLookupService foreignKeyLookup;

    @Override
    public void accept(final Builder24<?> builder) {
        switch(builder) {
            case Food.Builder recordBuilder -> {
                //TODO[dita-globodiet-survey-24] replace the (proxy-) food node by its associated composite node
                recordBuilder.type(Record24.Type.COMPOSITE);
                //final var lookupKey = new Recipe.SecondaryKey(recordBuilder.name()); //TODO extract recipeId
                //final var recipe = foreignKeyLookup.unique(lookupKey);


                //TODO[dita-globodiet-survey-24] lookup from recipe data
                //recordBuilder.subRecords(...);
            }
            default -> {}
        }
    }

}
