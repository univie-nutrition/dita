package at.ac.univie.nutrition.dita.recall24.model;

import java.time.LocalTime;
import java.util.List;

import at.ac.univie.nutrition.dita.commons.types.ObjectRef;

public record Meal24(

        /**
         * Parent interview.
         */
        ObjectRef<Interview24> parentInterviewRef,

        /**
         * Hour of day, when this meal took place.
         */
        LocalTime hourOfDay,

        /**
         * Identifying the occasion, when this meal took place.
         */
        String foodConsumptionOccasionId,

        /**
         * Identifying the place, where this meal took place.
         */
        String foodConsumptionPlaceId,

        /**
         * Memorized food for this meal.
         */
        List<MemorizedFood24> memorizedFood

        ) implements at.ac.univie.nutrition.dita.recall24.api.Meal24 {

    @Override
    public Interview24 parentInterview() {
        return parentInterviewRef.getValue();
    }
}
