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
package dita.globodiet.params.pathway;

import io.github.causewaystuff.companion.applib.services.lookup.Cloneable;
import io.github.causewaystuff.companion.applib.services.search.SearchService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.Navigable;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Programmatic;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Snapshot;
import org.apache.causeway.applib.annotation.Where;
import org.apache.causeway.applib.services.repository.RepositoryService;

/**
 * Probing question pathway for recipe.
 */
@Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity")
@Named("dita.globodiet.params.pathway.ProbingQuestionPathwayForRecipe")
@DomainObject
@DomainObjectLayout(
        describedAs = "Probing question pathway for recipe.",
        cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                        + "solid question .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
)
@PersistenceCapable(
        table = "RPQPATH"
)
@DatastoreIdentity(
        strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column = "id"
)
public class ProbingQuestionPathwayForRecipe implements Cloneable<ProbingQuestionPathwayForRecipe> {
    @Inject
    RepositoryService repositoryService;

    @Inject
    SearchService searchService;

    /**
     * Probing question code
     */
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "1",
            describedAs = "Probing question code",
            hidden = Where.ALL_TABLES
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "2",
            describedAs = "Recipe group code",
            hidden = Where.ALL_TABLES
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "3",
            describedAs = "Recipe subgroup code",
            hidden = Where.ALL_TABLES
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
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @PropertyLayout(
            fieldSetId = "foreign",
            sequence = "4",
            describedAs = "Recipe identification number (R_ IDNUM)",
            hidden = Where.ALL_TABLES
    )
    @Column(
            name = "ID_NUM",
            allowsNull = "true",
            length = 5
    )
    @Getter
    @Setter
    private String recipeCode;

    @ObjectSupport
    public String title() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "ProbingQuestionPathwayForRecipe(" + "probingQuestionCode=" + getProbingQuestionCode() + ","
         +"recipeGroupCode=" + getRecipeGroupCode() + ","
         +"recipeSubgroupCode=" + getRecipeSubgroupCode() + ","
         +"recipeCode=" + getRecipeCode() + ")";
    }

    @Programmatic
    @Override
    public ProbingQuestionPathwayForRecipe copy() {
        var copy = repositoryService.detachedEntity(new ProbingQuestionPathwayForRecipe());
        copy.setProbingQuestionCode(getProbingQuestionCode());
        copy.setRecipeGroupCode(getRecipeGroupCode());
        copy.setRecipeSubgroupCode(getRecipeSubgroupCode());
        copy.setRecipeCode(getRecipeCode());
        return copy;
    }

    @Property(
            snapshot = Snapshot.EXCLUDED
    )
    @PropertyLayout(
            navigable = Navigable.PARENT,
            hidden = Where.EVERYWHERE
    )
    @NotPersistent
    public ProbingQuestionPathwayForRecipe.Manager getNavigableParent() {
        return new ProbingQuestionPathwayForRecipe.Manager(searchService, "");
    }

    /**
     * Manager Viewmodel for @{link ProbingQuestionPathwayForRecipe}
     */
    @Generated("io.github.causewaystuff.companion.codegen.domgen._GenEntity_Manager")
    @Named("dita.globodiet.params.pathway.ProbingQuestionPathwayForRecipe.Manager")
    @DomainObject(
            nature = Nature.VIEW_MODEL
    )
    @DomainObjectLayout(
            describedAs = "Probing question pathway for recipe.",
            cssClassFa = "solid person-walking-arrow-right .recipe-color,\n"
                            + "solid question .recipe-color .ov-size-60 .ov-right-50 .ov-bottom-85\n"
    )
    @AllArgsConstructor
    public static final class Manager implements ViewModel {
        public final SearchService searchService;

        @Property(
                optionality = Optionality.OPTIONAL,
                editing = Editing.ENABLED
        )
        @PropertyLayout(
                fieldSetId = "searchBar"
        )
        @Getter
        @Setter
        private String search;

        @ObjectSupport
        public String title() {
            return "Manage Probing Question Pathway For Recipe";
        }

        @Collection
        public final List<ProbingQuestionPathwayForRecipe> getListOfProbingQuestionPathwayForRecipe(
                ) {
            return searchService.search(ProbingQuestionPathwayForRecipe.class, ProbingQuestionPathwayForRecipe::title, search);
        }

        @Override
        public final String viewModelMemento() {
            return getSearch();
        }
    }
}
