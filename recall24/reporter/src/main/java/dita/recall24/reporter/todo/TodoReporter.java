package dita.recall24.reporter.todo;

import java.util.TreeSet;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.applib.services.factory.FactoryService;
import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;

import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.qmap.QualifiedMap;
import dita.commons.sid.qmap.QualifiedMap.QualifiedMapKey;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;

public record TodoReporter(
        InterviewSet24 interviewSet,
        SystemId systemId,
        QualifiedMap nutMapping) {

    public void report(final DataSink dataSink, @NonNull final FactoryService factoryService) {

        var unmapped = new TreeSet<QualifiedMapKey>();
        interviewSet.streamDepthFirst(factoryService)
            .forEach((final RecallNode24 node)->{
                switch(node) {
                    case Record24.Consumption cRec -> {
                        var mapKey = cRec.asQualifiedMapKey();
                        var mapEntry = nutMapping.lookupEntry(mapKey);
                        if(!mapEntry.isPresent()) {
                            unmapped.add(mapKey);
                        }
                    }
                    default -> {}
                }
            });

        var qmap = QualifiedMap.todo(unmapped);

        DataSource.ofStringUtf8(qmap.toYaml())
            .pipe(dataSink);
    }

}