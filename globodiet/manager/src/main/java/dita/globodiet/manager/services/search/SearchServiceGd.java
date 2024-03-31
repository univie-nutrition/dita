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

import io.github.causewaystuff.companion.applib.services.search.SearchService;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.NonNull;

@Service
public class SearchServiceGd
implements SearchService {

    @Inject RepositoryService repositoryService;

    @Override
    public <T> List<T> search(
            final @NonNull Class<T> entityType,
            final @NonNull Function<T, String> searchOn,
            final @Nullable String searchString) {

        var searchTokens = searchTokens(searchString);

        return searchTokens.isEmpty()
                ? repositoryService.allInstances(entityType)
                : repositoryService.allMatches(entityType,
                        t->allMatch(searchTokens, searchOn.apply(t)));
    }

    // -- HELPER

    private static Can<String> searchTokens(final @Nullable String searchString) {
        return Can.ofStream(_Strings.splitThenStream(searchString, " "))
                .map(String::toLowerCase)
                .filter(_Strings::isNotEmpty);
    }

    private static boolean allMatch(
            final @NonNull Can<String> searchTokens,
            final @Nullable String candidateString) {
        if(_Strings.isEmpty(candidateString)) return false;
        var candidateStringLower = candidateString.toLowerCase();
        return searchTokens.stream()
                .allMatch(candidateStringLower::contains);
    }

}
