package dita.globodiet.survey.util;

import java.util.Optional;

import dita.commons.format.FormatUtils;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;

record NameWithCode(String name, String code) {
    static NameWithCode parseAssocFood(final String nameAndCode) {
        return parse(nameAndCode, "{assocFood=");
    }
    static NameWithCode parseAssocRecipe(final String nameAndCode) {
        return parse(nameAndCode, "{assocRecp=");
    }
    static NameWithCode parse(final String nameAndCode, final String magic) {
        int c1 =nameAndCode.indexOf(magic);
        if(c1==-1) return new NameWithCode(nameAndCode, null);
        int c2 = nameAndCode.indexOf("}", c1);
        return new NameWithCode(
                nameAndCode.substring(0, c1).trim(),
                FormatUtils.fillWithLeadingZeros(5, nameAndCode.substring(c1 + magic.length(), c2)));
    }
    Optional<SemanticIdentifier> associatedRecipeSid(final SystemId systemId) {
        return Optional.ofNullable(code())
                .map(code->ObjectId.Context.RECIPE.sid(systemId, code));
    }
}

