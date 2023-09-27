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
package dita.globodiet.dom.params.supplement;

import dita.commons.services.foreignkey.HasSecondaryKey;
import dita.commons.services.foreignkey.ISecondaryKey;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DependentDefaultsPolicy;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Dietary supplement classification
 */
@Named("dita.globodiet.params.supplement.DietarySupplementClassification")
@DomainObject
@DomainObjectLayout(
        describedAs = "Dietary supplement classification"
)
@PersistenceCapable(
        table = "DS_CLASSIF"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class DietarySupplementClassification implements HasSecondaryKey<DietarySupplementClassification> {
    /**
     * Dietary Supplement classification code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "1",
            describedAs = "Dietary Supplement classification code\n"
                            + "----\n"
                            + "required=false, unique=true",
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
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "2",
            describedAs = "Name of the food (sub-)(sub-)group\n"
                            + "----\n"
                            + "required=false, unique=false",
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
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            sequence = "3",
            describedAs = "Dietary Supplement classification code attached to (for subgroup)\n"
                            + "----\n"
                            + "required=false, unique=true",
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

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getCode())));
    }

    /**
     * Parameter model for @{link DietarySupplementClassification}
     * @param code Dietary Supplement classification code
     * @param name Name of the food (sub-)(sub-)group
     * @param attachedToCode Dietary Supplement classification code attached to (for subgroup)
     */
    public final record Params(
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Dietary Supplement classification code"
            )
            String code,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
                    optionality = Optionality.OPTIONAL
            )
            @ParameterLayout(
                    describedAs = "Name of the food (sub-)(sub-)group"
            )
            String name,
            @Parameter(
                    dependentDefaultsPolicy = DependentDefaultsPolicy.PRESERVE_CHANGES,
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
    public final record SecondaryKey(
            String code) implements ISecondaryKey<DietarySupplementClassification> {
        @Override
        public Class<DietarySupplementClassification> correspondingClass() {
            return DietarySupplementClassification.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link DietarySupplementClassification} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable DietarySupplementClassification",
            cssClassFa = "skull red"
    )
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
