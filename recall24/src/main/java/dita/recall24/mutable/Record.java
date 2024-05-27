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
package dita.recall24.mutable;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import lombok.Data;

@XmlRootElement(name="record")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public final class Record {

    enum Type {
        LEGACY;
        static Type destringify(final String v) {
            return Type.valueOf(v);
        }
        String stringify() {
            return name();
        }
    }

    @XmlElement(name="type")
    @XmlJavaTypeAdapter(value=Record.RecordTypeAdapter.class)
    private Record.Type type;

    @XmlElement(name="name")
    private String name;

    @XmlElement(name="facetSids")
    private String facetSids;

    @XmlElementWrapper(name="ingredients")
    @XmlElement(name="ingredient", type=Record.class)
    private List<Record> subRecords;

    static final class RecordTypeAdapter extends XmlAdapter<String, Record.Type>{
        @Override public Record.Type unmarshal(final String v) throws Exception {
            try {
                return Record.Type.destringify(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
        @Override public String marshal(final Record.Type v) throws Exception {
            return v!=null
                    ? v.stringify()
                    : null;
        }
    }

}
