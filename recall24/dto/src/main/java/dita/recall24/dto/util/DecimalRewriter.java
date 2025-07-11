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
package dita.recall24.dto.util;

import java.util.List;

import org.apache.causeway.commons.internal.base._Strings;

/**
 * put decimal values into quotes, so those are converted to type String and can be used with BigDecimal to full precision
 */
record ToQuotedDecimalRewriter(
        List<String> keys) {

    String rewriteJson(final String line) {
        for(var key : keys) {

            int p = line.indexOf(key);
            if(p<0) return line;
            if(line.indexOf("\""+key+"\"", p-1)<0) return line;
            p += key.length() + 1;
            if(line.indexOf("null", p)>-1) return line; // don't quote <null>

            return _Strings.splitThenApplyRequireNonEmpty(line, ":", (lhs, rhs)->{
                var value = rhs.trim();
                var hasColon = value.endsWith(",");
                if(hasColon) {
                    value = _Strings.substring(value, 0, -1).trim();
                }
                var rewritten = lhs + ": \"" + value + "\"";
                return hasColon
                        ? rewritten+","
                        : rewritten;
            }).orElseThrow();

        }
        return line;
    }

    String rewriteYaml(final String line) {
        for(var key : keys) {
            if(line.trim().startsWith(key)) {
                return _Strings.splitThenApplyRequireNonEmpty(line, ":", (lhs, rhs)->{
                    var value = rhs.trim();
                    return "null".equals(value)
                        ? lhs + ": " + value // don't quote <null>
                        : lhs + ": \"" + value + "\"";
                }).orElseThrow();
            }
        }
        return line;
    }

}
