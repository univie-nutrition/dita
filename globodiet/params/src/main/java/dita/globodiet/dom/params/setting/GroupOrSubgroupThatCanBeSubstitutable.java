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
package dita.globodiet.dom.params.setting;

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
 * Group/subgroup that can be substitutable
 */
@Named("dita.globodiet.params.setting.GroupOrSubgroupThatCanBeSubstitutable")
@DomainObject
@DomainObjectLayout(
    describedAs = "Group/subgroup that can be substitutable"
)
@PersistenceCapable(
    table = "SUBSTIT"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class GroupOrSubgroupThatCanBeSubstitutable {
  /**
   * 0=Food classification 1=Recipe classification
   */
  @Property
  @PropertyLayout(
      describedAs = "0=Food classification 1=Recipe classification"
  )
  @Column(
      name = "TYPE",
      allowsNull = "true",
      length = 1
  )
  @Getter
  @Setter
  private String type;

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
   * Food sub-Subgroup code
   */
  @Property
  @PropertyLayout(
      describedAs = "Food sub-Subgroup code"
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
   * List of food groups/subgroups to apply the substitution (e.g. “01”, “13,1602,0507,0508”)
   */
  @Property
  @PropertyLayout(
      describedAs = "List of food groups/subgroups to apply the substitution (e.g. “01”, “13,1602,0507,0508”)"
  )
  @Column(
      name = "F_CLASS",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String listOfFoodGroupsOrSubgroupsToApplyTheSubstitution;

  /**
   * List of recipe groups/subgroups to apply the substitution (e.g. “01”, “02, 0403, 0702”)
   */
  @Property
  @PropertyLayout(
      describedAs = "List of recipe groups/subgroups to apply the substitution (e.g. “01”, “02, 0403, 0702”)"
  )
  @Column(
      name = "R_CLASS",
      allowsNull = "true",
      length = 100
  )
  @Getter
  @Setter
  private String listOfRecipeGroupsOrsubgroupsToApplyTheSubstitution;
}