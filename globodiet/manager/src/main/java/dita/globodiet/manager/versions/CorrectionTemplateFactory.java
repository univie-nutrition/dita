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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Pair;
import dita.commons.util.NumberUtils;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved;
import dita.foodon.fdm.FoodDescriptionModel.RecipeIngredientResolved.Key;
import dita.globodiet.manager.versions.FdmDiffFactory.FdmDiff;
import dita.globodiet.manager.versions.FdmDiffFactory.IngredientAdded;
import dita.globodiet.manager.versions.FdmDiffFactory.IngredientChanged;
import dita.globodiet.manager.versions.FdmDiffFactory.IngredientRemoved;
import dita.globodiet.manager.versions.FdmDiffFactory.RecipeChange;
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

public record CorrectionTemplateFactory(
		Function<SemanticIdentifier, String> facetLiteralProvider,
		FdmDiff fdmDiff,
		/// Amount of mass change of an ingredient relative to the recipe's total amount in units of parts per million (ppm),
		/// that must be exceeded in order for a change to be emitted.
		/// Changes that fall below given threshold are simply ignored with the output. (10000ppm = 1%)
		int ppmThreshold,
		/// Whether to include group changes,
		/// however this is not needed because the Report Generator does correct groups automatically from its selected FDM.
		boolean includeGroupCorrections) {

	public Correction24 create(final InterviewSet24 interviewSet) {

		var map = fdmDiff.recipesChanged().stream()
			.collect(Collectors.toMap(RecipeChange::recipeSid, UnaryOperator.identity()));

        var corrs = new ArrayList<CompositeCorr>();
        interviewSet.transform(new RecallNode24.Transfomer() {
			@Override
			public <T extends RecallNode24> T transform(final T node) {
				if(node instanceof Record24.Composite composite) {
	                correctionFor(composite, map.get(composite.sid()))
	                	.ifPresent(corrs::add);
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
	/// @param list
	private Optional<CompositeCorr> correctionFor(
			final Composite composite,
			final RecipeChange recipesChange) {
        if(recipesChange==null)
        	return Optional.empty();

        // recipe that had changed between FDM versions
        final var compWrapper = new CompositeWrapper(composite);
        final var builder = new CompositeCorrBuilder(composite, facetLiteralProvider);

        Optional.ofNullable(recipesChange.nameChange())
        	.map(Pair::left)
        	.ifPresent(builder.rename()::set);

        if(includeGroupCorrections) {
            Optional.ofNullable(recipesChange.groupChange())
        		.map(Pair::left)
	        	.ifPresent(builder.newGroupSid()::set);
        }

        // handle addition
        // if consumed no-op, otherwise add
        recipesChange.ingredientsAdded().forEach(ingrAdded->{
        	var ingrConsumed = compWrapper.lookupIngredient(ingrAdded.key())
        			.orElse(null);
        	if(ingrConsumed!=null)
				return;
    		var newAmount = NumberUtils.totalTimesPermillion(
    				compWrapper.amountConsumedTotal(),
    				ingrAdded.relativeMassPermille());
    		newAmount = NumberUtils.reducedPrecision(newAmount, 2);
			builder.add(ingrAdded, newAmount , "ingredient was added to the recipe of FDM");
        });
        // handle removal
        // if consumed delete, otherwise no-op
        recipesChange.ingredientsRemoved().forEach(ingrRemoved->{
        	var ingrConsumed = compWrapper.lookupIngredient(ingrRemoved.key())
        			.orElse(null);
        	if(ingrConsumed==null)
				return;
        	builder.del(ingrRemoved, "ingredient was removed from the recipe of FDM");
        });
        // handle change
        // if consumed change amount, otherwise no-op
        recipesChange.ingredientsChanged().forEach(ingrChanged->{
        	var ingrConsumed = compWrapper.lookupIngredient(ingrChanged.key())
        			.orElse(null);
        	if(ingrConsumed==null)
				return;
        	if(ingrChanged.amountChangePpm()==null)
        		return;
        	var delta = ingrChanged.amountChangePpm().left()
        			- ingrChanged.amountChangePpm().right();
        	if(Math.abs(delta)>=ppmThreshold) { // ignore if below threshold
        		var newAmount = NumberUtils.totalTimesPermillion(
        				compWrapper.amountConsumedTotal(),
        				ingrChanged.amountChangePpm().left());
        		newAmount = NumberUtils.reducedPrecision(newAmount, 2);
				builder.change(ingrChanged, newAmount,
						"ingredient changed amount (%.2f%%->%.2f%%) in recipe of FDM"
							.formatted(
									0.0001 * ingrChanged.amountChangePpm().right(),
									0.0001 * ingrChanged.amountChangePpm().left()));
        	}
        });

        return !builder.isEmpty()
    		? Optional.of(builder
    				.comments(compWrapper.comments(facetLiteralProvider))
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
            List<String> notes,
            BigDecimal amountConsumedTotal,
            Map<RecipeIngredientResolved.Key, Consumption> ingredients) {
        CompositeWrapper(final Composite composite) {
            this(composite,
                notes(composite),
                streamConsumptions(composite)
                    .map(Consumption::amountConsumed)
                    .reduce(BigDecimal.ZERO, BigDecimal::add),
                streamConsumptions(composite)
	                .collect(Collectors.toMap(
	                		cons->new RecipeIngredientResolved.Key(composite.sid(), cons.sid(), cons.facetSids().hashCode()),
	                		UnaryOperator.identity())));
        }
        public Optional<Consumption> lookupIngredient(final Key key) {
        	return Optional.ofNullable(ingredients.get(key));
		}
        private List<String> comments(final Function<SemanticIdentifier, String> facetLiteralProvider) {
        	var totalGrams = amountConsumedTotal().doubleValue();
        	var comments = new ArrayList<String>();
            comments.add("ingredients consumed:");
            ingredients().values()
            	.forEach(ingrCons->{
            		comments.add("- %s %s (%.1f%%) %s (%s) {%s}"
            			.formatted(
        					formatDecimal(ingrCons.amountConsumed()),
        					ingrCons.consumptionUnit(),
        					100. * ingrCons.amountConsumed().doubleValue()
								/ totalGrams,
	                        ingrCons.name(), ingrCons.sid().objectId().toString(),
	                        formatFacets(ingrCons.facetSids(), facetLiteralProvider)));
            	});
            comments.add("amount-consumed-total: %.2fg"
            		.formatted(totalGrams));
			return comments;
		}
        private String formatFacets(
        		final SemanticIdentifierSet sids,
        		final Function<SemanticIdentifier, String> facetLiteralProvider) {
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
    	public void add(final IngredientAdded ingrAdded, final BigDecimal newAmount, final String secondaryComment) {
    		additions().add(new Addition(
    				ingrAdded.foodSid(),
    				ingrAdded.foodFacetSids(),
    				newAmount,
    				List.of(
						formatNameAndFacets(ingrAdded),
						secondaryComment)));
    	}
		public void del(final IngredientRemoved ingrRemoved, final String secondaryComment) {
    		deletions().add(new Deletion(
    				ingrRemoved.foodSid(),
    				ingrRemoved.foodFacetSids(),
    				List.of(
						formatNameAndFacets(ingrRemoved),
						secondaryComment)));
    	}
    	public void change(final IngredientChanged ingrChanged, final BigDecimal newAmount, final String secondaryComment) {
    		additions().add(new Addition(
    				ingrChanged.foodSid(),
    				ingrChanged.foodFacetSids(),
    				newAmount,
    				List.of(
						formatNameAndFacets(ingrChanged),
						secondaryComment)));
    		deletions().add(new Deletion(
    				ingrChanged.foodSid(),
    				ingrChanged.foodFacetSids(),
    				List.of(
						formatNameAndFacets(ingrChanged),
						"replaced")));
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
		CompositeCorr build() {
    		var coors = CompositeCorr.Coordinates.of(composite);
    		return new CompositeCorr(coors, rename.get(), newGroupSid.get(),
    				additions, deletions, comments);
    	}
		// -- HELPER
    	private String formatNameAndFacets(final IngredientAdded value) {
            return "name: %s, facets: %s"
					.formatted(
							value.name(),
							value.foodFacets());
        }
    	private String formatNameAndFacets(final IngredientRemoved value) {
            return "name: %s, facets: %s"
					.formatted(
							value.name(),
							value.foodFacets());
        }
		private String formatNameAndFacets(final IngredientChanged value) {
			return "name: %s, facets: %s"
					.formatted(
							value.name(),
							value.foodFacets());
		}
    }

    private static String formatDecimal(final BigDecimal bd) {
    	return NumberUtils.reducedPrecision(bd, 2).toPlainString();
    }

}
