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
package dita.globodiet.dom.params.food_descript;

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
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

/**
 * Exception for some food to the Facets/Descriptors pathway
 */
@Named("dita.globodiet.params.food_descript.ExceptionForSomeFoodToTheFacetDescriptorPathway")
@DomainObject
@DomainObjectLayout(
    describedAs = "Exception for some food to the Facets/Descriptors pathway"
)
@PersistenceCapable(
    table = "FOODFAEX"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class ExceptionForSomeFoodToTheFacetDescriptorPathway {
  /**
   * food ID number
   */
  @Property
  @PropertyLayout(
      describedAs = "food ID number"
  )
  @Column(
      name = "FOODNUM",
      allowsNull = "true",
      length = 5
  )
  @Getter
  @Setter
  private String foodIdNumber;

  /**
   * Facet codes that MUST appear in the sequence of facets corresponding to this food (superseeding its group pathway).<br>
   * The list of descriptors will be the ones defined for the subgroup in GROUPFAC file (Assuming always a subset)
   */
  @Property
  @PropertyLayout(
      describedAs = "Facet codes that MUST appear in the sequence of facets corresponding to this food (superseeding its group pathway).\n"
              + "The list of descriptors will be the ones defined for the subgroup in GROUPFAC file (Assuming always a subset)"
  )
  @Column(
      name = "FACET_CODE",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String facetCodesThatMUSTAppearInTheSequenceOfFacetsCorrespondingToThisFood;

  /**
   * Order to display the facets for the attached food (same order as order_fac from Groupfac table)
   */
  @Property
  @PropertyLayout(
      describedAs = "Order to display the facets for the attached food (same order as order_fac from Groupfac table)"
  )
  @Column(
      name = "ORDER_FAC",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer orderToDisplayFacetsForTheAttachedFood;
}