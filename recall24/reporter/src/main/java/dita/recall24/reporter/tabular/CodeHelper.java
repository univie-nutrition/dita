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
package dita.recall24.reporter.tabular;
import java.util.function.Predicate;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.tabular.TabularModel;
import org.apache.causeway.commons.tabular.TabularModel.TabularCell;
import org.apache.causeway.commons.tabular.TabularModel.TabularColumn;
import org.apache.causeway.commons.tabular.TabularModel.TabularRow;
import org.apache.causeway.commons.tabular.TabularModel.TabularSheet;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.sid.dmap.DirectMap;
import dita.commons.sid.dmap.DirectMapEntry;

record CodeHelper(
        SystemId systemId,
        String context,
        String friendlyName,
        DirectMap dMap) {

    public SemanticIdentifier code(final String nativeCode) {
        return new SemanticIdentifier(systemId, new ObjectId(context, nativeCode));
    }

    public Object label(final SemanticIdentifier code) {
        return dMap.lookupEntry(code)
                .map(DirectMapEntry::target)
                .map(SemanticIdentifier::objectId)
                .map(ObjectId::objectSimpleId)
                .orElse("?");
    }

    public SemanticIdentifierSet codes(final String commaSeparatedCodes) {
        return StringUtils.hasText(commaSeparatedCodes)
                ? SemanticIdentifierSet.ofCollection(_Strings.splitThenStream(commaSeparatedCodes, ",")
                        .map(String::trim)
                        .filter(Predicate.not(String::isEmpty))
                        .map(this::code)
                        .toList())
                : SemanticIdentifierSet.empty();
    }

    public TabularSheet sheet() {
        Can<TabularColumn> columns = Can.of(
                new TabularColumn(0, friendlyName + " Code", """
                    Semantic Identifier
                    [system]/[version]:
                    [context]/[object-id]"""),
                new TabularColumn(1, "Description", "Literal in native language"));

        Can<TabularRow> rows = dMap.streamEntries()
                .sorted((a, b)->a.source().compareTo(b.source()))
                .map(this::toCells)
                .map(TabularRow::new)
                .collect(Can.toCan());

        return new TabularModel.TabularSheet(friendlyName, columns, rows);
    }

    private Can<TabularCell> toCells(final DirectMapEntry dMapEntry) {
        return Can.of(
                TabularCell.single(dMapEntry.source().toStringNoBox()),
                TabularCell.single(dMapEntry.target().objectId().objectSimpleId()));
    }

}
