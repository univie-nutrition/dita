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
package dita.globodiet.params.setting;

import io.github.causewaystuff.companion.applib.jpa.EnumConverter;
import io.github.causewaystuff.companion.applib.jpa.EnumWithCode;
import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PrecedingParamsPolicy;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Place of Consumption
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.setting.PlaceOfConsumption")
@DomainObject
@DomainObjectLayout(
        describedAs = "Place of Consumption",
        cssClassFa = "solid building-user"
)
@Entity
@Table(
        name = "POC",
        uniqueConstraints = @UniqueConstraint(
                name = "SEC_KEY_UNQ_PlaceOfConsumption",
                columnNames = "`POC_CODE`"
        )
)
public class PlaceOfConsumption implements Persistable, Cloneable<PlaceOfConsumption>, HasSecondaryKey<PlaceOfConsumption> {
    @Inject
    @Transient
    RepositoryService repositoryService;

    @Inject
    @Transient
    SearchService searchService;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    /**
     * Place of consumption code
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Place of consumption code"
    )
    @Column(
            name = "\"POC_CODE\"",
            nullable = false,
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Place of consumption name
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Place of consumption name"
    )
    @Column(
            name = "\"POC_NAME\"",
            nullable = false,
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * 0=not a 'Other' place
     * 1='Other' place
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "0=not a 'Other' place\n"
                    + "1='Other' place"
    )
    @Column(
            name = "\"POC_OTHER\"",
            nullable = false
    )
    @Getter
    @Setter
    @Convert(
            converter = OtherPlaceQ.Converter.class
    )
    private OtherPlaceQ otherPlaceQ;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }

    @Override
    public String toString() {
        return "PlaceOfConsumption(" + "code=" + getCode() + ","
         +"name=" + getName() + ","
         +"otherPlaceQ=" + getOtherPlaceQ() + ")";
    }

    @Programmatic
    @Override
    public PlaceOfConsumption copy() {
        var copy = repositoryService.detachedEntity(new PlaceOfConsumption());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setOtherPlaceQ(getOtherPlaceQ());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @Transient
    public PlaceOfConsumption.Manager getNavigableParent() {
        return new PlaceOfConsumption.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @Getter
    @Accessors(
            fluent = true
    )
    @RequiredArgsConstructor
    public enum OtherPlaceQ implements EnumWithCode<Integer> {

        /**
         * not a 'Other' place
         */
        SPECIFIC(0, "specific"),
        /**
         * 'Other' place
         */
        OTHER(1, "other");

        private final Integer code;

        private final String title;

        @jakarta.persistence.Converter
        public static final class Converter implements EnumConverter<OtherPlaceQ, Integer> {
            @Override
            public OtherPlaceQ[] values() {
                return OtherPlaceQ.values();
            }
        }
    }

    /**
     * Manager Viewmodel for @{link PlaceOfConsumption}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.setting.PlaceOfConsumption.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Place of Consumption",
            cssClassFa = "solid building-user"
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
            return "Manage Place Of Consumption";
        }

        @Collection
        public final List<PlaceOfConsumption> getListOfPlaceOfConsumption() {
            return searchService.search(PlaceOfConsumption.class, PlaceOfConsumption::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link PlaceOfConsumption}
     *
     * @param code Place of consumption code
     * @param name Place of consumption name
     * @param otherPlaceQ 0=not a 'Other' place
     * 1='Other' place
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Place of consumption code") String code,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Place of consumption name") String name,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "0=not a 'Other' place\n"
                            + "1='Other' place") OtherPlaceQ otherPlaceQ
    ) {
    }

    /**
     * SecondaryKey for @{link PlaceOfConsumption}
     *
     * @param code Place of consumption code
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String code
    ) implements ISecondaryKey<PlaceOfConsumption> {
        @Override
        public Class<PlaceOfConsumption> correspondingClass() {
            return PlaceOfConsumption.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link PlaceOfConsumption} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable PlaceOfConsumption",
            describedAs = "Unresolvable PlaceOfConsumption",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.setting.PlaceOfConsumption.Unresolvable")
    @Embeddable
    @RequiredArgsConstructor
    public static final class Unresolvable extends PlaceOfConsumption implements ViewModel {
        @Getter(
                onMethod_ = {@Override}
        )
        @Accessors(
                fluent = true
        )
        private final String viewModelMemento;

        @Override
        public String title() {
            return viewModelMemento;
        }
    }
}
