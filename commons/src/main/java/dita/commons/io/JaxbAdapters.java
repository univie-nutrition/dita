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
package dita.commons.io;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.UnitFormat;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifier.SystemId;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import io.github.causewaystuff.commons.base.types.NamedPath;
import tech.units.indriya.format.SimpleUnitFormat;
import tech.units.indriya.quantity.Quantities;

@UtilityClass
public class JaxbAdapters {

    public static final class SystemIdAdapter extends XmlAdapter<String, SystemId>{
        @Override public SystemId unmarshal(final String v) throws Exception {
            try {
                return SystemId.parse(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
        @Override public String marshal(final SystemId v) throws Exception {
            return v!=null
                    ? v.toString()
                    : null;
        }
    }

    public static final class SemanticIdentifierAdapter extends XmlAdapter<String, SemanticIdentifier>{
        @Override public SemanticIdentifier unmarshal(final String v) throws Exception {
            try {
                return SemanticIdentifier.parse(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
        @Override public String marshal(final SemanticIdentifier v) throws Exception {
            return v!=null
                    ? v.toStringNoBox()
                    : null;
        }
    }

    public static final class SemanticIdentifierSetAdapter extends XmlAdapter<String, SemanticIdentifierSet>{
        @Override public SemanticIdentifierSet unmarshal(final String v) throws Exception {
            try {
                return SemanticIdentifierSet.parse(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
        @Override public String marshal(final SemanticIdentifierSet v) throws Exception {
            return v!=null
                    ? v.toStringNoBox()
                    : null;
        }
    }

    public static final class SexAdapter extends XmlAdapter<String, Sex>{
        @Override public Sex unmarshal(final String v) throws Exception {
            try {
                return Sex.destringify(v);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
        @Override public String marshal(final Sex v) throws Exception {
            return v!=null
                    ? v.stringify()
                    : null;
        }
    }

    public static final class QuantityAdapter extends XmlAdapter<String, Quantity<?>>{

        private final UnitFormat format = SimpleUnitFormat.getInstance();

        @Override public Quantity<?> unmarshal(final String v) throws Exception {
            try {
                final String unitLiteral = substringEmbeddedBy(v, "[", "]");
                final String valueLiteral = substringEmbeddedBy(v, null, "[");
                final double value = Double.parseDouble(valueLiteral);
                final Unit<?> unit = format.parse(unitLiteral);
                return Quantities.getQuantity(value, unit);
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }

        @Override public String marshal(final Quantity<?> v) throws Exception {

            String formattedUnit = format
                    .format(v.getUnit(), new StringBuilder()).toString();

            return v.getValue().doubleValue() + "[" + formattedUnit + "]";
        }

        /**
         * TODO this should be handled by some upstream library<p>
         * Extension to String's default substring method, where start and end indexes are
         * determined be searching for the first occurrence of <i>prefix</i> followed by <i>suffix</i>.
         * The string that's embedded between these two is returned.
         * @param x input
         * @param prefix if not found in string startIndex is 0 (null or empty is allowed)
         * @param suffix if not found in string endIndex is string's length (null or empty is allowed)
         * @return the string that's embedded between first <i>prefix</i> followed by <i>suffix</i>
         */
        private static String substringEmbeddedBy(final String x, final String prefix, final String suffix) {
            int p=0;
            if(StringUtils.hasLength(prefix)){
                p = x.indexOf(prefix);
                if(p==-1)
                    p=0; // prefix not found
                else
                    p=p+prefix.length();
            }
            int q = -1;
            if(StringUtils.hasLength(suffix))
                q = x.indexOf(suffix,p);
            if(q==-1)
                return x.substring(p); // suffix not found
            return x.substring(p, q);
        }
    }

    public static final class NamedPathAdapter extends XmlAdapter<String, NamedPath>{
        @Override public NamedPath unmarshal(final String v) throws Exception {
            try {
                return NamedPath.parse(v, "/");
            } catch (Exception e) {
                e.printStackTrace(); // might be swallowed otherwise
                throw e;
            }
        }
        @Override public String marshal(final NamedPath v) throws Exception {
            return v!=null
                    ? v.toString("/")
                    : null;
        }
    }

}
