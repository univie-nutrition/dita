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
package dita.commons.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.jspecify.annotations.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JoinUtils {

    public <T> List<T> joinUnique(final @Nullable List<T> list1, final @Nullable List<T> list2, final Comparator<T> comparator) {
        if(list2.isEmpty()) return list1;
        if(list1.isEmpty()) return list2;

        final var seen = new TreeSet<T>(comparator);
        final var result = new ArrayList<T>();

        for (T item : list1) {
            if (!seen.add(item)) {
                throw new IllegalArgumentException("Duplicate found in first list: " + item);
            }
            result.add(item);
        }

        for (T item : list2) {
            if (!seen.add(item)) {
                throw new IllegalArgumentException("Duplicate found across lists: " + item);
            }
            result.add(item);
        }

        return result;
    }
}
