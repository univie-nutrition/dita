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
package dita.globodiet.dom.params.recipe_description;

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
 * Descriptor per facet
 */
@Named("dita.globodiet.params.recipe_description.DescriptorPerFacet")
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
public class DescriptorPerFacet {
  /**
   * Facet code for recipes
   */
  @Property
  @PropertyLayout(
      sequence = "1",
      describedAs = "Facet code for recipes"
  )
  @Column(
      name = "RFACET_CODE",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String facetCodeForRecipes;

  /**
   * Descriptor code for recipes
   */
  @Property
  @PropertyLayout(
      sequence = "2",
      describedAs = "Descriptor code for recipes"
  )
  @Column(
      name = "RDESCR_CODE",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String descriptorCodeForRecipes;

  /**
   * Descriptor name
   */
  @Property
  @PropertyLayout(
      sequence = "3",
      describedAs = "Descriptor name"
  )
  @Column(
      name = "RDESCR_NAME",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String descriptorName;

  /**
   * Only for facet recipe production:<br>
   * 0=not homemade descriptor<br>
   * 1=Homemade descriptor
   */
  @Property
  @PropertyLayout(
      sequence = "4",
      describedAs = "Only for facet recipe production:\n"
              + "0=not homemade descriptor\n"
              + "1=Homemade descriptor"
  )
  @Column(
      name = "RDESCR_TYPE",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer homemadeOrNot;

  /**
   * Only for facet known/unknown: 1=unknown 2=known
   */
  @Property
  @PropertyLayout(
      sequence = "5",
      describedAs = "Only for facet known/unknown: 1=unknown 2=known"
  )
  @Column(
      name = "RDESCR_KNOWN",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer knownOrUnknown;

  /**
   * Descriptor with type='other' : 1=yes 0=no
   */
  @Property
  @PropertyLayout(
      sequence = "6",
      describedAs = "Descriptor with type='other' : 1=yes 0=no"
  )
  @Column(
      name = "RDESCR_OTHER",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer yesOrNo;

  /**
   * 0=not single descriptor<br>
   * 1=single descriptor
   */
  @Property
  @PropertyLayout(
      sequence = "7",
      describedAs = "0=not single descriptor\n"
              + "1=single descriptor"
  )
  @Column(
      name = "RDESCR_SINGLE",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer singleOrNot;
}
