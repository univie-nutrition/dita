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

import org.apache.causeway.commons.io.JsonUtils.JacksonCustomizer;
import org.apache.causeway.commons.io.YamlUtils;

import dita.commons.types.Pair;
import dita.commons.util.FormatUtils;
import dita.globodiet.manager.versions.FdmDiffFactory.FdmDiff;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

record FdmDiffYamlWriter(FdmDiff fdmDiff) {
	String toYaml() {
		return YamlUtils.toStringUtf8(fdmDiff, FormatUtils.yamlOptions(), pairAdapter());
	}

	static class PairSerializer extends StdSerializer<Pair<?, ?>> {
		protected PairSerializer() {
			super(Pair.class);
		}
		@Override
		public void serialize(final Pair<?, ?> value, final JsonGenerator gen, final SerializationContext ctxt) throws JacksonException {
			gen.writeStartObject();
			gen.writePOJOProperty("old", value.right());
			gen.writePOJOProperty("new", value.left());
			gen.writeEndObject();
		}
    }

    static JacksonCustomizer pairAdapter() {
        return builder->
            builder.addModule(new SimpleModule()
                    .addSerializer(new PairSerializer()));
    }
}