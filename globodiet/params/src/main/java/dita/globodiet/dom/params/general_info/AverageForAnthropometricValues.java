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
package dita.globodiet.dom.params.general_info;

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
 * Average for anthropometric values (height, weight)
 */
@Named("dita.globodiet.params.general_info.AverageForAnthropometricValues")
@DomainObject
@DomainObjectLayout(
    describedAs = "Average for anthropometric values (height, weight)"
)
@PersistenceCapable(
    table = "ANTHROP"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class AverageForAnthropometricValues {
  /**
   * Anthropometry variable (Height or Weight)
   */
  @Property
  @PropertyLayout(
      sequence = "1",
      describedAs = "Anthropometry variable (Height or Weight)"
  )
  @Column(
      name = "ANT_VAR",
      allowsNull = "true",
      length = 15
  )
  @Getter
  @Setter
  private String anthropometryVariable;

  /**
   * Sex (1 for men, 2 for women, blank for both)
   */
  @Property
  @PropertyLayout(
      sequence = "2",
      describedAs = "Sex (1 for men, 2 for women, blank for both)"
  )
  @Column(
      name = "SEX",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer sex;

  /**
   * Age minimum range
   */
  @Property
  @PropertyLayout(
      sequence = "3",
      describedAs = "Age minimum range"
  )
  @Column(
      name = "AGE_MIN",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer ageMinimumRange;

  /**
   * Age maximum range
   */
  @Property
  @PropertyLayout(
      sequence = "4",
      describedAs = "Age maximum range"
  )
  @Column(
      name = "AGE_MAX",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer ageMaximumRange;

  /**
   * Minimum value of height or weight
   */
  @Property
  @PropertyLayout(
      sequence = "5",
      describedAs = "Minimum value of height or weight"
  )
  @Column(
      name = "ANT_MIN",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer minimumValueOfHeightOrWeight;

  /**
   * Maximum value of height or weight
   */
  @Property
  @PropertyLayout(
      sequence = "6",
      describedAs = "Maximum value of height or weight"
  )
  @Column(
      name = "ANT_MAX",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer maximumValueOfHeightOrWeight;

  /**
   * Default value of height or weight
   */
  @Property
  @PropertyLayout(
      sequence = "7",
      describedAs = "Default value of height or weight"
  )
  @Column(
      name = "ANT_DEF",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer defaultValueOfHeightOrWeight;
}
