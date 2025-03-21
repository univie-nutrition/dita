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

import io.github.causewaystuff.companion.applib.jpa.Persistable;
import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.lookup.HasSecondaryKey;
import io.github.causewaystuff.companion.applib.services.lookup.ISecondaryKey;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import java.lang.Class;
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
 * Shape for quantity
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.quantif.Shape")
@DomainObject
@DomainObjectLayout(
        describedAs = "Shape for quantity",
        cssClassFa = "solid circle-half-stroke,\n"
                + "solid scale-balanced .ov-size-60 .ov-right-50 .ov-bottom-85\n"
)
@Entity
@Table(
        name = "M_SHAPES",
        uniqueConstraints = @UniqueConstraint(
                name = "SEC_KEY_UNQ_Shape",
                columnNames = "`SH_CODE`"
        )
)
public class Shape implements Persistable, Cloneable<Shape>, PhotoOrShape, HasSecondaryKey<Shape> {
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
     * Shape code (e.g. S001,S002,S003,...)
     */
    @Property(
            optionality = Optionality.MANDATORY
    )
    @PropertyLayout(
            fieldSetId = "identity",
            sequence = "1",
            describedAs = "Shape code (e.g. S001,S002,S003,...)"
    )
    @Column(
            name = "\"SH_CODE\"",
            nullable = false,
            length = 5
    )
    @Getter
    @Setter
    private String code;

    /**
     * Shape surface in cm2 (e.g. 200cm2). 2 decimals can be possible
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "2",
            describedAs = "Shape surface in cm2 (e.g. 200cm2). 2 decimals can be possible"
    )
    @Column(
            name = "\"SH_SURFACE\"",
            nullable = false
    )
    @Getter
    @Setter
    private double surfaceInCm2;

    /**
     * Comment attached to the shape (e.g. oval bread small or oval bread medium or oval bread large…)
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "3",
            describedAs = "Comment attached to the shape (e.g. oval bread small or oval bread medium or oval bread large…)"
    )
    @Column(
            name = "\"SH_COMMENT\"",
            nullable = false,
            length = 100
    )
    @Getter
    @Setter
    private String comment;

    /**
     * Order to display this shape
     */
    @Property(
            optionality = Optionality.MANDATORY,
            editing = Editing.ENABLED
    )
    @PropertyLayout(
            fieldSetId = "details",
            sequence = "4",
            describedAs = "Order to display this shape"
    )
    @Column(
            name = "\"SH_ORDER\"",
            nullable = false
    )
    @Getter
    @Setter
    private int displayOrder;

    @ObjectSupport
    public String title() {
        return String.format("Shape (code=%s,surface=%.2fcm2)", code, surfaceInCm2);
    }

    @Override
    public String toString() {
        return "Shape(" + "code=" + getCode() + ","
         +"surfaceInCm2=" + getSurfaceInCm2() + ","
         +"comment=" + getComment() + ","
         +"displayOrder=" + getDisplayOrder() + ")";
    }

    @Programmatic
    @Override
    public Shape copy() {
        var copy = repositoryService.detachedEntity(new Shape());
        copy.setCode(getCode());
        copy.setSurfaceInCm2(getSurfaceInCm2());
        copy.setComment(getComment());
        copy.setDisplayOrder(getDisplayOrder());
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
    public Shape.Manager getNavigableParent() {
        return new Shape.Manager(searchService, "");
    }

    @Programmatic
    public SecondaryKey secondaryKey() {
        return new SecondaryKey(getCode());
    }

    /**
     * Manager Viewmodel for @{link Shape}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.quantif.Shape.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Shape for quantity",
            cssClassFa = "solid circle-half-stroke,\n"
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
            return "Manage Shape";
        }

        @Collection
        public final List<Shape> getListOfShape() {
            return searchService.search(Shape.class, Shape::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }

    /**
     * Parameter model for @{link Shape}
     *
     * @param code Shape code (e.g. S001,S002,S003,...)
     * @param surfaceInCm2 Shape surface in cm2 (e.g. 200cm2). 2 decimals can be possible
     * @param comment Comment attached to the shape (e.g. oval bread small or oval bread medium or oval bread large…)
     * @param displayOrder Order to display this shape
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Params")
    public final record Params(
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Shape code (e.g. S001,S002,S003,...)") String code,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Shape surface in cm2 (e.g. 200cm2). 2 decimals can be possible") double surfaceInCm2,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Comment attached to the shape (e.g. oval bread small or oval bread medium or oval bread large…)") String comment,
            @Parameter(precedingParamsPolicy = PrecedingParamsPolicy.PRESERVE_CHANGES, optionality = Optionality.MANDATORY) @ParameterLayout(describedAs = "Order to display this shape") int displayOrder
    ) {
    }

    /**
     * SecondaryKey for @{link Shape}
     *
     * @param code Shape code (e.g. S001,S002,S003,...)
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_SecondaryKey")
    public final record SecondaryKey(
            String code
    ) implements ISecondaryKey<Shape> {
        @Override
        public Class<Shape> correspondingClass() {
            return Shape.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s%s",
                correspondingClass().getSimpleName(),
                this.toString().substring(12)));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link Shape} in case of an unresolvable secondary key.
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Unresolvable")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            named = "Unresolvable Shape",
            describedAs = "Unresolvable Shape",
            cssClassFa = "skull .unresolvable-color"
    )
    @Named("dita.globodiet.params.quantif.Shape.Unresolvable")
    @Embeddable
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
