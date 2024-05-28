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
package dita.recall24.api;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Casts;

public sealed interface RecallNode24
permits
    InterviewSet24,
    Respondent24,
    Interview24,
    Meal24,
    MemorizedFood24,
    Record24 {

    <T extends RecallNode24> Builder24<T> asBuilder();

    public interface Builder24<T extends RecallNode24> {
        T build();
    }

    public interface Transfomer extends Consumer<RecallNode24.Builder24<?>> {
    }

    // -- ANNOTATIONS

    public record Annotation(String key, Serializable value) implements Serializable {
        public static <T extends Serializable> Function<Annotation, T> valueAs(final Class<T> requiredType) {
            return annot->_Casts.<T>uncheckedCast(annot.value());
        }
        public static <E extends Serializable> Function<Annotation, Can<E>> valueAsCan(final Class<E> requiredElementType) {
            return annot->_Casts.<Can<E>>uncheckedCast(annot.value());
        }
    }

    default Map<String, Annotation> annotations() {
        return Collections.emptyMap();
    }

    default Optional<Annotation> annotation(final String key) {
        return Optional.ofNullable(annotations().get(key));
    }

}
