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
package dita.recall24.reporter.tabular;

import java.util.Stack;
import java.util.stream.IntStream;

import org.apache.causeway.commons.collections.Can;

import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Consumption;

class OrdinalTracker {
    int mealOrdinal;
    int[] consumptionOrdinal = new int[5]; // arbitrary composite max nesting level
    final Stack<Can<? extends Record24>> stack = new Stack<>();
    void nextMeal() {
        mealOrdinal++;
        indices(0)
            .forEach(i->consumptionOrdinal[i] = 0);
    }
    void nextComposite(final Composite comp) {
        shrinkStack(comp);
        inc();
        stack.push(comp.subRecords());
    }
    void nextConsumption(final Consumption cons) {
        shrinkStack(cons);
        inc();
    }
    private void shrinkStack(final Record24 record24) {
        while(!stack.isEmpty()) {
            var records = stack.peek();
            if(records.stream().anyMatch(x->x.equals(record24))) return;
            stack.pop();
        }
    }
    private void inc() {
        consumptionOrdinal[stack.size()]++;
        indices(stack.size() + 1)
            .forEach(i->consumptionOrdinal[i] = 0);
    }
    private IntStream indices(final int start) {
        if(start>=consumptionOrdinal.length) return IntStream.of();
        return IntStream.range(start, consumptionOrdinal.length);
    }

    String deweyOrdinal() {
        var dewey = "" + mealOrdinal;
        int i = 0;
        while(i<consumptionOrdinal.length
                && consumptionOrdinal[i]>0) {
            dewey += "." + consumptionOrdinal[i];
            i++;
        }
        return dewey;
    }

}
