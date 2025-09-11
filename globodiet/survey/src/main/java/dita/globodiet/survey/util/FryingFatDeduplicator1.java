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
package dita.globodiet.survey.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.apache.causeway.commons.internal.assertions._Assert;

import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.util.Recall24DtoUtils;
import io.github.causewaystuff.commons.base.types.internal.SneakyRef;

/**
 * Prevents FRYING FAT from being reported twice.
 *
 * <p>Phase 1 collects all the candidates.
 */
public record FryingFatDeduplicator1(
    SneakyRef<Stack<Composite>> compositeStack,
    List<FryingFatHandler> fryingFatHandlers) implements Transfomer {

    public FryingFatDeduplicator1() {
        this(SneakyRef.of(new Stack<>()), new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {
        return switch (node) {
            case Record24.Composite composite -> {
                compositeStack.value().push(composite);
                composite.subRecords().forEach(this::transform); // recursive
                compositeStack.value().pop();
                yield (T) composite;
            }
            case Record24.FryingFat fryingFat -> {
                // find composite this fryingFat associates with, if any
                // (we ignore cases when associates with regular food)
                var record24 = Recall24DtoUtils.recordForFryingFat(currentComposite().orElse(null), fryingFat);
                if(record24 instanceof Composite composite) {
                    fryingFatHandlers.add(new FryingFatHandler(composite, fryingFat));
                }
                yield (T) fryingFat;
            }
            default -> node;
        };
    }

    public Map<Integer, FryingFatHandler> fryingFatHandlersAsMap(){
        var map = fryingFatHandlers.stream()
            .collect(Collectors.toMap(handler->System.identityHashCode(handler.composite()), UnaryOperator.identity()));
        _Assert.assertEquals(fryingFatHandlers.size(), map.size());
        return map;
    }

    // -- HELPER

    private Optional<Composite> currentComposite() {
        return compositeStack.value().isEmpty()
            ? Optional.empty()
            : Optional.of(compositeStack.value().peek());
    }

}
