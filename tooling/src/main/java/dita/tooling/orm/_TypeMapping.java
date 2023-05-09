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
package dita.tooling.orm;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
class _TypeMapping {

    TypeName dbToJava(final String typeName) {
        if(typeName.startsWith("nvarchar")) {
            return ClassName.get("java.lang", "String");
        }
        if(typeName.equals("bit(1)")) {
            return TypeName.BOOLEAN;
        }
        if(typeName.startsWith("int")
                || typeName.startsWith("smallint")
                || typeName.startsWith("tinyint")) {
            return TypeName.INT;
        }
        if(typeName.startsWith("float")) {
            return TypeName.DOUBLE;
        }
        if(typeName.equals("datetime")
                || typeName.equals("datetime(23)")) {
            return ClassName.get("java.sql", "Timestamp");
        }
        throw _Exceptions.unmatchedCase(typeName);
    }

}
