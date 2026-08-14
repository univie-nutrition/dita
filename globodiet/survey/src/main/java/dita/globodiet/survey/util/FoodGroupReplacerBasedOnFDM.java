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

import dita.foodon.fdm.FoodDescriptionModel;
import dita.foodon.fdm.FoodDescriptionModel.Food;
import dita.recall24.dto.RecallNode24;
import dita.recall24.dto.RecallNode24.Transfomer;
import dita.recall24.dto.Record24;

/**
 * Replaces outdated food group SIDs based on given FDM
 */
public record FoodGroupReplacerBasedOnFDM(
		FoodDescriptionModel fdm) implements Transfomer {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RecallNode24> T transform(final T node) {
		if(node instanceof Record24.Composite composite) {
			var compositeBuilder = (Record24.Composite.Builder)composite.asBuilder();
			processComposite(compositeBuilder);
			return (T) compositeBuilder.build();
		}
		if(node instanceof Record24.Food food)
			return (T) processFood(food);
		return node;
	}

	Record24.Food processFood(final Record24.Food food) {
		// compare actual with expected
		return fdm.lookupFoodBySid(food.sid())
			.filter(refFood->!refFood.groupSid().equals(food.groupSid().orElse(null)))
			.map(Food::groupSid)
			.map(groupSidReplacement->
				((Record24.Food.Builder)food.asBuilder())
				.modifyGroup(_->groupSidReplacement)
				.build())
			.orElse(food);
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
				compositeBuilder.subRecords().add(processFood(food));
				return;
			}
			compositeBuilder.subRecords().add(sub);
		});
	}

}