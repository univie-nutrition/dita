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
 * Facet for Recipe
 */
@Named("dita.globodiet.params.recipe_description.RecipeFacet")
@DomainObject
@DomainObjectLayout(
        describedAs = "Facet for Recipe"
)
@PersistenceCapable(
        table = "R_FACET"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class RecipeFacet implements HasSecondaryKey<RecipeFacet> {
    /**
     * Facet code for recipes
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code for recipes<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * Facet name
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Facet name<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * Facet text (text to show on the screen describing the facet)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Facet text (text to show on the screen describing the facet)<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_TEXT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String textToShowOnTheScreenDescribingTheFacet;

    /**
     * 0=Standard facets with descriptors available in R_Descface table
     * 1=Facets with descriptors available in RBrand table
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "0=Standard facets with descriptors available in R_Descface table<br>1=Facets with descriptors available in RBrand table<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_TYPE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int descriptorsAvailableForRecipeOrBrandQ;

    /**
     * 0 = facet with mono-selection of descriptor
     * 1 = facets with multi-selection of descriptors
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "0 = facet with mono-selection of descriptor<br>1 = facets with multi-selection of descriptors<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_TYPE_S",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int singleOrMultiSelectDescriptorQ;

    /**
     * 0 = standard facet
     * 1 = Main facet (with non modified descriptor)
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "0 = standard facet<br>1 = Main facet (with non modified descriptor)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_MAIN",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int standardOrMainFacetQ;

    /**
     * Label on how to ask the facet question
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Label on how to ask the facet question<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "RFACET_QUEST",
            allowsNull = "false",
            length = 300
    )
    @Getter
    @Setter
    private String labelOnHowToAskTheFacetQuestion;

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
     * SecondaryKey for @{link RecipeFacet}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<RecipeFacet> {
        private static final long serialVersionUID = 1;

        /**
         * Facet code for recipes
         */
        private String code;

        @Override
        public Class<RecipeFacet> correspondingClass() {
            return RecipeFacet.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link RecipeFacet} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable RecipeFacet",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends RecipeFacet implements ViewModel {
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
