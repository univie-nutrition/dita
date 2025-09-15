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

import java.util.concurrent.atomic.LongAdder;

import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;

/**
 * Adds a composite number of occurrence.
 *
 * <p>Phase 2 processes all the candidates as collected from phase 1.
 */
public record CompositeDebugRenamer() implements Transfomer {

    private static final LongAdder c = new LongAdder();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecallNode24> T transform(final T node) {
        return switch (node) {
            case Record24.Composite composite -> {
                c.increment();
                var builder = ((Composite.Builder) composite.asBuilder())
                        .name(composite.name() + "(" + c.longValue() + ")");
                builder.replaceSubRecords(this::transform); // recursive
                yield (T) builder.build();
            }
            default -> node;
        };
    }

}
