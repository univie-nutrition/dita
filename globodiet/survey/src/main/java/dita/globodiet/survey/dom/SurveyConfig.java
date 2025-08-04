package dita.globodiet.survey.dom;

import org.apache.causeway.commons.io.YamlUtils;

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.util.FormatUtils;

public record SurveyConfig(SystemId systemId) {

    public static SurveyConfig empty() {
        return new SurveyConfig(SystemId.empty());
    }

    public static SurveyConfig fromYaml(final String yaml) {
        return YamlUtils.tryRead(SurveyConfig.class, yaml, FormatUtils.yamlOptions())
            .valueAsNonNullElseFail();
    }

    public String toYaml() {
        return YamlUtils.toStringUtf8(this, FormatUtils.yamlOptions());
    }

}
