package at.ac.univie.nutrition.dita.recall24.model;

import java.time.LocalDate;
import java.util.List;

import at.ac.univie.nutrition.dita.commons.types.IntRef;

public record Interview24(

        /**
         * Respondent of this interview.
         */
        Respondent24 respondent,

        /**
         * Interview date.
         */
        LocalDate interviewDate,

        /**
         *  Each respondent can have one ore more interviews within the context of a specific survey.
         *  This ordinal denotes the n-th interview (when ordered by interview date).
         */
        IntRef interviewOrdinalRef,

        /**
         * Respondent meta-data for this interview.
         */
        RespondentMetaData24 respondentMetaData,

        /**
         * The meals of this interview.
         */
        List<Meal24> meals

        ) implements at.ac.univie.nutrition.dita.recall24.api.Interview24 {

    @Override
    public int interviewOrdinal() {
        return interviewOrdinalRef().getValue();
    }
}
