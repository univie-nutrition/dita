package at.ac.univie.nutrition.dita.recall24.model;

import java.util.List;

import at.ac.univie.nutrition.dita.recall24.api.Interview24;
import at.ac.univie.nutrition.dita.recall24.api.Respondent24;

/**
 * A named survey object, identified by a key,
 * that holds a collective of respondents and their individual 24h recall interviews.
 */
public record Survey24(
        /**
         * Survey identifier.
         */
        String key,

        /**
         * Human readable survey name.
         */
        String name,

        // -- CHILDREN

        /**
         * Respondents that belong to this survey.
         */
        List<Respondent24> respondents,


        /**
         * Interviews that belong to this survey.
         */
        List<Interview24> interviews

        ) {


    /**
     * Factory for when the collective of respondents and their individual 24h recall interviews,
     * is made up of multiple {@link InterviewSet24}s.
     */
    public static Survey24 of(
            final String surveyKey,
            final String surveyName,
            final List<InterviewSet24> interviewSets) {
        return new Survey24(surveyKey, surveyName, null, null); //FIXME add merger
    }

}
