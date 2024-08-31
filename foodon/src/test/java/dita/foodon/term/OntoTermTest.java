package dita.foodon.term;

import org.junit.jupiter.api.Test;

import org.apache.causeway.commons.io.YamlUtils;

import dita.commons.sid.SemanticIdentifier;

class OntoTermTest {

    @SuppressWarnings("unused")
    @Test
    void toYaml() {

        var foodRoot = new OntoTerm(null, sid("-"), null);
        var g0 = new OntoTerm(foodRoot, sid("G0"), "UNCLASSIFIED");
        var g1 = new OntoTerm(foodRoot, sid("G1"), "POTATOES AND OTHER TUBERS");
        var g10 = new OntoTerm(g1, sid("G1.0"), "UNCLASSIFIED, MIXED AND OTHER TUBERS");
        var n2 = new OntoTerm(g10, sid("N2"), "Sweet potato");
        var n3 = new OntoTerm(g10, sid("N3"), "Jerusalem artichoke");

        var yaml = YamlUtils.toStringUtf8(foodRoot);

        System.err.printf("%s%n", yaml);
    }

    private static SemanticIdentifier sid(final String objedId) {
        return SemanticIdentifier.parse("at.gd/2.0", objedId);
    }
}
