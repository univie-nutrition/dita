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
package dita.globodiet.params.util;

import org.springframework.lang.Nullable;

import lombok.NonNull;

import dita.globodiet.params.pathway.QuantificationMethodPathwayForFood;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForFoodGroup;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipe;
import dita.globodiet.params.pathway.QuantificationMethodPathwayForRecipeGroup;

public record QuantificationMethodPathwayKey(
        @NonNull QuantificationMethod quantificationMethod,
        @Nullable String photoOrShapeCode) {

    public enum QuantificationMethod {
        PHOTO,
        HOUSEHOLD_MEASURE,
        STANDARD_UNIT,
        STANDARD_PORTION,
        SHAPE;

        // -- FACTORIES

        public static QuantificationMethod valueOf(final QuantificationMethodPathwayForFoodGroup.QuantificationMethod qm) {
            return QuantificationMethod.valueOf(qm.name());
        }

        public static QuantificationMethod valueOf(final QuantificationMethodPathwayForFood.QuantificationMethod qm) {
            return QuantificationMethod.valueOf(qm.name());
        }

        public static QuantificationMethod valueOf(final QuantificationMethodPathwayForRecipeGroup.QuantificationMethod qm) {
            return QuantificationMethod.valueOf(qm.name());
        }

        public static QuantificationMethod valueOf(final QuantificationMethodPathwayForRecipe.QuantificationMethod qm) {
            return QuantificationMethod.valueOf(qm.name());
        }

        // -- PREDICATES

        public boolean isPhoto() { return this == PHOTO; }
        public boolean isShape() { return this == SHAPE; }
        public boolean isPhotoOrShape() {
            return isPhoto()
                    || isShape();
        }
    }

    // -- FOOD

    public static QuantificationMethodPathwayKey valueOf(final QuantificationMethodPathwayForFood qmp) {
        return new QuantificationMethodPathwayKey(
                QuantificationMethod.valueOf(qmp.getQuantificationMethod()),
                qmp.getPhotoOrShapeCode());
    }

    public static QuantificationMethodPathwayKey valueOf(final QuantificationMethodPathwayForFoodGroup qmp) {
        return new QuantificationMethodPathwayKey(
                QuantificationMethod.valueOf(qmp.getQuantificationMethod()),
                qmp.getPhotoOrShapeCode());
    }

    // -- RECIPE

    public static QuantificationMethodPathwayKey valueOf(final QuantificationMethodPathwayForRecipe qmp) {
        return new QuantificationMethodPathwayKey(
                QuantificationMethod.valueOf(qmp.getQuantificationMethod()),
                qmp.getPhotoOrShapeCode());
    }

    public static QuantificationMethodPathwayKey valueOf(final QuantificationMethodPathwayForRecipeGroup qmp) {
        return new QuantificationMethodPathwayKey(
                QuantificationMethod.valueOf(qmp.getQuantificationMethod()),
                qmp.getPhotoOrShapeCode());
    }

}
