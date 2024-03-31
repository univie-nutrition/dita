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
package dita.globodiet.params.food_descript;

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
        sequence = "2.1",
        describedAs = "Facet code + Descriptor code that must exist in the current food description\n"
                        + "to allow the facet (FACET_CODE) to be asked.\n"
                        + "Additionally a group/subgroup code can be defined to force the food being described\n"
                        + "to belong to a specific food group to allow the facet to be asked (leave it to blanks if not applicable).",
        hidden = Where.REFERENCES_PARENT
)
@RequiredArgsConstructor
public class FoodFacetRule_facetDescriptor {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final FoodFacetRule mixee;

    @MemberSupport
    public FoodDescriptor prop() {
        return foreignKeyLookup.decodeLookupKeyList(FoodDescriptor.class, mixee.getFacetDescriptorLookupKey())
            .map(foreignKeyLookup::unique)
            .getSingleton().orElse(null);
    }
}
