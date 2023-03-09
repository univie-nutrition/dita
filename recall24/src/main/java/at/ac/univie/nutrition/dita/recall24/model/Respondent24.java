package at.ac.univie.nutrition.dita.recall24.model;

import java.time.LocalDate;

import at.ac.univie.nutrition.dita.commons.types.Gender;

public record Respondent24(
        /**
         * Anonymized respondent identifier, unique to the corresponding survey.
         */
        String alias,

        LocalDate dateOfBirth,

        Gender gender

        ) implements at.ac.univie.nutrition.dita.recall24.api.Respondent24 {
}
