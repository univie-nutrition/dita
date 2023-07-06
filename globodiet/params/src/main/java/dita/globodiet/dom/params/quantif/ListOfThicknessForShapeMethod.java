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
package dita.globodiet.dom.params.quantif;

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
 * list of thickness for shape method
 */
@Named("dita.globodiet.params.quantif.ListOfThicknessForShapeMethod")
@DomainObject
@DomainObjectLayout(
    describedAs = "list of thickness for shape method"
)
@PersistenceCapable(
    table = "THICKNESS"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class ListOfThicknessForShapeMethod {
  /**
   * Thickness code (e.g. A,B,C,58_1,58_2...)
   */
  @Property
  @PropertyLayout(
      describedAs = "Thickness code (e.g. A,B,C,58_1,58_2...)"
  )
  @Column(
      name = "TH_CODE",
      allowsNull = "true",
      length = 10
  )
  @Getter
  @Setter
  private String thicknessCode;

  /**
   * has no description
   */
  @Property
  @PropertyLayout(
      describedAs = "has no description"
  )
  @Column(
      name = "TH_THICK",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Double thickness;

  /**
   * Comment attached to the thickness (e.g. small, medium, large…)
   */
  @Property
  @PropertyLayout(
      describedAs = "Comment attached to the thickness (e.g. small, medium, large…)"
  )
  @Column(
      name = "TH_COMMENT",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String commentAttachedToTheThickness;

  /**
   * For the food items, the food (sub)groups for which this thickness has to be proposed.<br>
   * These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)<br>
   * When this field is empty, that means that this thickness has always to be proposed<br>
   * whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1<br>
   * and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)
   */
  @Property
  @PropertyLayout(
      describedAs = "For the food items, the food (sub)groups for which this thickness has to be proposed.\n"
              + "These (sub)groups have to be separated with a comma (e.g. 0603,1002,1003,1101)\n"
              + "When this field is empty, that means that this thickness has always to be proposed\n"
              + "whatever the food classification. multiple subgroup.group and/or subgroup.subgroup1\n"
              + "and/or subgroup.subgroup2 commaseparated (e.g. 0603,10,1102)"
  )
  @Column(
      name = "TH_FDCLASS",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String forTheFoodItemsTheFoodSubGroupsForWhichThisThicknessHasToBeProposed;

  /**
   * For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.<br>
   * These (sub)groups have to be separated with a comma (e.g. 01,02,0301)<br>
   * When this field is empty, that means that this thickness has always to be proposed<br>
   * whatever the recipe classification; muliple rsubgr.group and/or rsubgr.subgroup commaseparated (e.g. 01,0601)
   */
  @Property
  @PropertyLayout(
      describedAs = "For the recipe items, the recipe (sub)groups for which this thickness has to be proposed.\n"
              + "These (sub)groups have to be separated with a comma (e.g. 01,02,0301)\n"
              + "When this field is empty, that means that this thickness has always to be proposed\n"
              + "whatever the recipe classification; muliple rsubgr.group and/or rsubgr.subgroup commaseparated (e.g. 01,0601)"
  )
  @Column(
      name = "TH_RCPCLASS",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String forTheRecipeItemsTheRecipeSubGroupsForWhichThisThicknessHasToBeProposed;
}
