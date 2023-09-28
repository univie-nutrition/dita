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
package dita.globodiet.dom.params.general_info;

import dita.commons.services.search.SearchService;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Special diet predefined answer
 */
@Named("dita.globodiet.params.general_info.SpecialDietPredefinedAnswer")
@DomainObject
@DomainObjectLayout(
        describedAs = "Special diet predefined answer"
)
@PersistenceCapable(
        table = "SPECDIET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class SpecialDietPredefinedAnswer {
    /**
     * Special diet code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Special diet code\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SPD_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String specialDietCode;

    /**
     * Special diet label
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Special diet label\n"
                            + "----\n"
                            + "required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SPD_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String specialDietLabel;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    /**
     * Manager Viewmodel for @{link SpecialDietPredefinedAnswer}
     */
    @Named("dita.globodiet.params.general_info.SpecialDietPredefinedAnswer.Manager")
    @DomainObjectLayout(
            describedAs = "Special diet predefined answer"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Special Diet Predefined Answer";
        }

        @Collection
        public final List<SpecialDietPredefinedAnswer> getListOfSpecialDietPredefinedAnswer() {
            return searchService.search(SpecialDietPredefinedAnswer.class, SpecialDietPredefinedAnswer::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
