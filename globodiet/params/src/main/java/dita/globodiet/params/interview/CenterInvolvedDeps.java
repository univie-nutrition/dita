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
package dita.globodiet.params.interview;

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
public class CenterInvolvedDeps {
    public static Can<Class<?>> mixinClasses() {
        return Can.of(CenterInvolved_dependentInterviewerMappedByCenter.class,
        CenterInvolved_dependentSubjectToBeInterviewedMappedByCenter.class);
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class CenterInvolved_dependentInterviewerMappedByCenter {
        @Inject
        DependantLookupService dependantLookup;

        private final CenterInvolved mixee;

        @MemberSupport
        public List<Interviewer> coll() {
            return dependantLookup.findDependants(
                Interviewer.class,
                Interviewer_center.class,
                Interviewer_center::prop,
                mixee);
        }
    }

    @Collection
    @CollectionLayout(
            tableDecorator = CollectionTitleDecorator.class
    )
    @RequiredArgsConstructor
    public static class CenterInvolved_dependentSubjectToBeInterviewedMappedByCenter {
        @Inject
        DependantLookupService dependantLookup;

        private final CenterInvolved mixee;

        @MemberSupport
        public List<SubjectToBeInterviewed> coll() {
            return dependantLookup.findDependants(
                SubjectToBeInterviewed.class,
                SubjectToBeInterviewed_center.class,
                SubjectToBeInterviewed_center::prop,
                mixee);
        }
    }
}
