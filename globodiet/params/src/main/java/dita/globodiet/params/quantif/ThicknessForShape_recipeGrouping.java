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
package dita.globodiet.params.quantif;

import dita.globodiet.params.recipe_list.RecipeSubgroup;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import jakarta.inject.Inject;
import javax.annotation.processing.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.collections.Can;

@Generated("io.github.causewaystuff.companion.codegen.domgen._GenAssociationMixin")
@Collection
@CollectionLayout(
        describedAs = "For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.\n"
                        + "These (sub)groups have to be separated with a comma (e.g. 01,02,0301)\n"
                        + "When this field is empty, that means that this thickness has always to be proposed\n"
                        + "whatever the recipe classification.\n"
                        + "Muliple rsubgr.group and/or rsubgr.subgroup comma-separated (e.g. 01,0601)",
        hidden = Where.NOWHERE
)
@RequiredArgsConstructor
public class ThicknessForShape_recipeGrouping {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final ThicknessForShape mixee;

    @MemberSupport
    public Can<RecipeSubgroup> coll() {
        return foreignKeyLookup.decodeLookupKeyList(RecipeSubgroup.class, mixee.getRecipeGroupingLookupKey())
            .map(foreignKeyLookup::unique);
    }
}
