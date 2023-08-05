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
package dita.globodiet.dom.params.classification;

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
 * Recipe group
 */
@Named("dita.globodiet.params.classification.RecipeGroup")
@DomainObject
@DomainObjectLayout(
    describedAs = "Recipe group"
)
@PersistenceCapable(
    table = "RGROUPS"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class RecipeGroup {
  /**
   * Recipe Group code
   */
  @Property
  @PropertyLayout(
      sequence = "1",
      describedAs = "Recipe Group code"
  )
  @Column(
      name = "GROUP",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String recipeGroupCode;

  /**
   * Name of the Recipe group
   */
  @Property
  @PropertyLayout(
      sequence = "2",
      describedAs = "Name of the Recipe group"
  )
  @Column(
      name = "NAME",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String nameOfTheRecipeGroup;

  /**
   * Short Name of the Recipe group
   */
  @Property
  @PropertyLayout(
      sequence = "3",
      describedAs = "Short Name of the Recipe group"
  )
  @Column(
      name = "NAMEG_SHORT",
      allowsNull = "true",
      length = 20
  )
  @Getter
  @Setter
  private String shortNameOfTheRecipeGroup;
}
