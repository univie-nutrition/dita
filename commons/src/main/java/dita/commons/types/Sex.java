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
package dita.commons.types;

import org.jspecify.annotations.Nullable;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sex {
    UNCLASSIFIED,
    MALE,
    FEMALE;

    public boolean isUnclassified() { return this==UNCLASSIFIED; }
    public boolean isMale() { return this==MALE; }
    public boolean isFemale() { return this==FEMALE; }

    /**
     * 0=unclassified, 1=male, 2=female
     */
    public final int referenceOrdinal() {
        return ordinal();
    }

    public String stringify() {
        return name().toLowerCase();
    }

    @Nullable
    public static Sex destringify(final String stringified) {
        return StringUtils.hasLength(stringified)
                ? Sex.valueOf(stringified.toUpperCase())
                : null;
    }
}
