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
package dita.globodiet.manager.brand;

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
import dita.globodiet.dom.params.food_descript.BrandName;
import dita.globodiet.manager.DitaModuleGdManager;
import dita.globodiet.manager.FontawesomeConstants;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion;
import lombok.val;

@DomainObject(nature=Nature.VIEW_MODEL)
@Named(DitaModuleGdManager.NAMESPACE + ".BrandManager")
@DomainObjectLayout(
        cssClassFa = FontawesomeConstants.ICON_BRANDS)
public class BrandManager
implements HasCurrentlyCheckedOutVersion {

    @Inject EntitiesMenu entities;
    @Inject BlobStore blobStore;
    @Inject RepositoryService repo;

    @ObjectSupport
    public String title() {
        return "Manage Brands";
    }

    @Collection
    public List<BrandName> getBrandNames() {
        return entities.listAllBrandName();
    }

    @Action
    @ActionLayout(associateWith = "brandNames", position = Position.PANEL)
    public BrandManager addBrand(
            @Parameter
            final String nameOfBrand,
            @Parameter
            final String foodGroup,
            @Parameter
            final String foodSubgroup,
            @Parameter
            final String foodSubSubgroup) {

        val brandName = repo.detachedEntity(new BrandName());

        brandName.setNameOfBrand(nameOfBrand);
        brandName.setFoodGroupCode(foodGroup);
        brandName.setFoodSubgroupCode(foodSubgroup);
        brandName.setFoodSubSubgroupCode(foodSubSubgroup);

        repo.persist(brandName);
        return this;
    }
    @MemberSupport public String disableAddBrand() {
        return guardAgainstCannotEditVersion(blobStore)
                .orElse(null);
    }
    // TODO dialog needs lookup tables - perhaps auto-generate mixins via schema?

}


