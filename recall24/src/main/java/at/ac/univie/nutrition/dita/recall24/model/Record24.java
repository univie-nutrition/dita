package at.ac.univie.nutrition.dita.recall24.model;

import java.util.List;

import at.ac.univie.nutrition.dita.commons.types.ObjectRef;

public record Record24(

        /**
         * Memorized food this record belongs to.
         */
        ObjectRef<MemorizedFood24> parentMemorizedFoodRef,

        /**
         * The type of this record.
         */
        at.ac.univie.nutrition.dita.recall24.api.Record24.Type type,

        /**
         * The name of this record.
         */
        String name,

        /**
         * Ingredients of this record.
         */
        List<Ingredient24> ingredients


        ) implements at.ac.univie.nutrition.dita.recall24.api.Record24 {

    @Override
    public MemorizedFood24 parentMemorizedFood() {
        return parentMemorizedFoodRef.getValue();
    }
}
