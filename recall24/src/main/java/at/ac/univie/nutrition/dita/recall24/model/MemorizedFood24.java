package at.ac.univie.nutrition.dita.recall24.model;

import java.util.List;

import at.ac.univie.nutrition.dita.commons.types.ObjectRef;

public record MemorizedFood24(
        /**
         * Meal this memorized food belongs to.
         */
        ObjectRef<Meal24> parentMealRef,

        /**
         * Freetext, describing this memorized food.
         */
        String name,

        /**
         * Records for this memorized food.
         */
        List<Record24> records

        ) implements at.ac.univie.nutrition.dita.recall24.api.MemorizedFood24 {

    @Override
    public Meal24 parentMeal() {
        return parentMealRef.getValue();
    }
}
