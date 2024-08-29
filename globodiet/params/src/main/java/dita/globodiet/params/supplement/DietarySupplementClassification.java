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
 * Dietary supplement classification
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.supplement.DietarySupplementClassification")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement classification",
        cssClassFa = "solid tablets .supplement-color,\n"
                        + "solid layer-group .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
)
@PersistenceCapable(
        table = "DS_CLASSIF"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Unique(
        name = "SEC_KEY_UNQ_DietarySupplementClassification",
        members = {"code"}
)
public class DietarySupplementClassification implements Cloneable<DietarySupplementClassification>, HasSecondaryKey<DietarySupplementClassification> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Dietary Supplement classification code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Dietary Supplement classification code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DS_CLASS",
            allowsNull = "true",
            length = 20
    )
    @Getter
    @Setter
    private String code;

    /**
     * Name of the food (sub-)(sub-)group
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Name of the food (sub-)(sub-)group",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "NAME",
            allowsNull = "true",
            length = 50
    )
    @Getter
    @Setter
    private String name;

    /**
     * Dietary Supplement classification code attached to (for subgroup)
     */
    @Property(
            optionality = Optionality.OPTIONAL,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Dietary Supplement classification code attached to (for subgroup)",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DS_DS_CLASS",
            allowsNull = "true",
            length = 20
    )
    @Getter
    @Setter
    private String attachedToCode;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", name, code);
    }

    @Override
    public String toString() {
        return "DietarySupplementClassification(" + "code=" + getCode() + ","
         +"name=" + getName() + ","
         +"attachedToCode=" + getAttachedToCode() + ")";
    }

    @Programmatic
    @Override
    public DietarySupplementClassification copy() {
        var copy = repositoryService.detachedEntity(new DietarySupplementClassification());
        copy.setCode(getCode());
        copy.setName(getName());
        copy.setAttachedToCode(getAttachedToCode());
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
    public DietarySupplementClassification.Manager getNavigableParent() {
        return new DietarySupplementClassification.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    /**
     * Manager Viewmodel for @{link DietarySupplementClassification}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.supplement.DietarySupplementClassification.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Dietary supplement classification",
            cssClassFa = "solid tablets .supplement-color,\n"
                            + "solid layer-group .supplement-color .ov-size-80 .ov-right-55 .ov-bottom-55\n"
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
            return "Manage Dietary Supplement Classification";
        }

        @Collection
        public final List<DietarySupplementClassification> getListOfDietarySupplementClassification(
                ) {
            return searchService.search(DietarySupplementClassification.class, DietarySupplementClassification::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link DietarySupplementClassification}
     * @param code Dietary Supplement classification code
     * @param name Name of the food (sub-)(sub-)group
     * @param attachedToCode Dietary Supplement classification code attached to (for subgroup)
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Dietary Supplement classification code"
            )
            String code,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Name of the food (sub-)(sub-)group"
            )
            String name,
            @Parameter(
                    precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Dietary Supplement classification code attached to (for subgroup)"
            )
            String attachedToCode) {
    }

    /**
     * SecondaryKey for @{link DietarySupplementClassification}
     * @param code Dietary Supplement classification code
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String code) implements ISecondaryKey<DietarySupplementClassification> {
        @Override
        public Class<DietarySupplementClassification> correspondingClass() {
            return DietarySupplementClassification.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link DietarySupplementClassification} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable DietarySupplementClassification",
            describedAs = "Unresolvable DietarySupplementClassification",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.supplement.DietarySupplementClassification.Unresolvable")
    @RequiredArgsConstructor
    public static final class Unresolvable extends DietarySupplementClassification implements ViewModel {
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
