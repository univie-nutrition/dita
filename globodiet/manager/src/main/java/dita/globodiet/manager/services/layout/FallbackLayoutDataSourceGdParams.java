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
package dita.globodiet.manager.services.layout;

import jakarta.annotation.Priority;

import org.datanucleus.enhancement.Persistable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.io.DataSource;
import org.apache.causeway.core.metamodel.services.grid.bootstrap.GridSystemServiceBootstrap.FallbackLayoutDataSource;

@Service
@Priority(PriorityPrecedence.EARLY)
@Qualifier("Dita")
//@Log4j2
public class FallbackLayoutDataSourceGdParams implements FallbackLayoutDataSource {

    @Override
    public Try<String> tryLoadAsStringUtf8(final Class<?> domainClass) {
        if(domainClass.getSimpleName().endsWith("Manager")) {
            return DataSource.ofResource(getClass(), "ManagerLayout.xml").tryReadAsStringUtf8();
        }
        if(Persistable.class.isAssignableFrom(domainClass)) {
            return DataSource.ofResource(getClass(), "EntityLayout.xml").tryReadAsStringUtf8();
        }
        return Try.empty();
    }

}
