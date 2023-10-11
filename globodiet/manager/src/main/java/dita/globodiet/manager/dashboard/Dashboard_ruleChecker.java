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
package dita.globodiet.manager.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;

import dita.commons.services.rules.RuleChecker;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Action
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_ruleChecker {

    @Autowired(required = false) List<RuleChecker> checkers;

    final Dashboard dashboard;

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            final List<RuleChecker> checkers) {
        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Rule Checker Report"));
        return adoc.buildAsValue();
    }

    @MemberSupport
    public List<RuleChecker> defaultCheckers() {
        return checkers;
    }

    @MemberSupport
    public List<RuleChecker> choicesCheckers() {
        return checkers;
    }

    @MemberSupport
    public String disableAct() {
        return _NullSafe.isEmpty(checkers)
                ? "No Rule Checkers available."
                : null;
    }

}
