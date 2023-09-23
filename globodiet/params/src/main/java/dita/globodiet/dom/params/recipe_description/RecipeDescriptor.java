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
package dita.globodiet.dom.params.recipe_description;

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
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

/**
 * Descriptor per facet
 */
@Named("dita.globodiet.params.recipe_description.RecipeDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Descriptor per facet"
)
@PersistenceCapable(
        table = "R_DESCFACE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeDescriptor implements HasSecondaryKey<RecipeDescriptor> {
    /**
     * Facet code for recipes
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code for recipes<br>----<br>required=true, unique=false",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String recipeFacetCode;

    /**
     * Descriptor code for recipes
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Descriptor code for recipes<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Descriptor name
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Descriptor name<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Only for facet recipe production:
     * 0=not homemade descriptor
     * 1=Homemade descriptor
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Only for facet recipe production:<br>0=not homemade descriptor<br>1=Homemade descriptor<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_TYPE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int homemadeOrNot;

    /**
     * Only for facet known/unknown: 1=unknown 2=known
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Only for facet known/unknown: 1=unknown 2=known<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_KNOWN",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int knownOrUnknown;

    /**
     * Descriptor with type='other' : 1=yes 0=no
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Descriptor with type='other' : 1=yes 0=no<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_OTHER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int yesOrNo;

    /**
     * 0=not single descriptor
     * 1=single descriptor
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "0=not single descriptor<br>1=single descriptor<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RDESCR_SINGLE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int singleOrNot;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s)", name, recipeFacetCode, code);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getRecipeFacetCode(), getCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getRecipeFacetCode(), getCode())));
    }

    /**
     * SecondaryKey for @{link RecipeDescriptor}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<RecipeDescriptor> {
        private static final long serialVersionUID = 1;

        /**
         * Facet code for recipes
         */
        private String recipeFacetCode;

        /**
         * Descriptor code for recipes
         */
        private String code;

        @Override
        public Class<RecipeDescriptor> correspondingClass() {
            return RecipeDescriptor.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link RecipeDescriptor} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable RecipeDescriptor",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends RecipeDescriptor implements ViewModel {
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