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
package dita.globodiet.dom.params.quantif;

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
 * Shape
 */
@Named("dita.globodiet.params.quantif.Shape")
@DomainObject
@DomainObjectLayout(
        describedAs = "Shape"
)
@PersistenceCapable(
        table = "M_SHAPES"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class Shape implements HasSecondaryKey<Shape> {
    /**
     * Shape code (e.g. S001,S002,S003,...)
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Shape code (e.g. S001,S002,S003,...)<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SH_CODE",
            allowsNull = "false",
            length = 5
    )
    @Getter
    @Setter
    private String shapeCode;

    /**
     * Shape surface in cm2 (e.g. 200cm2). 2 decimals can be possible
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Shape surface in cm2 (e.g. 200cm2). 2 decimals can be possible<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SH_SURFACE",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private double shapeSurfaceInCm2;

    /**
     * Comment attached to the shape (e.g. oval bread small or oval bread medium or oval bread large…)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Comment attached to the shape (e.g. oval bread small or oval bread medium or oval bread large…)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SH_COMMENT",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String commentAttachedToTheShape;

    /**
     * Order to display the standard unit
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Order to display the standard unit<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "SH_ORDER",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int orderToDisplayTheStandardUnit;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getShapeCode());
    }

    @Programmatic
    public Unresolvable unresolvable() {
        return new Unresolvable(String.format("UNRESOLVABLE %s", new SecondaryKey(getShapeCode())));
    }

    /**
     * SecondaryKey for @{link Shape}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<Shape> {
        private static final long serialVersionUID = 1;

        /**
         * Shape code (e.g. S001,S002,S003,...)
         */
        private String shapeCode;

        @Override
        public Class<Shape> correspondingClass() {
            return Shape.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Shape} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable Shape",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends Shape implements ViewModel {
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
