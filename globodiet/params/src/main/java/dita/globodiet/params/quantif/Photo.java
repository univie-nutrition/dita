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
package dita.globodiet.params.quantif;

import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;
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
 * Photo and its quantities
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.quantif.Photo")
@DomainObject
@DomainObjectLayout(
        describedAs = "Photo and its quantities",
        cssClassFa = "solid image,\n"
                + "solid scale-balanced .ov-size-60 .ov-right-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "M_PHOTOS"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_Photo",
        members = {"code"}
)
public class Photo implements Cloneable<Photo>, PhotoOrShape, HasSecondaryKey<Photo> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Photo series code (P001,P002,P003,...)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Photo series code (P001,P002,P003,...)"
    )
    @Column(
            name = "PH_CODE",
            allowsNull = "false",
            length = 4
    )
    @Getter
    @Setter
    private String code;

    /**
     * List that defines the quantities of each photo (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "List that defines the quantities of each photo (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)"
    )
    @Column(
            name = "PH_QSTR",
            allowsNull = "false",
            length = 4096
    )
    @Getter
    @Setter
    private String quantificationList;

    /**
     * 1 = raw,
     * 2 = cooked (as estimated)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "1 = raw,\n"
                    + "2 = cooked (as estimated)"
    )
    @Column(
            name = "RAW_COOKED",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private RawOrCooked rawOrCooked;

    /**
     * 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "1 = without un-edible part,\n"
                    + "2 = with un-edible (as estimated)"
    )
    @Column(
            name = "EDIB",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private WithUnediblePartQ withUnediblePartQ;

    /**
     * G = in Unit grams (mass)
     * V = in Unit milliliter (volume)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "5",
            describedAs = "G = in Unit grams (mass)\n"
                    + "V = in Unit milliliter (volume)"
    )
    @Column(
            name = "PH_UNIT",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    @Extension(
            vendorName = "datanucleus",
            key = "enum-check-constraint",
            value = "true"
    )
    @Extension(
            vendorName = "datanucleus",
            key = "enum-value-getter",
            value = "getMatchOn"
    )
    private Unit unit;

    @ObjectSupport
    public String title() {
        return String.format("Photo (code=%s,unit=%s)", code, unit);
    }

    @Override
    public String toString() {
        return "Photo(" + "code=" + getCode() + ","
         +"quantificationList=" + getQuantificationList() + ","
         +"rawOrCooked=" + getRawOrCooked() + ","
         +"withUnediblePartQ=" + getWithUnediblePartQ() + ","
         +"unit=" + getUnit() + ")";
    }

    @Programmatic
    @Override
    public Photo copy() {
        var copy = repositoryService.detachedEntity(new Photo());
        copy.setCode(getCode());
        copy.setQuantificationList(getQuantificationList());
        copy.setRawOrCooked(getRawOrCooked());
        copy.setWithUnediblePartQ(getWithUnediblePartQ());
        copy.setUnit(getUnit());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            hidden = Where.EVERYWHERE,
            navigable = Navigable.PARENT
    )
    @NotPersistent
    public Photo.Manager getNavigableParent() {
        return new Photo.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @RequiredArgsConstructor
    public enum RawOrCooked {

        /**
         * no description
         */
        RAW("1", "raw"),
        /**
         * as estimated
         */
        COOKED("2", "cooked");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum WithUnediblePartQ {

        /**
         * without un-edible part
         */
        UN_EDIBLE_EXCLUDED("1", "un-edible excluded"),
        /**
         * with un-edible (as estimated)
         */
        UN_EDIBLE_INCLUDED("2", "un-edible included");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    @RequiredArgsConstructor
    public enum Unit {

        /**
         * in unit grams (mass)
         */
        GRAMS("G", "Grams"),
        /**
         * in unit milliliter (volume)
         */
        MILLILITER("V", "Milliliter");

        @Getter
        private final String matchOn;

        @Getter
        @Accessors(
                fluent = true
        )
        private final String title;
    }

    /**
     * Manager Viewmodel for @{link Photo}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.quantif.Photo.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Photo and its quantities",
            cssClassFa = "solid image,\n"
                    + "solid scale-balanced .ov-size-60 .ov-right-50 .ov-bottom-85\n"
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
            return "Manage Photo";
        }

        @Collection
        public final List<Photo> getListOfPhoto() {
            return searchService.search(Photo.class, Photo::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Photo}
     *
     * @param code Photo series code (P001,P002,P003,...)
     * @param quantificationList List that defines the quantities of each photo (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)
     * @param rawOrCooked 1 = raw,
     * 2 = cooked (as estimated)
     * @param withUnediblePartQ 1 = without un-edible part,
     * 2 = with un-edible (as estimated)
     * @param unit G = in Unit grams (mass)
     * V = in Unit milliliter (volume)
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Photo series code (P001,P002,P003,...)") String code,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "List that defines the quantities of each photo (e.g. 1-70,2-141,3-228,4-304,5-405,6-507)") String quantificationList,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "1 = raw,\n"
                            + "2 = cooked (as estimated)") RawOrCooked rawOrCooked,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "1 = without un-edible part,\n"
                            + "2 = with un-edible (as estimated)") WithUnediblePartQ withUnediblePartQ,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "G = in Unit grams (mass)\n"
                            + "V = in Unit milliliter (volume)") Unit unit
    ) {
    }

    /**
     * SecondaryKey for @{link Photo}
     *
     * @param code Photo series code (P001,P002,P003,...)
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String code
    ) implements ISecondaryKey<Photo> {
        @Override
        public Class<Photo> correspondingClass() {
            return Photo.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Photo} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable Photo",
            describedAs = "Unresolvable Photo",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.quantif.Photo.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends Photo implements ViewModel {
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
