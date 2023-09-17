package dita.globodiet.manager.lookup;

import org.apache.causeway.applib.ViewModel;
import org.apache.causeway.applib.annotation.DomainObjectLayout;

import dita.globodiet.dom.params.classification.FoodSubgroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import lombok.val;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Unresolvables {

    // -- FACET DESCRIPTOR

    public FacetDescriptorNotFound facetDescriptorNotFound(final String d1, final String d2) {
        val entity = new FacetDescriptorNotFound("UNRESOLVABLE");
        entity.setFacetCode(d1);
        entity.setCode(d2);
        return entity;
    }

    @DomainObjectLayout(
            cssClassFa = "tag red")
    public class FacetDescriptorNotFound extends FacetDescriptor
    implements ViewModel {
        public FacetDescriptorNotFound(final String memento) { setName(memento); }
        @Override public String viewModelMemento() { return getName(); }
    }

    // -- FOOD SUBGROUP

    public FoodSubgroupNotFound foodSubgroupNotFound(final String d1, final String d2, final String d3) {
        val entity = new FoodSubgroupNotFound("UNRESOLVABLE");
        entity.setFoodGroupCode(d1);
        entity.setFoodSubgroupCode(d2);
        entity.setFoodSubSubgroupCode(d3);
        return entity;
    }

    @DomainObjectLayout(
            cssClassFa = "solid layer-group red")
    public class FoodSubgroupNotFound extends FoodSubgroup
    implements ViewModel {
        public FoodSubgroupNotFound(final String memento) { setName(memento); }
        @Override public String viewModelMemento() { return getName(); }
    }

    // --

}
