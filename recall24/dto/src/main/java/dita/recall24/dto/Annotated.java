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
package dita.recall24.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Casts;

public interface Annotated {

    public record Annotation(String key, Serializable value) implements Serializable {
        public static <T extends Serializable> Function<Annotation, T> valueAs(final Class<T> requiredType) {
            return annot->_Casts.<T>uncheckedCast(annot.value());
        }
        public static <E extends Serializable> Function<Annotation, Can<E>> valueAsCan(final Class<E> requiredElementType) {
            return annot->_Casts.<Can<E>>uncheckedCast(annot.value());
        }
    }

    Map<String, Serializable> annotations();

    default Optional<Annotation> lookupAnnotation(final String key) {
        return Optional.ofNullable(annotations().get(key)).map(value->new Annotation(key, value));
    }

    /** replaces any previous mapping under this key */
    default void putAnnotation(final String key, final Serializable value) {
        annotations().put(key, value);
    }
    
    default void putAnnotation(@Nullable Annotation annotation) {
        if(annotation==null) return;
        annotations().put(annotation.key(), annotation.value());
    }
    
    default void putAnnotations(@Nullable Iterable<Annotation> annotations) {
        if(annotations==null) return;
        annotations.forEach(this::putAnnotation);
    }
    
    default Stream<Annotation> streamAnnotations() {
        var map = annotations();
        if(map==null
            || map.isEmpty()) {
            return Stream.empty();
        }
        return map.entrySet().stream().map(entry->new Annotation(entry.getKey(), entry.getValue()));
    }
}
