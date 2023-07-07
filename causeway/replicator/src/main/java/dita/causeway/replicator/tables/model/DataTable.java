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
package dita.causeway.replicator.tables.model;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.metamodel.object.ManagedObject;
import org.apache.causeway.core.metamodel.spec.ObjectSpecification;
import org.apache.causeway.core.metamodel.spec.feature.MixedIn;

import lombok.Getter;
import lombok.NonNull;

public class DataTable {

    // -- CONSTRUCTION

    @Getter private final @NonNull ObjectSpecification elementType;
    @Getter private final @NonNull Can<DataColumn> dataColumns;

    @Getter private @NonNull Can<ManagedObject> dataElements;
    @Getter private @NonNull Can<DataRow> dataRows;

    public DataTable(
            final ObjectSpecification elementType) {

        this.elementType = elementType;
        this.dataColumns = elementType
                .streamProperties(MixedIn.EXCLUDED)
                .filter(prop->prop.isIncludedWithSnapshots())
                .map(property->new DataColumn(this, property))
                .collect(Can.toCan());

        this.dataElements = Can.empty();
        this.dataRows = Can.empty();
    }

    public void setDataElements(final Can<ManagedObject> dataElements) {
        this.dataElements = dataElements;
        this.dataRows = dataElements
                .map(domainObject->new DataRow(this, domainObject));
    }

    public String getLogicalName() {
        return getElementType().getLogicalTypeName();
    }

    /**
     * Count data rows.
     */
    public int getElementCount() {
        return dataRows.size();
    }

}
