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
package dita.globodiet.connectors.nutridb.util;

import java.util.stream.Collectors;

import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

import dita.commons.format.FormatUtils;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Builder24;
import dita.recall24.dto.Record24;

@UtilityClass
public class NutriDbConverters {

    @UtilityClass
    public class ToNutriDB {
        public String convertFacet(final String facetGd) {
            var facet = facetGd.substring(0, 2);
            var descriptor = facetGd.substring(2);
            return "gd:F" + FormatUtils.noLeadingZeros(facet) + "." + descriptor;
        }
        public String convertFacetList(final String facetSids) {
            return _Strings.splitThenStream(facetSids, ",")
                .map(ToNutriDB::convertFacet)
                .collect(Collectors.joining(","));
        }
        public String convertFood(final Record24.Type recordType, final String sid) {
            return switch(recordType) {
                case FOOD, TYPE_OF_FAT_USED, TYPE_OF_MILK_OR_LIQUID_USED, FRYING_FAT ->
                    // ndb system-id = 'gd'
                    "gd:N" + FormatUtils.noLeadingZeros(sid);
                case COMPOSITE ->
                    // ndb system-id = 'gdr'
                    "gdr:" + FormatUtils.noLeadingZeros(sid);
                case PRODUCT ->
                    // ndb system-id = 'ndb' (supplements only)
                    "ndb:" + FormatUtils.noLeadingZeros(sid);
            };
        }
    }

    /**
     * Converts GloboDiet to NutriDb identifiers.
     */
    public record ToNutriDbTransfomer() implements RecallNode24.Transfomer {
        @Override
        public void accept(final Builder24<?> builder) {
            switch (builder) {
            case Record24.Builder recBuilder -> toNutriDbPrefixes(recBuilder);
            default -> {}
            };
        }
        private void toNutriDbPrefixes(final Record24.Builder recBuilder) {
            recBuilder.sid(ToNutriDB.convertFood(recBuilder.type(), recBuilder.sid()));
            recBuilder.facetSids(ToNutriDB.convertFacetList(recBuilder.facetSids()));
        }
    }

}
