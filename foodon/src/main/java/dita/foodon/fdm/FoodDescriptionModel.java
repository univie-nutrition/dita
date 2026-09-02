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
package dita.foodon.fdm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;

/// Provides a set of food ontologies,
/// that allow to describe consumed food,
/// including categorization (grouping) and descriptive facets.
public record FoodDescriptionModel(
        Map<SemanticIdentifier, Food> foodBySid,
        Map<SemanticIdentifier, Recipe> recipeBySid,
        Map<SemanticIdentifier, List<RecipeIngredientResolved>> ingredientsByRecipeSid,
        Map<SemanticIdentifier, ClassificationFacet> classificationFacetBySid
        ) {

    public sealed interface HasAttributes
    permits FoodDescriptionModel.Food, FoodDescriptionModel.Recipe {
        Map<String, String> attributes();
        /// @returns optionally the attribute value corresponding to given attributeName
        default Optional<String> attribute(final String attributeName) {
            return _Strings.nonEmpty(attributes().get(attributeName));
        }
    }

    public record Food(
            SemanticIdentifier sid,
            String name,
            SemanticIdentifier groupSid,
            Map<String, String> attributes) implements HasAttributes {
    }

    public record Recipe(
            SemanticIdentifier sid,
            String name,
            SemanticIdentifier groupSid,
            Map<String, String> attributes) implements HasAttributes {
    }

    public record RecipeIngredient(
            SemanticIdentifier recipeSid,
            SemanticIdentifier foodSid,
            SemanticIdentifierSet foodFacetSids,
            /**
             * Amount consumed in gram.
             */
            BigDecimal amountGrams,
            BigDecimal rawToCookedCoefficient) {
    }

    public record RecipeIngredientResolved(
    		Key key,
    		Recipe recipe,
    		Food food,
    		int relativeMassPermille,
    		RecipeIngredient data) {

    	public RecipeIngredientResolved {
    		Assert.isTrue(relativeMassPermille>0, ()->"relativeMassPermille must be greater than zero");
    	}

    	public RecipeIngredientResolved(
        		final Recipe recipe,
        		final Food food,
        		final int relativeMassPermillion,
        		final RecipeIngredient data) {
    		this(new Key(recipe.sid(), food.sid(), data.foodFacetSids().hashCode()), recipe, food, relativeMassPermillion, data);
    	}

    	public record Key(
    			SemanticIdentifier recipeSid,
    			SemanticIdentifier foodSid,
    			/**
    			 * unique hash generated from facets
    			 */
    			int facetHash) {
    		@Override
    		public final String toString() {
    			return recipeSid.toStringNoBox() + "::" + foodSid.toStringNoBox() + "::" + facetHash;
    		}
    	}
       	public SemanticIdentifier recipeSid() { return data.recipeSid(); }
       	public SemanticIdentifier foodSid() { return data.foodSid(); }
       	public SemanticIdentifierSet foodFacetSids() { return data.foodFacetSids(); }
    	public BigDecimal amountGrams() { return data.amountGrams(); }
    	public BigDecimal rawToCookedCoefficient() { return data.rawToCookedCoefficient(); }

    	@Override public final boolean equals(final Object obj) {
    		return obj instanceof RecipeIngredientResolved other
				? this.key.equals(other.key)
					&& this.data.equals(other.data)
				: false;
    	}
    	@Override public final int hashCode() {
    		return Objects.hash(key, amountGrams());
    	}
    	@Override public final String toString() {
    		return "RecipeIngredientResolved[key=%s, amountGrams=%s]".formatted(key, amountGrams());
    	}
    }

    public record ClassificationFacet(
            SemanticIdentifier sid,
            String name) {
    }

    // -- FACTORIES

	/**
     * empty, but mutable
     */
    public static FoodDescriptionModel empty() {
        return new FoodDescriptionModel(
                new HashMap<SemanticIdentifier, Food>(),
                new HashMap<SemanticIdentifier, Recipe>(),
                new HashMap<SemanticIdentifier, List<RecipeIngredientResolved>>(),
                new HashMap<SemanticIdentifier, ClassificationFacet>());
    }

    // -- UTIL

    public Optional<Food> lookupFoodBySid(@Nullable final SemanticIdentifier sid) {
        return sid!=null
                ? Optional.ofNullable(foodBySid().get(sid))
                : Optional.empty();
    }

    public Optional<Recipe> lookupRecipeBySid(@Nullable final SemanticIdentifier sid) {
        return sid!=null
                ? Optional.ofNullable(recipeBySid().get(sid))
                : Optional.empty();
    }

    public Food lookupFoodBySidElseFail(@Nullable final SemanticIdentifier sid) {
        return lookupFoodBySid(sid)
                .orElseThrow(()->_Exceptions.illegalArgument("failed to resolve food for sid %s", sid));
    }

    public Recipe lookupRecipeBySidElseFail(@Nullable final SemanticIdentifier sid) {
        return lookupRecipeBySid(sid)
                .orElseThrow(()->_Exceptions.illegalArgument("failed to resolve recipe for sid %s", sid));
    }

    public String facetSetLiteral(final SemanticIdentifierSet sids) {
    	return sids.elements().map(this::facetLiteral).join(", ");
    }

    public String facetLiteral(final SemanticIdentifier sid) {
        return Optional.ofNullable(classificationFacetBySid()
                .get(sid))
            .map(FoodDescriptionModel.ClassificationFacet::name)
            .orElse(sid.toStringNoBox());
    }

    /**
     * Streams all ingredients of given recipe.
     */
    public Stream<RecipeIngredientResolved> streamIngredients(@Nullable final Recipe recipe) {
        return recipe!=null
                ? _NullSafe.stream(ingredientsByRecipeSid.get(recipe.sid()))
                : Stream.empty();
    }

    /**
     * Returns sum of amount gram consumed over all ingredients of given recipe.
     */
    public BigDecimal sumAmountGramsForRecipe(@Nullable final Recipe recipe) {
        return streamIngredients(recipe)
            .map(RecipeIngredientResolved::amountGrams)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // -- IO

    public static FoodDescriptionModel fromYaml(final String yaml) {
        return FdmUtils.fromYaml(yaml);
    }

    public String toYaml() {
        return FdmUtils.toYaml(this);
    }

}
