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
import java.util.function.Function;
import java.util.stream.Stream;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Diff;
import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.ClassificationFacet;
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

					// for each composite consumption we check whether it is affected by changes as reported by the diff.
					// (1) recipe name (typos) or group may have changed
					// (2) the recipe diff may include additions, that are not seen in the current consumption
					// (3) the recipe diff may include deletions, that are not seen in the current consumption
					// based on an analysis, we generate a Correction24 instance, that records all potentially required changes

					// ad 2) needs left outer of diff(additions, ingredients)
					// ad 3) needs left outer of diff(deletions, ingredients)

					var ingredientDiff = fdmDiff.ingredientDiffByRecipeSid().getOrDefault(composite.sid(), Diff.empty());
					if(ingredientDiff.leftOuter().size()==0
							&& ingredientDiff.rightOuter().size()==0)
						//TODO check for changes also
						return node; // skip

					var occurrence = new Occurrence(composite, sid->facetLiteral(sid));


					var coors = CompositeCorr.Coordinates.of(composite);
		            String rename = null;
		            SemanticIdentifier newGroupSid = null;

	                var additions = calculateCorrectionAdditions(occurrence, ingredientDiff.leftOuter());
	                var deletions = calculateCorrectionDeletions(occurrence, ingredientDiff.rightOuter());

					corrs.add(new CompositeCorr(coors, rename, newGroupSid,
							additions, deletions, occurrence.comments()));
				}
				return node;
			}
        });

        var corr24 = new Correction24(null, null, corrs);
		return corr24;
	}

	List<Addition> calculateCorrectionAdditions(final Occurrence occurrence,
			final List<RecipeIngredientResolved> ingredientsAddedInMain) {
		var ingredientDiff = Diff.typed(RecipeIngredientResolved.class, Consumption.class);
		ingredientDiff.process(ingredientsAddedInMain, occurrence.ingredientConsumptions(),
				RecipeIngredientResolved::key, occurrence::key, (a, b) -> true); //TODO flesh out a proper equality relation based on key and relative amount

		return ingredientDiff.leftOuter().stream()
			.map(ingr -> new Addition(
					ingr.foodSid(),
					ingr.amountGrams(),
					ingr.foodFacetSids(),
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
	        		List.of(ingr.food().name())))
			.toList();
	}

    FoodDescriptionModel fdm() {
    	return fdmDiff.mainFdm();
    }

    String facetLiteral(final SemanticIdentifier sid) {
        return Optional.ofNullable(
        		fdm().classificationFacetBySid()
                    .get(sid))
                .map(ClassificationFacet::name)
                .orElse(sid.toStringNoBox());
    }

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
