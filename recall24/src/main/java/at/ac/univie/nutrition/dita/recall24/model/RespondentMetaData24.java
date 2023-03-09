package at.ac.univie.nutrition.dita.recall24.model;

import at.ac.univie.nutrition.dita.commons.types.ObjectRef;

public record RespondentMetaData24(

        /**
         * Parent interview.
         */
        ObjectRef<Interview24> parentInterviewRef,

        /**
         * Diet as practiced on the interview date.
         */
        String specialDietId,

        /**
         * Special day as practiced on the interview date.
         */
        String specialDayId,

        /**
         * Respondent's height in units of centimeter,
         * as measured on the interview date.
         */
        double heightCM,

        /**
         * Respondent's weight in units of kilogram,
         * as measured on the interview date.
         */
        double weightKG

        ) implements at.ac.univie.nutrition.dita.recall24.api.RespondentMetaData24 {

    @Override
    public Interview24 parentInterview() {
        return parentInterviewRef.getValue();
    }
}
