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
// Auto-generated by DitA-Tooling
package dita.globodiet.dom.survey;

import dita.commons.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.services.factory.FactoryService;

@Named("dita.globodiet.survey.EntitiesMenu")
@DomainService(
        nature = org.apache.causeway.applib.annotation.NatureOfService.VIEW
)
public class EntitiesMenu {
    @Inject
    private FactoryService factoryService;

    @Inject
    private SearchService searchService;

    @Action
    @ActionLayout(
            cssClassFa = "solid users-viewfinder .survey-color"
    )
    public Survey.Manager manageSurvey() {
        return factoryService.viewModel(new Survey.Manager(searchService, ""));
    }
}
