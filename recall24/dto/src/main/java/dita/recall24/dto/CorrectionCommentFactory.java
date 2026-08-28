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
package dita.recall24.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.causeway.commons.io.YamlUtils.YamlWriter;

import dita.commons.io.JaxbAdapters;
import dita.recall24.dto.Correction24.CompositeCorr;
import dita.recall24.dto.Correction24.CompositeCorr.Addition;
import dita.recall24.dto.Correction24.CompositeCorr.Coordinates;
import dita.recall24.dto.Correction24.CompositeCorr.Deletion;
import dita.recall24.dto.Correction24.FoodByNameCorr;
import dita.recall24.dto.Correction24.RespondentCorr;
import lombok.SneakyThrows;

public record CorrectionCommentFactory() {

	public static String toYaml(final Correction24 correction24) {
		return new Correction24YamlEmitter().toYaml(correction24);
	}

	/**
	 * Convert Correction24 DTO to YAML.
	 */
	record Correction24YamlEmitter(
			YamlWriter writer,
	    	JaxbAdapters.NamedPathAdapter namedPathAdapter,
	    	DateTimeFormatter localDateTimeFormat) {

		Correction24YamlEmitter() {
			this(new YamlWriter(),
	    		new JaxbAdapters.NamedPathAdapter(),
	    		DateTimeFormatter.ofPattern("HH:mm:ss"));
		}

	    public String toYaml(final Correction24 correction) {
	        if (correction == null)
				return "null";

	        // 1. Respondents
	        List<RespondentCorr> respondents = correction.respondents();
	        if (respondents != null && !respondents.isEmpty()) {
	            writer.write("respondents:").nl();
	            for (RespondentCorr resp : respondents) {
	                writeRespondentCorr(resp);
	            }
	        } else {
	            writer.write("respondents: []").nl();
	        }

	        // 2. FoodByName
	        List<FoodByNameCorr> foodByName = correction.foodByName();
	        if (foodByName != null && !foodByName.isEmpty()) {
	            writer.write("foodByName:").nl();
	            for (FoodByNameCorr food : foodByName) {
	                writeFoodByNameCorr(food);
	            }
	        } else {
	            writer.write("foodByName: []").nl();
	        }

	        // 3. Composites
	        List<CompositeCorr> composites = correction.composites();
	        if (composites != null && !composites.isEmpty()) {
	            writer.write("composites:").nl();
	            for (CompositeCorr comp : composites) {
	                writeCompositeCorr(comp);
	            }
	        } else {
	            writer.write("composites: []").nl();
	        }

	        String result = writer.toString();
	        return result;
	    }

	    private void writeRespondentCorr(final RespondentCorr resp) {
	        writer.sq().write("alias: ").dq(resp.alias()).nl();
	        if (resp.withdraw() != null) {
	            writer.ind().write("withdraw: ").write(resp.withdraw().toString()).nl();
	        }
	        if (resp.newAlias() != null) {
	            writer.ind().write("newAlias: ").dq(resp.newAlias()).nl();
	        }
	        if (resp.dateOfBirth() != null) {
	            writer.ind().write("dateOfBirth: ").dq(resp.dateOfBirth().toString()).nl();
	        }
	        if (resp.sex() != null) {
	            writer.ind().write("sex: ").dq(resp.sex().name()).nl();
	        }
	    }

	    private void writeFoodByNameCorr(final FoodByNameCorr food) {
	        writer.sq().write("name: ").dq(food.name()).nl();
	        if (food.sid() != null) {
	            writer.ind().write("sid: ").dq(food.sid().toStringNoBox()).nl();
	        }
	    }

	    private void writeCompositeCorr(final CompositeCorr comp) {
	        writer.sq().write("coordinates:").nl();
	        writeCoordinates(comp.coordinates()); // Indent 3 for properties under coordinates
	        // rename
	        if (comp.rename() != null) {
	            writer.ind().write("rename: ").dq(comp.rename()).nl();
	        }
	        // groupSid
	        if (comp.groupSid() != null) {
	            writer.ind().write("groupSid: ").dq(comp.groupSid().toStringNoBox()).nl();
	        }
	        // additions
	        List<Addition> additions = comp.additions();
	        if (additions != null && !additions.isEmpty()) {
	            writer.ind().write("additions:").nl();
	            for (Addition add : additions) {
	                writeAddition(add);
	            }
	        } else {
	            writer.ind().write("additions: []").nl();
	        }
	        // deletions
	        List<Deletion> deletions = comp.deletions();
	        if (deletions != null && !deletions.isEmpty()) {
	            writer.ind().write("deletions:").nl();
	            for (Deletion del : deletions) {
	                writeDeletion(del);
	            }
	        } else {
	            writer.ind().write("deletions: []").nl();
	        }
	    }

	    @SneakyThrows
	    private void writeCoordinates(final Coordinates coords) {
	        if (coords == null)
	        	return;
	        if (coords.sid() != null) {
	            writer.ind(2).write("sid: ").dq(coords.sid().toStringNoBox()).nl();
	        }
	        if (coords.respondentId() != null) {
	            writer.ind(2).write("respondentId: ").dq(coords.respondentId()).nl();
	        }
	        writer.ind(2).write("interviewOrdinal: ").write(String.valueOf(coords.interviewOrdinal())).nl();
	        if (coords.mealHourOfDay() != null) {
	            writer.ind(2).write("mealHourOfDay: ").dq(coords.mealHourOfDay().format(localDateTimeFormat)).nl();
	        }
	        if (coords.name() != null) {
	            writer.ind(2).write("name: ").dq(coords.name()).nl();
	        }
	        if (coords.source() != null) {
	            writer.ind(2).write("source: ").dq(namedPathAdapter.marshal(coords.source())).nl();
	        }
	    }

	    private void writeAddition(final Addition add) {
	        writer.ind().sq();
	        writer.write("sid: ").dq(add.sid().toStringNoBox()).nl();
	        writer.ind(2).write("amountGrams: ").write(add.amountGrams().toString()).nl();
	        if (add.facets() != null) {
	            writer.ind(2).write("facets: ").dq(add.facets().toStringNoBox()).nl();
	        }
	    }

	    private void writeDeletion(final Deletion del) {
	        writer.ind().sq();
	        writer.write("sid: ").dq(del.sid().toStringNoBox()).nl();
	    }
	}

}
