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
import java.lang.Integer;
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
 * Density factor for food
 */
@Named("dita.globodiet.params.food_coefficient.DensityFactorForFood")
@DomainObject
@DomainObjectLayout(
    describedAs = "Density factor for food"
)
@PersistenceCapable(
    table = "DENSITY"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class DensityFactorForFood {
  /**
   * Food identification number (FOODNUM)<br>
   * either Foods.foodnum OR Mixedrec.r_idnum
   */
  @Property
  @PropertyLayout(
      describedAs = "Food identification number (FOODNUM)\n"
              + "either Foods.foodnum OR Mixedrec.r_idnum"
  )
  @Column(
      name = "ID_NUM",
      allowsNull = "true",
      length = 5
  )
  @Getter
  @Setter
  private String foodIdentificationNumber;

  /**
   * Density coefficient
   */
  @Property
  @PropertyLayout(
      describedAs = "Density coefficient"
  )
  @Column(
      name = "D_FACTOR",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Double densityCoefficient;

  /**
   * Facet string<br>
   * multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)
   */
  @Property
  @PropertyLayout(
      describedAs = "Facet string\n"
              + "multiple (descface.facet_code + descface.descr_code) comma separated (e.g. 0401,0203,051)"
  )
  @Column(
      name = "FACETS_STR",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String facetString;

  /**
   * Priority order
   */
  @Property
  @PropertyLayout(
      describedAs = "Priority order"
  )
  @Column(
      name = "PRIORITY",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String priorityOrder;

  /**
   * 1 = without un-edible part,<br>
   * 2 = with un-edible (as estimated)
   */
  @Property
  @PropertyLayout(
      describedAs = "1 = without un-edible part,\n"
              + "2 = with un-edible (as estimated)"
  )
  @Column(
      name = "EDIB",
      allowsNull = "true",
      length = 1
  )
  @Getter
  @Setter
  private String withUnediblePartQ;

  /**
   * 1 = raw, 2 = cooked (as estimated)
   */
  @Property
  @PropertyLayout(
      describedAs = "1 = raw, 2 = cooked (as estimated)"
  )
  @Column(
      name = "RAWCOOK",
      allowsNull = "true",
      length = 1
  )
  @Getter
  @Setter
  private String rawOrCooked;

  /**
   * 1 = density for food/ingredient,<br>
   * 2 = density for recipe
   */
  @Property
  @PropertyLayout(
      describedAs = "1 = density for food/ingredient,\n"
              + "2 = density for recipe"
  )
  @Column(
      name = "D_TYPE",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer densityForFoodOrRecipe;
}
