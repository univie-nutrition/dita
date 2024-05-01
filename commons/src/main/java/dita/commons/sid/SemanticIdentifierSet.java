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
package dita.commons.sid;

import java.util.Collections;
import java.util.Set;

import org.springframework.lang.Nullable;

public record SemanticIdentifierSet(Set<SemanticIdentifier> elements) {
    
    private static final SemanticIdentifierSet EMPTY = new SemanticIdentifierSet(Collections.emptySet());
    
    public static SemanticIdentifierSet empty() {
        return EMPTY;
    }
    
    public static SemanticIdentifierSet nullToEmpty(@Nullable SemanticIdentifierSet set) {
        return set!=null
                ? set
                : EMPTY;
    }
}
