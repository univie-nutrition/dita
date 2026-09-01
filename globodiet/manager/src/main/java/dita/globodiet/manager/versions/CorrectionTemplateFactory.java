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
package dita.globodiet.manager.versions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.Assert;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Pair;
import dita.commons.util.NumberUtils;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved;
import dita.globodiet.manager.versions.FdmDiffFactory.FdmDiff;
import dita.recall24.dto.Annotated;
import dita.recall24.dto.Annotated.Annotation;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Correction24.CompositeCorr;
import dita.recall24.dto.Correction24.CompositeCorr.Addition;
import dita.recall24.dto.Correction24.CompositeCorr.Deletion;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.Composite;
import dita.recall24.dto.Record24.Consumption;

public record CorrectionTemplateFactory(FdmDiff fdmDiff) {

	public Correction24 create(final InterviewSet24 interviewSet) {

        var corrs = new ArrayList<CompositeCorr>();
        interviewSet.transform(new RecallNode24.Transfomer() {
			@Override
			public <T extends RecallNode24> T transform(final T node) {
				if(node instanceof Record24.Composite composite) {
	                correctionFor(composite).ifPresent(corrs::add);
				}
				return node;
			}
        });

        var corr24 = new Correction24(null, null, corrs);
		return corr24;
	}

	/// For each composite consumption we check whether it is affected by changes as reported by the diff.
	/// * recipe name (typos) or group may have changed
	/// * the recipe diff may include additions, that are not seen reflected in the current consumption
	/// * the recipe diff may include deletions, that are not seen reflected in the current consumption
	/// Based on an analysis, we generate a Correction24 instance, that records all potentially required changes
	private Optional<CompositeCorr> correctionFor(final Composite composite) {
        final var recipeSid = composite.sid();
        // recipe that had changed between FDM versions
        final var recipeChange = fdmDiff.recipeChangeFor(recipeSid)
        		.orElse(null);
        if(recipeChange==null)
        	return Optional.empty();

        final var compWrapper = new CompositeWrapper(composite, this::facetLiteral);
        final var builder = new CompositeCorrBuilder(composite, this::facetLiteral);

        recipeChange.nameChange()
        	.map(Pair::left)
        	.ifPresent(builder.rename()::set);
//XXX DISABLED
//        recipeChange.groupChange()
//	    	.map(Pair::left)
//	    	.ifPresent(builder.newGroupSid()::set);

        compWrapper.ingredients().stream()
	        .forEach(food->{
	        	var ingrKey = compWrapper.keyForFood(food);
	        	var ingredientAdded = recipeChange.lookupAdditions(ingrKey).orElse(null);
	        	var ingredientRemoved = recipeChange.lookupDeletions(ingrKey).orElse(null);
	        	var ingredientChanged = recipeChange.lookupChanges(ingrKey).orElse(null);
	        	final int nonNullCount = (ingredientAdded!=null ? 1 : 0)
	        			+ (ingredientRemoved!=null ? 1 : 0)
	        			+ (ingredientChanged!=null ? 1 : 0);
	        	Assert.isTrue(nonNullCount<=1, ()->"inconsitent number of changes on same key, "
	        			+ "can at most be of one kind");
	        	if(nonNullCount==0)
	        		return;

	        	if(ingredientAdded!=null) {
					// an ingredient was added to the recipe, that has the same ingredient key as the food
	        		// probably fine to skip
	        	} else if (ingredientRemoved!=null) {
	        		// an ingredient was removed from the recipe, that has the same ingredient key as the food
	        		builder.del(ingredientRemoved, "ingredient was removed from the recipe in the FDM");
	        	} else if(ingredientChanged!=null) {
	        		// an ingredient was changed, that has the same ingredient key as the food
	        		final BigDecimal newAmount = NumberUtils.totalTimesPermillion(
	        				compWrapper.amountConsumedTotal(),
	        				ingredientChanged.left().relativeMassPermille());
	        		builder.change(ingredientChanged, newAmount);
	        	}
	        });
        // for each ingredient that was added to the recipe, but is not in the composite -> do add
        var keysAlreadyPartOfTheCompositeReported = compWrapper.keySet();
        recipeChange.ingredientsAdded().stream()
        	.filter(ingr->!keysAlreadyPartOfTheCompositeReported.contains(ingr.key()))
        	.forEach(ingr->{
        		final BigDecimal newAmount = NumberUtils.totalTimesPermillion(
        				compWrapper.amountConsumedTotal(),
        				ingr.relativeMassPermille());
        		builder.add(ingr, newAmount, "ingredient was added to the recipe in the FDM");
        	});

        return !builder.isEmpty()
    		? Optional.of(builder
    				.comments(compWrapper.comments())
    				.build())
			: Optional.empty();
    }

    /**
     * Small helper.
     *
     * We group current composite Food sub-records by SemanticIdentifier.
     */
    private record CompositeWrapper(
    		Composite composite,
    		List<String> facetLiterals,
            List<String> notes,
            BigDecimal amountConsumedTotal,
            List<Consumption> ingredients,
            Function<SemanticIdentifier, String> facetLiteralProvider) {
        CompositeWrapper(final Composite composite, final Function<SemanticIdentifier, String> facetLiteralProvider) {
            this(composite,
        		composite.facetSids().elements()
	    			.map(facetLiteralProvider)
	    			.toList(),
                notes(composite),
                streamConsumptions(composite)
                    .map(Consumption::amountConsumed)
                    .reduce(BigDecimal.ZERO, BigDecimal::add),
                streamConsumptions(composite)
	                .toList(),
                facetLiteralProvider);
        }
        RecipeIngredientResolved.Key keyForFood(final Consumption food) {
        	return new RecipeIngredientResolved.Key(composite.sid(), food.sid(), food.facetSids().hashCode());
        }
        Set<RecipeIngredientResolved.Key> keySet() {
        	return ingredients.stream().map(this::keyForFood).collect(Collectors.toSet());
        }
        private List<String> comments() {
        	var comments = new ArrayList<String>();
            comments.add("ingredients consumed:");
            ingredients()
            	.forEach(ingrCons->{
            		comments.add("- %s %s %s (%s) {%s}"
            			.formatted(
        					formatDecimal(ingrCons.amountConsumed()),
        					ingrCons.consumptionUnit(),
	                        ingrCons.name(), ingrCons.sid().objectId().toString(),
	                        formatFacets(ingrCons.facetSids())));
            	});
            comments.add("amount-consumed-total: %.2fg"
            		.formatted(amountConsumedTotal().doubleValue()));
			return comments;
		}
        @SuppressWarnings("unchecked")
        private static List<String> notes(final Composite composite) {
            return composite.lookupAnnotation(Annotated.NOTES)
                .map(Annotation::value)
                .map(x->(List<String>)x)
                .orElseGet(List::of);
        }
        private static Stream<Consumption> streamConsumptions(final Composite composite) {
            return composite.subRecords().stream()
                .filter(Consumption.class::isInstance)
                .map(Consumption.class::cast);
        }
    	private String formatFacets(final SemanticIdentifierSet sids) {
            if(sids.elements().isEmpty())
            	return "";
            return "%s (%s)".formatted(
                    sids.shortFormat(","),
                    sids.elements().map(facetLiteralProvider).join(", "));
        }
    }

    private record CompositeCorrBuilder(
    		Composite composite,
    		Function<SemanticIdentifier, String> facetLiteralProvider,
    		AtomicReference<String> rename,
    		AtomicReference<SemanticIdentifier> newGroupSid,
    		List<Addition> additions,
    		List<Deletion> deletions,
    		List<String> comments) {
    	CompositeCorrBuilder(final Composite composite,
    			final Function<SemanticIdentifier, String> facetLiteralProvider) {
			this(composite, facetLiteralProvider,
					new AtomicReference<>(), new AtomicReference<>(),
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    	}
    	public CompositeCorrBuilder comments(final List<String> comments) {
    		this.comments.addAll(comments);
			return this;
		}
		boolean isEmpty() {
    		return rename.get()==null
    				&& newGroupSid.get()==null
					&& additions.isEmpty()
					&& deletions.isEmpty();
		}
		void add(final RecipeIngredientResolved recipeIngr, final BigDecimal newAmount,
				final String secondaryComment) {
    		additions().add(new Addition(
    				recipeIngr.foodSid(),
    				recipeIngr.foodFacetSids(),
    				NumberUtils.reducedPrecision(newAmount, 2),
    				List.of(
						"ADD " + formatNameAndFacets(recipeIngr),
						secondaryComment)));
    	}
    	void del(final RecipeIngredientResolved recipeIngr, final String secondaryComment) {
    		deletions().add(new Deletion(
    				recipeIngr.foodSid(),
    				recipeIngr.foodFacetSids(),
    				List.of(
						"DEL " + formatNameAndFacets(recipeIngr),
						secondaryComment)));
    	}
    	void change(final Pair<RecipeIngredientResolved, RecipeIngredientResolved> ingredientChange, final BigDecimal newAmount) {
    		int oldPpm = ingredientChange.right().relativeMassPermille();
    		int newPpm = ingredientChange.left().relativeMassPermille();
    		add(ingredientChange.left(), newAmount,
    				"amount changed in FDM %sg -> %sg (%s%% %s%%)"
					.formatted(
							formatDecimal(ingredientChange.right().amountGrams()),
							formatDecimal(ingredientChange.left().amountGrams()),
							formatDecimal(BigDecimal.valueOf(oldPpm).movePointLeft(4)), // converts ppm to percent 10^-6 -> 10^-2
							formatDecimal(BigDecimal.valueOf(newPpm).movePointLeft(4))));
    		del(ingredientChange.left(), "change of amount");
		}
		CompositeCorr build() {
    		var coors = CompositeCorr.Coordinates.of(composite);
    		return new CompositeCorr(coors, rename.get(), newGroupSid.get(),
    				additions, deletions, comments);
    	}
		// -- HELPER
    	private String formatNameAndFacets(final RecipeIngredientResolved ingrResolved) {
            return "name: %s, facets: %s"
					.formatted(
							ingrResolved.food().name(),
							formatFacets(ingrResolved.foodFacetSids()));
        }
    	private String formatFacets(final SemanticIdentifierSet sids) {
            if(sids.elements().isEmpty())
            	return "";
            return "%s (%s)".formatted(
                    sids.shortFormat(","),
                    sids.elements().map(facetLiteralProvider).join(", "));
        }
    }

    private String facetLiteral(final SemanticIdentifier sid) {
        return Optional.ofNullable(
            fdmDiff.mainFdm()
                .classificationFacetBySid()
                .get(sid)
    		)
            .map(FoodDescriptionModel.ClassificationFacet::name)
            .orElse(sid.toStringNoBox());
    }

    private static String formatDecimal(final BigDecimal bd) {
    	return NumberUtils.reducedPrecision(bd, 2).toPlainString();
    }

}
