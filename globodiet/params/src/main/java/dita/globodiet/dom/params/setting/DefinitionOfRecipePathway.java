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
import java.lang.Integer;
import java.lang.String;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;

/**
 * Definition of recipe pathway (available for each recipe type)
 */
@Named("dita.globodiet.params.setting.DefinitionOfRecipePathway")
@DomainObject
@DomainObjectLayout(
    describedAs = "Definition of recipe pathway (available for each recipe type)"
)
@PersistenceCapable(
    table = "RCP_PATH"
)
@DatastoreIdentity(
    strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
    column = "id"
)
public class DefinitionOfRecipePathway {
  /**
   * Type of recipe:<br>
   * 1.1=Open – Known<br>
   * 1.2=Open – Unknown<br>
   * 1.3=Open with brand<br>
   * 2.1=Closed<br>
   * 2.2=Closed with brand<br>
   * 3.0=Commercial<br>
   * 4.1=New – Known<br>
   * 4.2=New – Unknown
   */
  @Property
  @PropertyLayout(
      sequence = "1",
      describedAs = "Type of recipe:\n"
              + "1.1=Open – Known\n"
              + "1.2=Open – Unknown\n"
              + "1.3=Open with brand\n"
              + "2.1=Closed\n"
              + "2.2=Closed with brand\n"
              + "3.0=Commercial\n"
              + "4.1=New – Known\n"
              + "4.2=New – Unknown"
  )
  @Column(
      name = "R_TYPE",
      allowsNull = "true",
      length = 3
  )
  @Getter
  @Setter
  private String typeOfRecipe;

  /**
   * 1 = The ingredient window is displayed 0 = The ingredient window is not displayed
   */
  @Property
  @PropertyLayout(
      sequence = "2",
      describedAs = "1 = The ingredient window is displayed 0 = The ingredient window is not displayed"
  )
  @Column(
      name = "R_ING",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer ingredientWindowIsDisplayedQ;

  /**
   * Functions allocated in NEW interview mode when the ingredient window is displayed (R_ING=1):<br>
   * 1 = S - Substitute<br>
   * 2 = SAD - Substitute, Add & Delete<br>
   * 3 = SADQ - Substitute, Add, Delete & Quantify
   */
  @Property
  @PropertyLayout(
      sequence = "3",
      describedAs = "Functions allocated in NEW interview mode when the ingredient window is displayed (R_ING=1):\n"
              + "1 = S - Substitute\n"
              + "2 = SAD - Substitute, Add & Delete\n"
              + "3 = SADQ - Substitute, Add, Delete & Quantify"
  )
  @Column(
      name = "N_IFUNCTION",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer functionsAllocatedInNEWInterviewModeWhenTheIngredientWindowIsDisplayed;

  /**
   * Functions allocated in EDIT interview mode when the ingredient window is displayed (R_ING=1):<br>
   * 1 = S - Substitute<br>
   * 2 = SAD - Substitute, Add & Delete<br>
   * 3 = SADQ - Substitute, Add, Delete & Quantify
   */
  @Property
  @PropertyLayout(
      sequence = "4",
      describedAs = "Functions allocated in EDIT interview mode when the ingredient window is displayed (R_ING=1):\n"
              + "1 = S - Substitute\n"
              + "2 = SAD - Substitute, Add & Delete\n"
              + "3 = SADQ - Substitute, Add, Delete & Quantify"
  )
  @Column(
      name = "E_IFUNCTION",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer functionsAllocatedInEDITInterviewModeWhenTheIngredientWindowIsDisplayed;

  /**
   * Display of the automatic note window:<br>
   * 0 = No display of note window<br>
   * 1 = Display of note window<br>
   * 2 = Display of note window only for Add & Delete functions
   */
  @Property
  @PropertyLayout(
      sequence = "5",
      describedAs = "Display of the automatic note window:\n"
              + "0 = No display of note window\n"
              + "1 = Display of note window\n"
              + "2 = Display of note window only for Add & Delete functions"
  )
  @Column(
      name = "D_NOTES",
      allowsNull = "true"
  )
  @Getter
  @Setter
  private Integer displayOfTheAutomaticNoteWindow;

  @ObjectSupport
  public String title() {
    return this.toString();
  }
}
