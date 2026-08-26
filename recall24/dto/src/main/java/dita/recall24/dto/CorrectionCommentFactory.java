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

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.causeway.commons.io.JsonUtils.JacksonCustomizer;
import org.apache.causeway.commons.io.TextUtils;

import dita.commons.util.FormatUtils;
import dita.recall24.dto.Correction24.CompositeCorr;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

public record CorrectionCommentFactory() {

	public JacksonCustomizer[] yamlOptions() {
		return new JacksonCustomizer[] {
				FormatUtils.yamlOptions(),
				builder->builder.addModule(new SimpleModule()
		                .addSerializer(new CompositeCorrSerializer())),
			};
	}

	public static String postprocess(final String yaml) {
		boolean inComments = false;
		String indent = "";
		var lines = new ArrayList<String>();
		for(String line : TextUtils.readLines(yaml)) {
			var trimmed = line.stripLeading();
			if(line.contains("comment: \"START\"")) {
				inComments = true;
				indent = line.substring(0, line.length() - trimmed.length());
				continue;
			}
			if(line.contains("comment: \"END\"")) {
				inComments = false;
				continue;
			}
			if(!inComments) {
				lines.add(line);
				continue;
			}
			var converted = trimmed;
			if(trimmed.startsWith("comment:")) {
				converted = "# " + trimmed.substring(10);
			} else if(trimmed.startsWith("\\")) {
				converted = "#  " + trimmed.substring(1);
			}
			if(converted.endsWith("\"")) {
				converted = converted.substring(0, converted.length()-1);
			}
			lines.add(indent + converted);
		}
		return lines.stream().collect(Collectors.joining("\n"));
	}

	private static class CompositeCorrSerializer
    extends StdSerializer<Correction24.CompositeCorr> {
        CompositeCorrSerializer() {
            super(Correction24.CompositeCorr.class);
        }
		@SuppressWarnings("unchecked")
		@Override
		public void serialize(final CompositeCorr value, final JsonGenerator gen,
				final SerializationContext ctxt) throws JacksonException {
			gen.writeStartObject();
			ctxt.defaultSerializeProperty("coordinates", value.coordinates(), gen);
			Optional.ofNullable(value.properties())
				.map(map->map.get("comments"))
				.map(java.util.List.class::cast)
				.filter(list->!list.isEmpty())
				.ifPresent(list->{
					ctxt.defaultSerializeProperty("comment", "START", gen);
					list.forEach(
						comment->ctxt.defaultSerializeProperty("comment", comment, gen));
					ctxt.defaultSerializeProperty("comment", "END", gen);
				});
			ctxt.defaultSerializeProperty("rename", value.rename(), gen);
			ctxt.defaultSerializeProperty("groupSid", value.groupSid(), gen);
			ctxt.defaultSerializeProperty("additions", value.additions(), gen);
			ctxt.defaultSerializeProperty("deletions", value.deletions(), gen);
			gen.writeEndObject();
		}
    }

}
