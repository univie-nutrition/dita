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
 * 
 */
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.params.food_table;

import jakarta.inject.Named;
import java.lang.Integer;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

/**
 * Items list and definition
 */
@Named("dita.globodiet.params.food_table.FoodOrRecipeOrAttachment")
@DomainObject
@DomainObjectLayout(
        describedAs = "Items list and definition"
)
@PersistenceCapable(
        table = "ITEMS_DEF"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class FoodOrRecipeOrAttachment {
    /**
     * Sequential number
     */
    @Property
    @PropertyLayout(
            sequence = "1",
            describedAs = "Sequential number"
    )
    @Column(
            name = "ITEM_SEQ",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer sequentialNumber;

    /**
     * Type of record: F=Food, R=recipe, A2=fat attached, A3=liquid attached
     */
    @Property
    @PropertyLayout(
            sequence = "2",
            describedAs = "Type of record: F=Food, R=recipe, A2=fat attached, A3=liquid attached"
    )
    @Column(
            name = "TYPE",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String typeOfRecord;

    /**
     * Food or recipe group
     */
    @Property
    @PropertyLayout(
            sequence = "3",
            describedAs = "Food or recipe group"
    )
    @Column(
            name = "GROUP",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeGroup;

    /**
     * Food or recipe sub-group
     */
    @Property
    @PropertyLayout(
            sequence = "4",
            describedAs = "Food or recipe sub-group"
    )
    @Column(
            name = "SUBGROUP1",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodOrRecipeSubgroup;

    /**
     * Food sub-sub-group
     */
    @Property
    @PropertyLayout(
            sequence = "5",
            describedAs = "Food sub-sub-group"
    )
    @Column(
            name = "SUBGROUP2",
            allowsNull = "true",
            length = 2
    )
    @Getter
    @Setter
    private String foodSubSubgroup;

    /**
     * Food or Recipe code
     */
    @Property
    @PropertyLayout(
            sequence = "6",
            describedAs = "Food or Recipe code"
    )
    @Column(
            name = "ID_NUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String foodOrRecipeCode;

    /**
     * Facet string
     */
    @Property
    @PropertyLayout(
            sequence = "7",
            describedAs = "Facet string"
    )
    @Column(
            name = "FACET_STR",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String facetString;

    /**
     * Brand name
     */
    @Property
    @PropertyLayout(
            sequence = "8",
            describedAs = "Brand name"
    )
    @Column(
            name = "BRANDNAME",
            allowsNull = "true",
            length = 100
    )
    @Getter
    @Setter
    private String brandName;

    /**
     * Priority order
     */
    @Property
    @PropertyLayout(
            sequence = "9",
            describedAs = "Priority order"
    )
    @Column(
            name = "PRIORITY",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer priority;

    /**
     * Attached records: only for the Type=A2 & A3
     */
    @Property
    @PropertyLayout(
            sequence = "10",
            describedAs = "Attached records: only for the Type=A2 & A3"
    )
    @Column(
            name = "ITEM_SEQ_SEQ",
            allowsNull = "true"
    )
    @Getter
    @Setter
    private Integer attachedRecords;

    /**
     * Comment
     */
    @Property
    @PropertyLayout(
            sequence = "11",
            describedAs = "Comment"
    )
    @Column(
            name = "COMMENT",
            allowsNull = "true",
            length = 1000000000
    )
    @Getter
    @Setter
    private String comment;

    @ObjectSupport
    public String title() {
        return this.toString();
    }
}
