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
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.survey;

import dita.globodiet.survey.dom.Campaign;
import dita.globodiet.survey.dom.CampaignDeps;
import dita.globodiet.survey.dom.Campaign_delete;
import dita.globodiet.survey.dom.Campaign_survey;
import dita.globodiet.survey.dom.Survey;
import dita.globodiet.survey.dom.SurveyDeps;
import dita.globodiet.survey.dom.Survey_delete;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;
import org.apache.causeway.commons.collections.Can;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Generated("org.causewaystuff.companion.codegen.domgen._GenModule")
@Configuration
@Import({

        // Config Beans
        ModuleConfig.class,

        // Menu Entries
        EntitiesMenu.class,

        // Entities
        Campaign.class,
        Survey.class,

        // Submodules
        CampaignDeps.class,
        SurveyDeps.class,

        // Mixins
        Campaign_delete.class,
        Campaign_survey.class,
        Survey_delete.class,
        })
public class DitaModuleGdSurvey {
    public static final String NAMESPACE = "dita.globodiet.survey";

    public static Can<Class<?>> entityClasses() {
        return Can.of(Campaign.class,
        Survey.class);
    }
}
