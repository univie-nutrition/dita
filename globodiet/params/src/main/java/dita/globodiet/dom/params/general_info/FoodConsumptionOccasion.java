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
 * Food Consumption Occasion
 */
@Named("dita.globodiet.params.general_info.FoodConsumptionOccasion")
@DomainObject
@DomainObjectLayout(
        describedAs = "Food Consumption Occasion",
        cssClassFa = "solid user-clock"
)
@PersistenceCapable(
        table = "FCO"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FoodConsumptionOccasion implements HasSecondaryKey<FoodConsumptionOccasion> {
    /**
     * Food Consumption Occasion code
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Food Consumption Occasion code<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_CODE",
            allowsNull = "false",
            length = 2
    )
    @Getter
    @Setter
    private String code;

    /**
     * FCO long label (text displayed on screen)
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "FCO long label (text displayed on screen)<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_NAME",
            allowsNull = "false",
            length = 100
    )
    @Getter
    @Setter
    private String textDisplayedOnScreen;

    /**
     * FCO type: if =1 the FCO can be selected several times (e.g. During morning)
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "FCO type: if =1 the FCO can be selected several times (e.g. During morning)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_MODE",
            allowsNull = "false",
            length = 1
    )
    @Getter
    @Setter
    private String mode;

    /**
     * FCO short label to identify easily the FCO
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "FCO short label to identify easily the FCO<br>----<br>required=true, unique=true",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_SNAME",
            allowsNull = "false",
            length = 50
    )
    @Getter
    @Setter
    private String shortLabelToIdentifyEasily;

    /**
     * 0=non main FCO
     * 1=main FCO (to be displayed in nutrient check screen)
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "0=non main FCO<br>1=main FCO (to be displayed in nutrient check screen)<br>----<br>required=true, unique=false",
            hidden = Where.NOWHERE
    )
    @Column(
            name = "FCM_PRINCIPAL",
            allowsNull = "false"
    )
    @Getter
    @Setter
    private int displayInNutrientCheckScreenQ;

    @ObjectSupport
    public String title() {
        return String.format("%s (code=%s)", textDisplayedOnScreen, code);
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
     * SecondaryKey for @{link FoodConsumptionOccasion}
     */
    @Value
    public static final class SecondaryKey implements ISecondaryKey<FoodConsumptionOccasion> {
        private static final long serialVersionUID = 1;

        /**
         * Food Consumption Occasion code
         */
        private String code;

        @Override
        public Class<FoodConsumptionOccasion> correspondingClass() {
            return FoodConsumptionOccasion.class;
        }

        @Override
        public final Unresolvable unresolvable() {
            return new Unresolvable(String.format("UNRESOLVABLE %s", this));
        }
    }

    /**
     * Placeholder @{link ViewModel} for @{link FoodConsumptionOccasion} in case of an unresolvable secondary key.
     */
    @DomainObjectLayout(
            describedAs = "Unresolvable FoodConsumptionOccasion",
            cssClassFa = "skull red"
    )
    @RequiredArgsConstructor
    public static final class Unresolvable extends FoodConsumptionOccasion implements ViewModel {
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
