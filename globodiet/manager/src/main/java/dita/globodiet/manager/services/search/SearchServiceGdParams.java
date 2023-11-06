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
package dita.globodiet.manager.services.search;

import java.util.List;
import java.util.function.Function;

import jakarta.inject.Inject;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.internal.base._Strings;

import dita.commons.services.search.SearchService;
import lombok.NonNull;
import lombok.val;

@Service
public class SearchServiceGdParams
implements SearchService {

    @Inject RepositoryService repositoryService;

    @Override
    public <T> List<T> search(
            final @NonNull Class<T> entityType,
            final @NonNull Function<T, String> searchOn,
            final @Nullable String searchString) {

        val lowerCased = _Strings.nonEmpty(searchString)
                .map(String::toLowerCase)
                .orElse(null);

        return lowerCased == null
                ? repositoryService.allInstances(entityType)
                : repositoryService.allMatches(entityType,
                        t->searchOn.apply(t).toLowerCase().contains(lowerCased));
    }

}
