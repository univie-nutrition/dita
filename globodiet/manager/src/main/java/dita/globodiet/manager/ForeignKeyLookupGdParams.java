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
package dita.globodiet.manager;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import jakarta.inject.Inject;

import org.springframework.stereotype.Service;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

import dita.commons.services.foreignkey.ForeignKeyLookupService;
import dita.commons.types.BiString;
import dita.globodiet.dom.params.classification.RecipeGroupOrSubgroup;
import dita.globodiet.dom.params.food_descript.FacetDescriptor;
import lombok.NonNull;
import lombok.val;

@Service
public class ForeignKeyLookupGdParams
implements ForeignKeyLookupService {

    @Inject RepositoryService repositoryService;

    @Override
    public <L, F> Optional<F> uniqueMatch(final L localEntity, final Object localField,
            final Class<F> foreignType, final Function<F, Object> foreignFieldGetter) {

        return repositoryService.uniqueMatch(foreignType, foreign->
            Objects.equals(localField, foreignFieldGetter.apply(foreign)));
    }

    @Override
    public <L, F> Optional<F> uniqueMatch(final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Function<F, Object> foreignFieldGetter1,
            final Function<F, Object> foreignFieldGetter2) {

        if(localField == null) return Optional.empty();

        if(FacetDescriptor.class.equals(foreignType)) {
            val key = decodeFacetDescriptorLookupKey((String) localField);
            return repositoryService.uniqueMatch(foreignType, foreign->
                key.equalsPair(
                        (String)foreignFieldGetter1.apply(foreign),
                        (String)foreignFieldGetter2.apply(foreign)));

        } else if(RecipeGroupOrSubgroup.class.equals(foreignType)) {
            val keys = decodeGroupListLookupKey((String) localField);

            return switch(keys.getCardinality()) {
            case ZERO -> Optional.empty();
            case ONE -> repositoryService.uniqueMatch(foreignType, foreign->
                keys.getFirstElseFail().equalsPair(
                        (String)foreignFieldGetter1.apply(foreign),
                        (String)foreignFieldGetter2.apply(foreign)));
            case MULTIPLE ->
            // FIXME[DITA-110] need a way to perhaps display multiple links in tables
            repositoryService.uniqueMatch(foreignType, foreign->
                keys.getFirstElseFail().equalsPair(
                        (String)foreignFieldGetter1.apply(foreign),
                        (String)foreignFieldGetter2.apply(foreign)));
            };

        }

        throw _Exceptions.unrecoverable("2-ary foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    @Override
    public <L, F> Optional<F> uniqueMatch(
            final L localEntity, final Object localField,
            final Class<F> foreignType,
            final Function<F, Object> foreignFieldGetter1,
            final Function<F, Object> foreignFieldGetter2,
            final Function<F, Object> foreignFieldGetter3) {

        throw _Exceptions.unrecoverable("3-ary foreign key lookup not implemented for foreign type %s",
                foreignType.getName());
    }

    // -- HELPER

    /**
     * Facet string multiple (descface.facet_code + descface.descr_code)comma separated (e.g. 0401,0203,051)
     */
    static BiString decodeFacetDescriptorLookupKey(final @NonNull String input) {
        _Assert.assertEquals(4, input.length());
        return new BiString(input.substring(0, 2), input.substring(2));
    }

    /**
     * comma separated (e.g. 02 or 0300)
     */
    static BiString decodeGroupLookupKey(final @NonNull String input) {
        _Assert.assertTrue(input.length()==2
                || input.length()==4);
        return new BiString(input.substring(0, 2), input.length()==4
                ? input.substring(2)
                : null);
    }

    /**
     * comma separated (e.g. 02,0300,0301)
     */
    static Can<BiString> decodeGroupListLookupKey(final @NonNull String input) {
        return _Strings.splitThenStream(input, ",")
            .map(chunk->decodeGroupLookupKey(chunk))
            .collect(Can.toCan());
    }

}
