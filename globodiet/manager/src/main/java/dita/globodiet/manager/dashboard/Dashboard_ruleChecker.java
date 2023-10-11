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
import java.util.stream.Stream;

import org.asciidoctor.ast.StructuralNode;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import dita.commons.services.rules.RuleChecker;
import dita.commons.services.rules.RuleChecker.RuleViolation;
import dita.globodiet.dom.params.DitaModuleGdParams;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Action
@ActionLayout(fieldSetName="About", position = Position.PANEL)
@RequiredArgsConstructor
public class Dashboard_ruleChecker {

    @Autowired(required = false) List<RuleChecker> checkers;

    final Dashboard dashboard;

    public enum GroupBy {
        CHECKER,
        ENTITY
    }

    @MemberSupport
    public AsciiDoc act(
            @Parameter
            @ParameterLayout(
                    describedAs = "Select which Rule Checkers to run")
            final Can<RuleChecker> checkers,

            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES)
            @ParameterLayout(
                    describedAs = """
                            The report can be grouped by
                            individual Rule Checker or
                            individual Entity""")
            final GroupBy groupBy) {

        val adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle("Rule Checker Report"));

        val checkersSorted = checkersSorted();

        switch (groupBy) {
        case CHECKER: {

            checkersSorted.forEach(checker->{
                adoc.append(doc->{

                    var checkerSection = AsciiDocFactory.section(doc, nameOf(checker));

                    streamEntityClasses().forEach(entityClass->{
                        var violations = checker.check(entityClass);
                        violations.forEach(violation->formatAsAsciiDoc(checkerSection, violation));
                    });

                });
            });
            break;
        }
        case ENTITY: {
            streamEntityClasses().forEach(entityClass->{

                adoc.append(doc->{

                    var entitySection = AsciiDocFactory.section(doc, entityClass.getSimpleName());

                    checkersSorted.forEach(checker->{
                        var violations = checker.check(entityClass);
                        violations.forEach(violation->formatAsAsciiDoc(entitySection, violation));
                    });

                });

            });
            break;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + groupBy);
        }
        return adoc.buildAsValue();
    }

    @MemberSupport
    public Can<RuleChecker> defaultCheckers() {
        return checkersSorted();
    }
    @MemberSupport
    public Can<RuleChecker> choicesCheckers() {
        return checkersSorted();
    }

    @MemberSupport
    public GroupBy defaultGroupBy() {
        return GroupBy.CHECKER;
    }

    @MemberSupport
    public String disableAct() {
        return _NullSafe.isEmpty(checkers)
                ? "No Rule Checkers available."
                : null;
    }

    // -- HELPER

    private Can<RuleChecker> checkersSorted() {
        return Can.ofCollection(checkers)
                .sorted((a, b)->nameOf(a).compareTo(nameOf(b)));
    }

    private static String nameOf(final RuleChecker checker) {
        return checker.getClass().getSimpleName();
    }

    private static StructuralNode formatAsAsciiDoc(
            final StructuralNode parent,
            final RuleViolation ruleViolation) {
        var admonitionNode = switch (ruleViolation.criticality()) {
        case INFORMAL: yield AsciiDocFactory.note(parent);
        case WARNING: yield AsciiDocFactory.warning(parent);
        case SEVERE: yield AsciiDocFactory.caution(parent);
        };
        AsciiDocFactory.block(admonitionNode, ruleViolation.message());
        return admonitionNode;
    }

    private static Stream<Class<?>> streamEntityClasses() {
        return _NullSafe.stream(DitaModuleGdParams.entityClasses());
    }

}
