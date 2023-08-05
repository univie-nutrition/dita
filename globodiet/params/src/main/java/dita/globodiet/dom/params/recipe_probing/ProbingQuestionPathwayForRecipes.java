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
package dita.globodiet.dom.params.recipe_probing;

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
 * Probing question pathway for recipes
 */
@Named("dita.globodiet.params.recipe_probing.ProbingQuestionPathwayForRecipes")
@DomainObject
@DomainObjectLayout(
    describedAs = "Probing question pathway for recipes"
)
@PersistenceCapable(
    table = "RPQPATH"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class ProbingQuestionPathwayForRecipes {
  /**
   * Probing question code
   */
  @Property
  @PropertyLayout(
      sequence = "1",
      describedAs = "Probing question code"
  )
  @Column(
      name = "PQ_CODE",
      allowsNull = "true",
      length = 3
  )
  @Getter
  @Setter
  private String probingQuestionCode;

  /**
   * Recipe group code
   */
  @Property
  @PropertyLayout(
      sequence = "2",
      describedAs = "Recipe group code"
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
   * Recipe subgroup code
   */
  @Property
  @PropertyLayout(
      sequence = "3",
      describedAs = "Recipe subgroup code"
  )
  @Column(
      name = "SUBGROUP",
      allowsNull = "true",
      length = 2
  )
  @Getter
  @Setter
  private String recipeSubgroupCode;

  /**
   * Recipe identification number (R_ IDNUM)
   */
  @Property
  @PropertyLayout(
      sequence = "4",
      describedAs = "Recipe identification number (R_ IDNUM)"
  )
  @Column(
      name = "ID_NUM",
      allowsNull = "true",
      length = 5
  )
  @Getter
  @Setter
  private String recipeIdNumber;
}
