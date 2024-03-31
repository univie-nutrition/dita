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
package dita.globodiet.params.supplement;

import jakarta.inject.Inject;
import java.lang.Class;
import java.util.List;
import javax.annotation.processing.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.commons.collections.Can;
import io.github.causewaystuff.companion.applib.decorate.CollectionTitleDecorator;
import io.github.causewaystuff.companion.applib.services.lookup.DependantLookupService;
import org.springframework.context.annotation.Configuration;

@Generated("io.github.causewaystuff.companion.codegen.domgen._GenDependants")
@Configuration
public class DietarySupplementFacetDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(DietarySupplementFacet_dependentDietarySupplementMappedByFacet.class,
        DietarySupplementFacet_dependentDietarySupplementDescriptorMappedByFacet.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class DietarySupplementFacet_dependentDietarySupplementMappedByFacet {
        @Inject
        DependantLookupService dependantLookup;

        private final DietarySupplementFacet mixee;

        @MemberSupport
        public List<DietarySupplement> coll() {
            return dependantLookup.findDependants(
                DietarySupplement.class,
                DietarySupplement_facet.class,
                DietarySupplement_facet::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class DietarySupplementFacet_dependentDietarySupplementDescriptorMappedByFacet {
        @Inject
        DependantLookupService dependantLookup;

        private final DietarySupplementFacet mixee;

        @MemberSupport
        public List<DietarySupplementDescriptor> coll() {
            return dependantLookup.findDependants(
                DietarySupplementDescriptor.class,
                DietarySupplementDescriptor_facet.class,
                DietarySupplementDescriptor_facet::prop,
                mixee);
        }
    }
}
