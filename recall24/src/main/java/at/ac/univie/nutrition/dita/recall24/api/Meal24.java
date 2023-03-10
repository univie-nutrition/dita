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
package at.ac.univie.nutrition.dita.recall24.api;

import java.time.LocalTime;

import org.apache.causeway.commons.collections.Can;

// TODO: Auto-generated Javadoc
/**
 * The Interface Meal24.
 */
public interface Meal24 {

    /**
     * Parent interview.
     */
    Interview24 parentInterview();

    /**
     * Hour of day, when this meal took place.
     */
    LocalTime hourOfDay();

    /**
     * Identifying the occasion, when this meal took place.
     */
    String foodConsumptionOccasionId();

    /**
     * Identifying the place, where this meal took place.
     */
    String foodConsumptionPlaceId();

    /**
     * Memorized food for this meal.
     */
    Can<? extends MemorizedFood24> memorizedFood();

}