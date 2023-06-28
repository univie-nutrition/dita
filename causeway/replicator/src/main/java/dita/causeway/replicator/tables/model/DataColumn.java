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

import java.util.Optional;

import org.apache.causeway.core.metamodel.spec.feature.OneToOneAssociation;

import lombok.Getter;
import lombok.NonNull;

public class DataColumn {

    @Getter private final @NonNull OneToOneAssociation propertyMetaModel;
    @Getter private final @NonNull String columnFriendlyName;
    @Getter private final @NonNull Optional<String> columnDescription;

    public DataColumn(final DataTable parentTable, final OneToOneAssociation propertyMetaModel) {
        this.propertyMetaModel = propertyMetaModel;
        this.columnFriendlyName = propertyMetaModel.getCanonicalFriendlyName();
        this.columnDescription = propertyMetaModel.getCanonicalDescription();
    }

}
