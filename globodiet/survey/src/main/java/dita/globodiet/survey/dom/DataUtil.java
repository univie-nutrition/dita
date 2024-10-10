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
package dita.globodiet.survey.dom;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.experimental.UtilityClass;

@UtilityClass
class DataUtil {

    record Line(
            String text,
            boolean enabled) implements Comparable<Line>{
        static Line parse(final String text) {
            var trimmed = _Strings.blankToNullOrTrim(text);
            if(trimmed==null) return null;
            if(trimmed.startsWith("#")) {
                trimmed = _Strings.blankToNullOrTrim(trimmed.substring(1));
                if(trimmed==null) return null;
                return new Line(trimmed, false);
            }
            return new Line(trimmed, true);
        }
        static Can<Line> sync(final Can<Line> allLines, final Can<Line> currentLines) {
            var aMap = allLines.toMap(Line::text);
            var bMap = currentLines.toMap(Line::text);
            var merged = new ArrayList<Line>();

            // if in a but NOT in b -> add to merged (and set enabled)
            // if NOT in a but in b -> don't add to merged
            // if in both a AND b -> add to merged (keep enabled-state as defined by b)
            aMap.entrySet().stream()
            .forEach(entry->{
                Line a = entry.getValue();
                Line b = bMap.get(entry.getKey());
                if(b!=null) {
                    merged.add(b);
                } else {
                    merged.add(a);
                }
            });
            Collections.sort(merged, Line::compareTo);
            return Can.ofCollection(merged);
        }
        @Override
        public int compareTo(final Line o) {
            return this.text.compareTo(o.text);
        }
        @Override
        public final String toString() {
            return enabled()
                    ? text()
                    : "#" + text();
        }
    }

}
