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
import java.lang.Integer;
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
 * Average for anthropometric values (height, weight)
 */
@Named("dita.globodiet.params.general_info.AnthropometricAverage")
@DomainObject
@DomainObjectLayout(
        describedAs = "Average for anthropometric values (height, weight)"
)
@PersistenceCapable(
        table = "ANTHROP"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class AnthropometricAverage {
    /**
     * Anthropometry variable (Height or Weight)
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "1",
            describedAs = "Anthropometry variable (Height or Weight)\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ANT_VAR",
            allowsNull = "false",
            length = 15
    )
    @Getter
    @Setter
    private String anthropometryVariable;

    /**
     * Sex (1 for men, 2 for women, blank for both)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Sex (1 for men, 2 for women, blank for both)\n"
                            + "----\n"
                            + "required=false, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SEX",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer sex;

    /**
     * Age minimum range
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Age minimum range\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "AGE_MIN",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int ageMinimumRange;

    /**
     * Age maximum range
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Age maximum range\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "AGE_MAX",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int ageMaximumRange;

    /**
     * Minimum value of height or weight
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "Minimum value of height or weight\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ANT_MIN",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int minimumValueOfHeightOrWeight;

    /**
     * Maximum value of height or weight
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "6",
            describedAs = "Maximum value of height or weight\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ANT_MAX",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int maximumValueOfHeightOrWeight;

    /**
     * Default value of height or weight
     */
    @Property(
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "7",
            describedAs = "Default value of height or weight\n"
                            + "----\n"
                            + "required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "ANT_DEF",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int defaultValueOfHeightOrWeight;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    /**
     * Manager Viewmodel for @{link AnthropometricAverage}
     */
    @Named("dita.globodiet.params.general_info.AnthropometricAverage.Manager")
    @DomainObjectLayout(
            describedAs = "Average for anthropometric values (height, weight)"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @PropertyLayout(
                fieldSetId = "searchBar"
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Anthropometric Average";
        }

        @Collection
        public final List<AnthropometricAverage> getListOfAnthropometricAverage() {
            return searchService.search(AnthropometricAverage.class, AnthropometricAverage::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
