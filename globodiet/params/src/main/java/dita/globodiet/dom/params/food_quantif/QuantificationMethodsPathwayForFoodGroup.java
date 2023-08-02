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
package dita.globodiet.dom.params.food_quantif;

import jakarta.inject.Named;
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
 * Quantification methods pathway for food groups/subgroups
 */
@Named("dita.globodiet.params.food_quantif.QuantificationMethodsPathwayForFoodGroup")
@DomainObject
@DomainObjectLayout(
    describedAs = "Quantification methods pathway for food groups/subgroups"
)
@PersistenceCapable(
    table = "QM_GROUP"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class QuantificationMethodsPathwayForFoodGroup {
  /**
   * Food group code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food group code"
  )
  @Column(
      name = "GROUP",
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
      name = "SUBGROUP1",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String foodSubgroupCode;

  /**
   * Food sub-subgroup code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food sub-subgroup code"
  )
  @Column(
      name = "SUBGROUP2",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String foodSubSubgroupCode;

  /**
   * Descriptor code of Physical state facet descface.facet_code+descface.descr_code (e.g. 0205)
   */
  @Property
  @PropertyLayout(
      describedAs = "Descriptor code of Physical state facet descface.facet_code+descface.descr_code (e.g. 0205)"
  )
  @Column(
      name = "PHYS_STATE",
      allowsNull = "true",
      length = 4
  )
  @Getter
  @Setter
  private String descriptorCodeOfPhysicalStateFacet;

  /**
   * 1=raw, 2=cooked (as Consumed)
   */
  @Property
  @PropertyLayout(
      describedAs = "1=raw, 2=cooked (as Consumed)"
  )
  @Column(
      name = "RAW_COOKED",
      allowsNull = "true",
      length = 1
  )
  @Getter
  @Setter
  private String rawOrCookedAsConsumed;

  /**
   * Quantification method code:<br>
   * 'P' for photo,<br>
   * 'H' for HHM,<br>
   * 'U' for stdu,<br>
   * 'S' for standard portion,<br>
   * 'A' for shape
   */
  @Property
  @PropertyLayout(
      describedAs = "Quantification method code:\n"
              + "'P' for photo,\n"
              + "'H' for HHM,\n"
              + "'U' for stdu,\n"
              + "'S' for standard portion,\n"
              + "'A' for shape"
  )
  @Column(
      name = "METHOD",
      allowsNull = "true",
      length = 1
  )
  @Getter
  @Setter
  private String quantificationMethodCode;

  /**
   * Photo code (if method='P' and 'A');<br>
   * either M_photos.ph_code or M_shapes.sh_code
   */
  @Property
  @PropertyLayout(
      describedAs = "Photo code (if method='P' and 'A');\n"
              + "either M_photos.ph_code or M_shapes.sh_code"
  )
  @Column(
      name = "METH_CODE",
      allowsNull = "true",
      length = 4
  )
  @Getter
  @Setter
  private String photoCode;

  /**
   * Comment
   */
  @Property
  @PropertyLayout(
      describedAs = "Comment"
  )
  @Column(
      name = "COMMENT",
      allowsNull = "true",
      length = 200
  )
  @Getter
  @Setter
  private String comment;
}