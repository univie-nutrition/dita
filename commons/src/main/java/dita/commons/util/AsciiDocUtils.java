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
package dita.commons.util;

import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import org.jspecify.annotations.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AsciiDocUtils {

    public AsciiDoc yamlBlock(final String docTitle, final String blockTitle, @NonNull final String yamlSource) {
        var adoc = new AsciiDocBuilder();
        adoc.append(doc->doc.setTitle(docTitle));
        adoc.append(doc->{
            var sourceBlock = AsciiDocFactory.sourceBlock(doc, "yml", yamlSource);
            sourceBlock.setTitle(blockTitle);
        });

        return adoc.buildAsValue();
    }

}
