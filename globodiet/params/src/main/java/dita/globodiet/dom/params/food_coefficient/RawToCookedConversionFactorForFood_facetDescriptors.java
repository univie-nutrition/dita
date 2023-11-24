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
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.food_coefficient;

import dita.commons.services.lookup.ForeignKeyLookupService;
import dita.globodiet.dom.params.food_descript.FoodDescriptor;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.commons.collections.Can;

@Collection
@CollectionLayout(
        describedAs = "Facet string; multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)",
        hidden = Where.NOWHERE
)
@RequiredArgsConstructor
public class RawToCookedConversionFactorForFood_facetDescriptors {
    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    private final RawToCookedConversionFactorForFood mixee;

    @MemberSupport
    public Can<FoodDescriptor> coll() {
        return foreignKeyLookup.decodeLookupKeyList(FoodDescriptor.class, mixee.getFacetDescriptorsLookupKey())
            .map(foreignKeyLookup::unique);
    }
}
