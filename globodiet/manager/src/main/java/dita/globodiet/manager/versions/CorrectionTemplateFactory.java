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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Diff;
import dita.commons.types.Pair;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.Recipe;
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
import dita.recall24.dto.Record24.Food;

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

	private Optional<CompositeCorr> correctionFor(final Composite composite) {

        final var recipeSid = composite.sid();

        final var ingredientDiff = fdmDiff.ingredientDiffByRecipeSid()
            .getOrDefault(recipeSid, Diff.empty());

        final var recipeChangeOpt = recipeChangeFor(recipeSid);
        final var facts = new CompositeFacts(composite);

        final var affectedFoodSids = new TreeSet<SemanticIdentifier>();

        // 1) Ingredients added in main FDM.
        ingredientDiff.leftOuter().forEach(ingr -> {
            if (ingr.foodSid() != null) {
                affectedFoodSids.add(ingr.foodSid());
            }
        });

        // 2) Ingredients removed from main FDM.
        ingredientDiff.rightOuter().forEach(ingr -> {
            if (ingr.foodSid() != null) {
                affectedFoodSids.add(ingr.foodSid());
            }
        });

        // 3) Ingredients present in both versions, but changed.
        //
        // We deliberately do NOT treat pure absolute-amount changes as meaningful.
        // Instead, we compare relative mass.
        ingredientDiff.innerMismatch().forEach(pair -> {
            final var mainIng = pair.left();
            final var baseIng = pair.right();

            if (isMeaningfulIngredientChange(mainIng, baseIng)) {
                if (mainIng.foodSid() != null) {
                    affectedFoodSids.add(mainIng.foodSid());
                }
                if (baseIng.foodSid() != null) {
                    affectedFoodSids.add(baseIng.foodSid());
                }
            }
        });

        if (affectedFoodSids.isEmpty() && recipeChangeOpt.isEmpty())
			return Optional.empty();

        final var additions = new ArrayList<Addition>();
        final var deletions = new ArrayList<Deletion>();
        final var comments = new ArrayList<String>();

        String rename = null;
        SemanticIdentifier newGroupSid = null;

        var change = recipeChangeOpt.orElse(null);

        if(change!=null) {
            if (!Objects.equals(change.baseName(), change.mainName())) {
                rename = change.mainName();
                comments.add("RENAME %s -> %s".formatted(change.baseName(), change.mainName()));
            }

            if (!Objects.equals(change.baseGroupSid(), change.mainGroupSid())) {
                newGroupSid = change.mainGroupSid();
                comments.add("GROUP CHANGE %s -> %s".formatted(
                    change.baseGroupSid() != null ? change.baseGroupSid().toStringNoBox() : "∅",
                    change.mainGroupSid() != null ? change.mainGroupSid().toStringNoBox() : "∅"
                ));
            }
        }

        final var mainIngredients = mainIngredientsFor(recipeSid);

        // We replace by foodSid.
        //
        // This is intentional:
        // - Correction24.Deletion only has a sid, no facets.
        // - The same foodSid may occur multiple times with different facets.
        // - If any variant of that foodSid changed, replacing all variants with the
        //   main-FDM variants is the safest delete + add strategy.
        for (final var foodSid : affectedFoodSids) {

            final var currentFoods = facts.foodsBySid(foodSid);

            final var targetFoods = mainIngredients.stream()
                .filter(ingr -> Objects.equals(ingr.foodSid(), foodSid))
                .toList();

            // Delete current ingredient(s) for this SID, if any.
            if (!currentFoods.isEmpty()) {
                deletions.add(new Deletion(foodSid));

                final var currentNames = currentFoods.stream()
                    .map(Food::name)
                    .collect(Collectors.joining(","));

                comments.add("CORR DELETE affected ingredient(s): %s [%s]"
                    .formatted(foodSid.toStringNoBox(), currentNames));
            }

            // Add new main-FDM ingredient(s) for this SID.
            for (final var target : targetFoods) {
                final var amount = target.amountGrams() != null
                    ? target.amountGrams()
                    : BigDecimal.ZERO;

                final var facets = target.foodFacetSids() != null
                    ? target.foodFacetSids().toStringNoBox()
                    : "";

                final var additionComments = List.of(
                    "main FDM ingredient: %s [%s]"
                        .formatted(target.food().name(), facets)
                );

                additions.add(new Addition(
                    target.foodSid(),
                    target.foodFacetSids(),
                    amount,
                    additionComments
                ));

                comments.add("CORR ADD affected ingredient: %s %s [%s]"
                    .formatted(foodSid.toStringNoBox(), amount, facets));
            }
        }

        if (additions.isEmpty()
            && deletions.isEmpty()
            && rename == null
            && newGroupSid == null)
			return Optional.empty();

        final var coordinates = CompositeCorr.Coordinates.of(composite);

        return Optional.of(new CompositeCorr(
            coordinates,
            rename,
            newGroupSid,
            additions,
            deletions,
            comments
        ));
    }

    private boolean isMeaningfulIngredientChange(
        final RecipeIngredientResolved main,
        final RecipeIngredientResolved base) {

        // Same food identity?
        if (!Objects.equals(main.foodSid(), base.foodSid()))
			return true;

        // Facets are part of the ingredient key, but include this check defensively.
        if (!Objects.equals(main.foodFacetSids(), base.foodFacetSids()))
			return true;

        // Relative amount changed?
        if (main.relativeMassPermille() != base.relativeMassPermille())
			return true;

        // Raw-to-cooked coefficient changed?
        if (!Objects.equals(main.rawToCookedCoefficient(), base.rawToCookedCoefficient()))
			return true;

        // Pure absolute amount changes are ignored on purpose.
        return false;
    }

    private List<RecipeIngredientResolved> mainIngredientsFor(final SemanticIdentifier recipeSid) {
        return fdmDiff.mainFdm()
            .lookupRecipeBySid(recipeSid)
            .map(recipe -> fdmDiff.mainFdm().streamIngredients(recipe).toList())
            .orElseGet(List::of);
    }

    private Optional<RecipeChange> recipeChangeFor(final SemanticIdentifier recipeSid) {
        final var main = fdmDiff.mainFdm().lookupRecipeBySid(recipeSid).orElse(null);
        final var base = baseRecipeFor(recipeSid);

        if (main == null || base == null)
			return Optional.empty();

        final boolean nameChanged = !Objects.equals(main.name(), base.name());
        final boolean groupChanged = !Objects.equals(main.groupSid(), base.groupSid());

        if (!nameChanged && !groupChanged)
			return Optional.empty();

        return Optional.of(new RecipeChange(
            base.name(),
            main.name(),
            base.groupSid(),
            main.groupSid()
        ));
    }

    private Recipe baseRecipeFor(final SemanticIdentifier recipeSid) {
        final var pairs = Stream.concat(
            fdmDiff.recipeDiff().innerMatch().stream(),
            fdmDiff.recipeDiff().innerMismatch().stream()
        );

        return pairs
            .map(Pair::right)
            .filter(Objects::nonNull)
            .filter(recipe -> Objects.equals(recipe.sid(), recipeSid))
            .findFirst()
            .orElse(null);
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

    private record RecipeChange(
        String baseName,
        String mainName,
        SemanticIdentifier baseGroupSid,
        SemanticIdentifier mainGroupSid
    ) {
    }

    /**
     * Small helper replacing the old Occurrence class.
     *
     * We group current composite Food sub-records by SemanticIdentifier.
     */
    private static class CompositeFacts {

        private final Map<SemanticIdentifier, List<Food>> foodsBySid;

        CompositeFacts(final Composite composite) {
            this.foodsBySid = composite.subRecords()
                .stream()
                .filter(Food.class::isInstance)
                .map(Food.class::cast)
                .filter(food -> food.sid() != null)
                .collect(Collectors.groupingBy(Food::sid));
        }

        List<Food> foodsBySid(final SemanticIdentifier sid) {
            return foodsBySid.getOrDefault(sid, List.of());
        }
    }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	/// For each composite consumption we check whether it is affected by changes as reported by the diff.
	/// * recipe name (typos) or group may have changed
	/// * the recipe diff may include additions, that are not seen in the current consumption
	/// * the recipe diff may include deletions, that are not seen in the current consumption
	/// Based on an analysis, we generate a Correction24 instance, that records all potentially required changes
	Optional<CompositeCorr> correctionFor2(final Composite composite) {
		var ingredientDiff = fdmDiff.ingredientDiffByRecipeSid().getOrDefault(composite.sid(), Diff.empty());
		if(ingredientDiff.leftOuter().size()==0
				&& ingredientDiff.rightOuter().size()==0)
			//TODO check for changes also
			return Optional.empty(); // skip

		var occurrence = new Occurrence(composite, this::facetLiteral);

		var coors = CompositeCorr.Coordinates.of(composite);
        String rename = null;
        SemanticIdentifier newGroupSid = null;

        var additions = calculateCorrectionAdditions(occurrence, ingredientDiff.leftOuter());
        var deletions = calculateCorrectionDeletions(occurrence, ingredientDiff.rightOuter());

		return Optional.of(new CompositeCorr(coors, rename, newGroupSid,
				additions, deletions, occurrence.comments()));
	}

	List<Addition> calculateCorrectionAdditions(final Occurrence occurrence,
			final List<RecipeIngredientResolved> ingredientsAddedInMain) {
		var ingredientDiff = Diff.typed(RecipeIngredientResolved.class, Consumption.class);
		ingredientDiff.process(ingredientsAddedInMain, occurrence.ingredientConsumptions(),
				RecipeIngredientResolved::key, occurrence::key, (a, b) -> true); //TODO flesh out a proper equality relation based on key and relative amount

		return ingredientDiff.leftOuter().stream()
			.map(ingr -> new Addition(
					ingr.foodSid(),
					ingr.foodFacetSids(),
					ingr.amountGrams(),
					List.of(ingr.food().name())))
			.toList();
	}

	List<Deletion> calculateCorrectionDeletions(final Occurrence occurrence,
			final List<RecipeIngredientResolved> ingredientsRemovedFromMain) {
		var ingredientDiff = Diff.typed(RecipeIngredientResolved.class, Consumption.class);
		ingredientDiff.process(ingredientsRemovedFromMain, occurrence.ingredientConsumptions(),
				RecipeIngredientResolved::key, occurrence::key, (a, b) -> true); //TODO flesh out a proper equality relation based on key and relative amount

		return ingredientDiff.leftOuter().stream()
			.map(ingr -> new Deletion(
	        		ingr.foodSid(),
	        		ingr.foodFacetSids(),
	        		List.of(ingr.food().name())))
			.toList();
	}

    FoodDescriptionModel fdm() {
    	return fdmDiff.mainFdm();
    }

//    String facetLiteral(final SemanticIdentifier sid) {
//        return Optional.ofNullable(
//        		fdm().classificationFacetBySid()
//                    .get(sid))
//                .map(ClassificationFacet::name)
//                .orElse(sid.toStringNoBox());
//    }

    private record Occurrence(
            CompositeCorr.Coordinates coors,
            Composite composite,
            List<String> facetLiterals,
            List<String> notes,
            BigDecimal amountConsumedTotal,
            List<Consumption> ingredientConsumptions,
            Function<SemanticIdentifier, String> facetLiteralProvider) {
    	private Occurrence(
        		final Composite composite,
        		final Function<SemanticIdentifier, String> facetLiteralProvider){
            this(CompositeCorr.Coordinates.of(composite),
                composite,
                composite.facetSids().elements()
	    			.map(facetLiteralProvider)
	    			.toList(),
                notes(composite),
                streamConsumptions(composite)
                    .map(Consumption::amountConsumed)
                    .reduce(BigDecimal.ZERO, BigDecimal::add),
                streamConsumptions(composite).toList(),
                facetLiteralProvider);
        }
    	RecipeIngredientResolved.Key key(final Consumption consumption) {
			return new RecipeIngredientResolved.Key(composite.sid(), consumption.sid(), 0); //TODO the ordinal has no
    	}
    	private List<String> comments() {
        	var comments = new ArrayList<String>();
            comments.add("ingredients consumed:");
            ingredientConsumptions()
            	.forEach(ingrCons->{
            		comments.add("- %s %s %s (%s) {%s}"
            			.formatted(
	                        ingrCons.amountConsumed(), ingrCons.consumptionUnit(),
	                        ingrCons.name(), ingrCons.sid().objectId().toString(),
	                        formatFacets(ingrCons.facetSids())));
            	});
            comments.add("amount-consumed-total: %.2fg"
            		.formatted(amountConsumedTotal().doubleValue()));
			return comments;
		}
    	private String formatFacets(final SemanticIdentifierSet sids) {
            if(sids.elements().isEmpty())
            	return "";
            return "%s (%s)".formatted(
                    sids.shortFormat(","),
                    sids.elements().map(facetLiteralProvider).join(", "));
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
    }

}
