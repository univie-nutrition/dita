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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.causeway.commons.functional.ThrowingRunnable;
import org.apache.causeway.core.metamodel.context.MetaModelContext;

import io.github.causewaystuff.commons.base.types.NamedPath;

record ReportJobExecutor(
    ExecutorService executor,
    Set<NamedPath> runningJobs) {

    ReportJobExecutor(){
        this(Executors.newSingleThreadExecutor(), new HashSet<>());
    }

    void run(final NamedPath namedPath, final ThrowingRunnable runnable) {
        runningJobs.add(namedPath);
        executor.submit(()->{
            try {
                MetaModelContext.instanceElseFail().getInteractionService().runAnonymous(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                runningJobs.remove(namedPath);
            }
        });
    }
}