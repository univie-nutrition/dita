/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.globodiet.manager.editing.food;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.globodiet.dom.params.EntitiesMenu;
import dita.globodiet.dom.params.food_list.FoodOrProductOrAlias;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".FoodManager")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_FOOD)
public class FoodManager
implements HasCurrentlyCheckedOutVersion {

    @Inject EntitiesMenu entities;
    @Inject BlobStore blobStore;
    @Inject RepositoryService repositoryService;

    @ObjectSupport
    public String title() {
        return "Manage Food List";
    }

    @Collection
    public List<FoodOrProductOrAlias> getFoodList() {
        return entities.listAllFoodOrProductOrAlias();
    }

    @Action
    @ActionLayout(fieldSetName="foodList", position = Position.PANEL)
    public FoodManager addFood(

            @Parameter
            final String name) {
        //TODO this is just a stub
        return this;
    }
    @MemberSupport public String disableAddFood() {
        return guardAgainstCannotEditVersion(blobStore)
                .orElse(null);
    }


}

