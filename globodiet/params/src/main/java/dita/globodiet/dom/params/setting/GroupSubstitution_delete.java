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
package dita.globodiet.dom.params.setting;

import dita.commons.services.lookup.DependantLookupService;
import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import java.lang.String;
import lombok.RequiredArgsConstructor;
import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.SemanticsOf;

@Action(
        semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE
)
@ActionLayout(
        fieldSetId = "details",
        sequence = "9",
        describedAs = "Delete this GroupSubstitution",
        position = ActionLayout.Position.PANEL
)
@RequiredArgsConstructor
public class GroupSubstitution_delete {
    @Inject
    DependantLookupService dependantService;

    @Inject
    SearchService searchService;

    private final GroupSubstitution mixee;

    @MemberSupport
    public GroupSubstitution.Manager act(String dependants) {
        return new GroupSubstitution.Manager(searchService, "");
    }

    @MemberSupport
    public String default0Act() {
        return dependantService.findAllDependantsAsMultilineString(mixee);
    }
}
