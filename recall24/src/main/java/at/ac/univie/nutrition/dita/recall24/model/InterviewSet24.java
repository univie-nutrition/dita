package at.ac.univie.nutrition.dita.recall24.model;

import java.util.List;

/**
 * Holds a collective of respondents and their individual 24h recall interviews.
 */
public record InterviewSet24(
        /**
         * Respondents that belong to this survey.
         */
        List<Respondent24> respondents,


        /**
         * Interviews that belong to this survey.
         */
        List<Interview24> interviews

        ) {
}
