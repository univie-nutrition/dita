/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.recall24.reporter.todo;

import java.util.TreeSet;

import org.apache.causeway.commons.io.DataSink;
import org.apache.causeway.commons.io.DataSource;


import lombok.experimental.UtilityClass;

import dita.commons.qmap.QualifiedMap;
import dita.commons.qmap.QualifiedMap.QualifiedMapKey;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;

@UtilityClass
public class TodoReporters {

    public record TodoReporter(
            InterviewSet24 interviewSet,
            SystemId systemId,
            QualifiedMap nutMapping) {

        public void report(final DataSink dataSink) {

            var unmapped = new TreeSet<QualifiedMapKey>();
            interviewSet.streamDepthFirst()
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
}
