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
package dita.globodiet.dom.params.food_coefficient;

import jakarta.inject.Named;
import java.lang.Double;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

/**
 * % of fat use during cooking attached to a food
 */
@Named("dita.globodiet.params.food_coefficient.PercentOfFatUseDuringCookingAttachedToAFood")
@DomainObject
@DomainObjectLayout(
    describedAs = "% of fat use during cooking attached to a food"
)
@PersistenceCapable(
    table = "SPFDCOOK"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class PercentOfFatUseDuringCookingAttachedToAFood {
  /**
   * Food group code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food group code"
  )
  @Column(
      name = "FOOD_GROUP",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String foodGroupCode;

  /**
   * Food subgroup code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food subgroup code"
  )
  @Column(
      name = "FOOD_SUBG1",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String foodSubgroupCode;

  /**
   * Food sub-Subgroup code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food sub-Subgroup code"
  )
  @Column(
      name = "FOOD_SUBG2",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String foodSubSubgroupCode;

  /**
   * Food code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food code"
  )
  @Column(
      name = "FOOD_IDNUM",
      allowsNull = "true",
      length = 5
  )
  @Getter
  @Setter
  private String foodCode;

  /**
   * Fat group code for fat
   */
  @Property
  @PropertyLayout(
      describedAs = "Fat group code for fat"
  )
  @Column(
      name = "FAT_GROUP",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String fatGroupCodeForFat;

  /**
   * Fat subgroup code for fat
   */
  @Property
  @PropertyLayout(
      describedAs = "Fat subgroup code for fat"
  )
  @Column(
      name = "FAT_SUBG1",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String fatSubgroupCodeForFat;

  /**
   * Fat sub-subgroup code for fat
   */
  @Property
  @PropertyLayout(
      describedAs = "Fat sub-subgroup code for fat"
  )
  @Column(
      name = "FAT_SUBG2",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String fatSubSubgroupCodeForFat;

  /**
   * Fat code for fat
   */
  @Property
  @PropertyLayout(
      describedAs = "Fat code for fat"
  )
  @Column(
      name = "FAT_IDNUM",
      allowsNull = "true",
      length = 5
  )
  @Getter
  @Setter
  private String fatCodeForFat;

  /**
   * Cooking method Facet and Descriptor codes (e.g. 0305)
   */
  @Property
  @PropertyLayout(
      describedAs = "Cooking method Facet and Descriptor codes (e.g. 0305)"
  )
  @Column(
      name = "COOK_METH",
      allowsNull = "true",
      length = 4
  )
  @Getter
  @Setter
  private String cookingMethodFacetAndDescriptorCodes;

  /**
   * Percentage of fat absorbed during cooking
   */
  @Property
  @PropertyLayout(
      describedAs = "Percentage of fat absorbed during cooking"
  )
  @Column(
      name = "PCT_ABSOR",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Double percentageOfFatAbsorbedDuringCooking;
}