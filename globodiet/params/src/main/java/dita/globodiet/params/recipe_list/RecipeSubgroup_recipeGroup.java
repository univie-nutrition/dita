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
package dita.globodiet.params.recipe_list;

import jakarta.inject.Inject;
import javax.annotation.processing.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;

@Generated("io.github.causewaystuff.companion.codegen.domgen._GenAssociationMixin")
@Property(
        snapshot = Snapshot.EXCLUDED
)
@PropertyLayout(
        fieldSetId = "details",
        sequence = "1.1",
        describedAs = "Recipe group code",
        hidden = Where.REFERENCES_PARENT
)
@RequiredArgsConstructor
public class RecipeSubgroup_recipeGroup {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final RecipeSubgroup mixee;

    @MemberSupport
    public RecipeGroup prop() {
        final var lookupKey = new RecipeGroup.SecondaryKey(mixee.getRecipeGroupCode());
        return foreignKeyLookup.unique(lookupKey);
    }
}
