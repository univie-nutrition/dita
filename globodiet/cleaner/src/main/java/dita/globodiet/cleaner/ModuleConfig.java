/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// Auto-generated by Causeway-Stuff code generator.
package dita.globodiet.cleaner;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import dita.globodiet.cleaner.dom.ConsumptionDataCleanerManager_createCleaner;
import dita.globodiet.cleaner.dom.ConsumptionDataDefectManager_addDefect;
import dita.globodiet.cleaner.dom.ConsumptionDataDefect_editInstruction;
import dita.globodiet.cleaner.dom.ConsumptionDataDefect_instructionView;

@Configuration
@Import({
    ConsumptionDataDefectManager_addDefect.class,
    ConsumptionDataCleanerManager_createCleaner.class,
    ConsumptionDataDefect_instructionView.class,
    ConsumptionDataDefect_editInstruction.class,
})
public class ModuleConfig {
}
