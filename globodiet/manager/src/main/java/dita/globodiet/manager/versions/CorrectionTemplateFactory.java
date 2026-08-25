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
import java.util.Collection;
import java.util.List;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.collections._Multimaps;
import org.apache.causeway.commons.internal.collections._Multimaps.ListMultimap;
import org.jspecify.annotations.Nullable;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.ObjectId.Context;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.TabularData.Row;
import dita.commons.types.TabularDiff;
import dita.commons.types.TabularDiff.RowDiff;
import dita.recall24.dto.Correction24;
import dita.recall24.dto.Correction24.CompositeCorr;
import dita.recall24.dto.Correction24.CompositeCorr.Addition;
import dita.recall24.dto.Correction24.CompositeCorr.Deletion;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.Record24;

public record CorrectionTemplateFactory(TabularDiff tabularDiff) {

	public Correction24 create(final InterviewSet24 interviewSet) {

        var ingredientChanges = ingredientChanges();
        var ingredientAdditions = ingredientChanges.groupByRecipeId(ingredientChanges.additions());
        var ingredientDeletions = ingredientChanges.groupByRecipeId(ingredientChanges.deletions());

        var corrs = new ArrayList<CompositeCorr>();
        interviewSet.transform(new RecallNode24.Transfomer() {
			@Override
			public <T extends RecallNode24> T transform(final T node) {
				if(node instanceof Record24.Composite composite) {

					var additions = calculateCorrectionAdditions(ingredientAdditions.get(composite.sid()));
					var deletions = calculateCorrectionDeletions(ingredientDeletions.get(composite.sid()));

					if(additions.size()==0
							&& deletions.size()==0)
						return node; // skip

					var coors = CompositeCorr.Coordinates.of(composite);
		            String rename = null;
		            SemanticIdentifier newGroupSid = null;
					corrs.add(new CompositeCorr(coors, rename, newGroupSid,
							additions, deletions));
				}
				return node;
			}
        });

        var corr24 = new Correction24(null, null, corrs);
		return corr24;
	}

	List<Addition> calculateCorrectionAdditions(final @Nullable Collection<IngredientAdapter> list) {
		return _NullSafe.stream(list).map(IngredientAdapter::asCorrectionAddition).toList();
	}
    List<Deletion> calculateCorrectionDeletions(final @Nullable Collection<IngredientAdapter> list) {
    	return _NullSafe.stream(list).map(IngredientAdapter::asCorrectionDeletion).toList();
    }

	record IngredientChanges(RowDiff rowDiff){
		List<IngredientAdapter> additions() {
			return adapt(rowDiff.rowDiff().leftOuter());
		}
		List<IngredientAdapter> deletions() {
			return adapt(rowDiff.rowDiff().rightOuter());
		}
		List<IngredientAdapter> adapt(final Collection<Row> rows) {
			return rows.stream().map(IngredientAdapter::new).toList();
		}
		ListMultimap<SemanticIdentifier, IngredientAdapter> groupByRecipeId(final Collection<IngredientAdapter> adapters) {
			var multimap = _Multimaps.<SemanticIdentifier, IngredientAdapter>newListMultimap();
			adapters.forEach(adapter->multimap.putElement(adapter.recipeSid(), adapter));
			return multimap;
		}
	}

	//TODO it would perhaps be simpler to diff with the FDM directly instead
	record IngredientAdapter(Row row){
		SemanticIdentifier recipeSid() {
			return new SemanticIdentifier(SystemId.parse("at.gd/2.0"), Context.RECIPE.objectId(row.cellLiterals().get(0)));
		}
		SemanticIdentifier sid() {
			return new SemanticIdentifier(SystemId.parse("at.gd/2.0"), Context.FOOD.objectId(row.cellLiterals().get(5)));
		}
        BigDecimal amountGrams() {
        	return new BigDecimal(row.cellLiterals().get(12));
        }
        SemanticIdentifierSet facets() {
        	return new SemanticIdentifierSet(_Strings.splitThenStream(row.cellLiterals().get(4), ",")
            		.map(fourdigits->new SemanticIdentifier(SystemId.parse("at.gd/2.0"), Context.FOOD_DESCRIPTOR.objectId(fourdigits)))
            		.collect(Can.toCan()));
        }
		Addition asCorrectionAddition() {
			return new Addition(sid(), amountGrams(), facets());
		}
		Deletion asCorrectionDeletion() {
			return new Deletion(sid());
		}
	}

	IngredientChanges ingredientChanges() {
		return new IngredientChanges(tabularDiff.tableDiff().rowDiffByTableKey().get("dita.globodiet.params.recipe_list.RecipeIngredient"));
	}

}
