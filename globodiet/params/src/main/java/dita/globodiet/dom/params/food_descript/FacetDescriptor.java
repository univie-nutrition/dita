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
package dita.globodiet.dom.params.food_descript;

import dita.commons.services.foreignkey.HasSecondaryKey;
import dita.commons.services.foreignkey.ISecondaryKey;
import jakarta.inject.Named;
import java.lang.Class;
import java.lang.Integer;
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
 * Descriptor for food facets (not recipe facets)
 */
@Named("dita.globodiet.params.food_descript.FacetDescriptor")
@DomainObject
@DomainObjectLayout(
        describedAs = "Descriptor for food facets (not recipe facets)",
        cssClassFa = "tag darkgreen"
)
@PersistenceCapable(
        table = "DESCFACE"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FacetDescriptor implements HasSecondaryKey<FacetDescriptor> {
    /**
     * Facet code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Facet code",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "FACET_CODE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String facetCode;

    /**
     * Descriptor code
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Descriptor code",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESCR_CODE",
            allowsNull = "true",
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
            describedAs = "Descriptor name",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESCR_NAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String name;

    /**
     * 0=Other descriptor without consequences in the algorithms (also from other facets)<br>
     * 1=Raw descriptor<br>
     * 2=Descriptors to ask the question 'fat used during cooking'
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "0=Other descriptor without consequences in the algorithms (also from other facets)<br>\n"
                            + "1=Raw descriptor<br>\n"
                            + "2=Descriptors to ask the question 'fat used during cooking'",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESC_COOK",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer type;

    /**
     * TODO missing description
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "TODO missing description",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESC_SINGLE",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer single;

    /**
     * TODO missing description
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "TODO missing description",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "DESC_OTHER",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer other;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s|%s)", name, facetCode, code);
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getFacetCode(), getCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getFacetCode(), getCode())));
    }

    /**
     * SecondaryKey for @{link FacetDescriptor}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<FacetDescriptor> {
        private static final long serialVersionUID = 1;

        /**
         * Facet code
         */
        private String facetCode;

        /**
         * Descriptor code
         */
        private String code;

        @Override
        public Class<FacetDescriptor> correspondingClass() {
            return FacetDescriptor.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FacetDescriptor} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FacetDescriptor",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends FacetDescriptor implements ViewModel {
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
