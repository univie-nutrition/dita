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

					var additions = calculateCorrectionAdditions(composite.sid());
					var deletions = calculateCorrectionDeletions(composite.sid());

					if(additions.size()==0
							&& deletions.size()==0)
						return node; // skip

					var coors = CompositeCorr.Coordinates.of(composite);
		            String rename = null;
		            SemanticIdentifier newGroupSid = null;

		            var occurrence = new Occurrence(composite, sid->facetLiteral(sid));

					corrs.add(new CompositeCorr(coors, rename, newGroupSid,
							additions, deletions, Map.of("comments", occurrence.comments())));
				}
				return node;
			}
        });

        var corr24 = new Correction24(null, null, corrs);
		return corr24;
	}

	List<Addition> calculateCorrectionAdditions(final SemanticIdentifier recipeSid) {
		var ingredientDiff = fdmDiff.ingredientDiffByRecipeSid().getOrDefault(recipeSid, Diff.empty());
		return ingredientDiff.leftOuter().stream()
			.map(RecipeIngredientResolved::data)
			.map(it->new Addition(it.foodSid(), it.amountGrams(), it.foodFacetSids()))
			.toList();
	}
    List<Deletion> calculateCorrectionDeletions(final SemanticIdentifier recipeSid) {
    	var ingredientDiff = fdmDiff.ingredientDiffByRecipeSid().getOrDefault(recipeSid, Diff.empty());
		return ingredientDiff.rightOuter().stream()
			.map(RecipeIngredientResolved::data)
			.map(it->new Deletion(it.foodSid()))
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
        Occurrence(
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
        List<String> comments() {
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
        String formatFacets(final SemanticIdentifierSet sids) {
            if(sids.elements().isEmpty())
            	return "";
            return "%s (%s)".formatted(
                    sids.shortFormat(","),
                    sids.elements().map(facetLiteralProvider).join(", "));
        }
        @SuppressWarnings("unchecked")
        static List<String> notes(final Composite composite) {
            return composite.lookupAnnotation(Annotated.NOTES)
                .map(Annotation::value)
                .map(x->(List<String>)x)
                .orElseGet(List::of);
        }
        static Stream<Consumption> streamConsumptions(final Composite composite) {
            return composite.subRecords().stream()
                .filter(Consumption.class::isInstance)
                .map(Consumption.class::cast);
        }
    }

}
