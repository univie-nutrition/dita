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
package dita.globodiet.survey.util;

import java.util.List;
import java.util.Optional;

import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;

/**
 * Merges ingredients that only differ in amount.
 */
public record DuplicateIngredientMerger() implements Transfomer {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecallNode24> T transform(final T node) {
		if(node instanceof Record24.Composite composite) {
			var compositeBuilder = (Record24.Composite.Builder)composite.asBuilder();
			processComposite(compositeBuilder);
			return (T) compositeBuilder.build();
		}
		// don't need to process top level food records
		return node;
	}

	void processComposite(final Record24.Composite.Builder compositeBuilder) {
		var origSubs = List.copyOf(compositeBuilder.subRecords());
		compositeBuilder.subRecords().clear();
		origSubs.forEach(sub->{
			if(sub instanceof Record24.Composite subComposite) {
				var subCompositeBuilder = (Record24.Composite.Builder)subComposite.asBuilder();
				processComposite(subCompositeBuilder);
				compositeBuilder.subRecords().add(subCompositeBuilder.build());
				return;
			}
			if(sub instanceof Record24.Food food) {
				// for the current composite, search for ingredients that are exact copies and merge them
				final var seenBefore = seenBefore(compositeBuilder.subRecords(), food).orElse(null);
				if(seenBefore!=null) {
					compositeBuilder.subRecords().replaceAll(it->it==seenBefore ? seenBefore.merge(food) : it);
				} else {
					compositeBuilder.subRecords().add(food);
				}
				return;
			}
			compositeBuilder.subRecords().add(sub);
		});
	}

	Optional<Record24.Food> seenBefore(final List<Record24> list, final Record24.Food candidate) {
		return list.stream()
			.filter(Record24.Food.class::isInstance)
			.map(Record24.Food.class::cast)
			.filter(food->food.equalsIgnoreAmount(candidate))
			.findFirst();
	}

}