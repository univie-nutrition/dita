package dita.globodiet.manager.editing;

import java.util.List;
import java.util.Objects;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.ActionLayout.Position;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.ParameterTuple;
import org.apache.causeway.applib.services.repository.RepositoryService;

import dita.globodiet.dom.params.classification.FoodGroup;
import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.food_list.Food;
import dita.globodiet.manager.blobstore.BlobStore;
import dita.globodiet.manager.blobstore.HasCurrentlyCheckedOutVersion;
import lombok.RequiredArgsConstructor;

@Action
@ActionLayout(fieldSetId="listOfFood", position = Position.PANEL)
@RequiredArgsConstructor
public class FoodManager_add {

    @Inject BlobStore blobStore;
    @Inject RepositoryService repositoryService;

    protected final Food.Manager mixee;

    public Food.Manager act(@ParameterTuple final Food.Params p) {
        //TODO this is just a stub
        return mixee;
    }
    @MemberSupport public List<FoodGroup> choices1Act(final Food.Params p) {
        return repositoryService.allInstances(FoodGroup.class);
    }
    @MemberSupport public List<FoodSubgroup> choices2Act(final Food.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()==null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode()));
    }
    @MemberSupport public List<FoodSubgroup> choices3Act(final Food.Params p) {
        return repositoryService.allMatches(FoodSubgroup.class,
                fg->fg.getFoodSubSubgroupCode()!=null
                    && Objects.equals(fg.getFoodGroupCode(), p.foodGroup().getCode())
                    && Objects.equals(fg.getFoodSubgroupCode(), p.foodSubgroup().getFoodSubgroupCode()));
    }
    @MemberSupport public String disableAct() {
        //TODO refactor into util
        return (new HasCurrentlyCheckedOutVersion() {}).guardAgainstCannotEditVersion(blobStore)
                .orElse(null);
    }

}
