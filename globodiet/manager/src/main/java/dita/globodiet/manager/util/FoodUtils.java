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
package dita.globodiet.manager.util;

import org.springframework.lang.Nullable;

import org.apache.causeway.commons.internal.base._NullSafe;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FoodUtils {

    /**
     * Maps null to zero.
     */
    public int foodCodeToInt(final @Nullable String foodCode) {
        if(foodCode==null) return 0;
        return Integer.parseInt(foodCode);
    }

    /**
     * Returns a fixed length String, that is 5 digits.
     */
    public String foodCodeFromInt(final int number) {
        var foodCode = "" + number;
        while(foodCode.length()<5) {
            foodCode = "0" + foodCode;
        }
        return foodCode;
    }

    /**
     * If validation fails returns a string describing what went wrong, otherwise returns null.
     */
    public String validateFoodCode(final @Nullable String foodCode) {
        if(_NullSafe.size(foodCode)!=5) {
            return "Food Code must have exactly 5 digits.";
        }
        if(!foodCode.matches("^[0-9]+$")) {
            return "Food Code must have digits (0..9) only.";
        }
        return null;
    }

}
