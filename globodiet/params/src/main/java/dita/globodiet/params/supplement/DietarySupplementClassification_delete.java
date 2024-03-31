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
import java.lang.String;
import javax.annotation.processing.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.LabelPosition;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;
import io.github.causewaystuff.companion.applib.services.lookup.DependantLookupService;
import io.github.causewaystuff.companion.applib.services.lookup.ForeignKeyLookupService;
import io.github.causewaystuff.companion.applib.services.search.SearchService;

@Generated("io.github.causewaystuff.companion.codegen.domgen._GenDeleteMixin")
@Action(
        semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
)
@ActionLayout(
        fieldSetId = "details",
        sequence = "9",
        describedAs = "Delete this DietarySupplementClassification",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class DietarySupplementClassification_delete {
    @Inject
    DependantLookupService dependantService;

    @Inject
    ForeignKeyLookupService foreignKeyLookup;

    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    private final DietarySupplementClassification mixee;

    @MemberSupport
    public DietarySupplementClassification.Manager act(
            @ParameterLayout(labelPosition = LabelPosition.TOP, multiLine = 12) String dependants) {
        repositoryService.remove(mixee);
        foreignKeyLookup.clearCache(mixee.getClass());
        return new DietarySupplementClassification.Manager(searchService, "");
    }

    @MemberSupport
    public String default0Act() {
        return dependantService.findAllDependantsAsMultilineString(mixee);
    }
}
