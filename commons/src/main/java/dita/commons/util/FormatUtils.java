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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;

import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.io.JsonUtils;
import org.apache.causeway.commons.io.JsonUtils.JacksonCustomizer;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocBuilder;
import org.apache.causeway.valuetypes.asciidoc.builder.AsciiDocFactory;

import lombok.experimental.UtilityClass;

import dita.commons.food.composition.FoodComponent;
import dita.commons.io.JaxbAdapters;

@UtilityClass
public class FormatUtils {

    public String emptyToDash(final @Nullable String input) {
        return StringUtils.hasLength(input)
                ? input
                : "-";
    }

    public String noLeadingZeros(final @Nullable String input) {
        if(!StringUtils.hasLength(input)) return input;

        var result = input;
        while(result.length()>1
                && result.startsWith("0")) {
            result = result.substring(1);
        }
        return result;
    }

    public String fillString(final int length, final char character) {
        var array = new char[length];
        Arrays.fill(array, character);
        return new String(array);
    }

    public String fillWithLeadingZeros(final int stringLengthYield, final @Nullable String input) {
        if(!StringUtils.hasLength(input)) {
            return fillString(stringLengthYield, '0');
        }
        final int gap = stringLengthYield - input.length();
        if(gap<1) return input;

        return fillString(gap, '0') + input;
    }

    // -- TEMPORAL

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);

    @Nullable
    public String isoDate(final @Nullable LocalDate localDate) {
        return localDate!=null
            ? ISO_DATE.format(localDate)
            : null;
    }

    private static final DateTimeFormatter HOUR_OF_DAY = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT);

    @Nullable
    public String hourOfDay(final @Nullable LocalTime time) {
        return time!=null
            ? HOUR_OF_DAY.format(time)
            : null;
    }

    // -- JAVA UTIL FORMAT

    public String safelyFormat(final @Nullable String format) {
        return _Strings.nullToEmpty(format);
    }

    public String safelyFormat(final @Nullable String format, final Object ...args) {
        return StringUtils.hasLength(format)
                ? Try.call(()->String.format(format, args))
                        .mapFailureToSuccess(ex->"message formatting error: " + ex.getMessage())
                    .getValue()
                    .orElse("")
                : "";
    }

    // -- JACKSON

    public JacksonCustomizer yamlOptions() {
    	JacksonCustomizer c1 = JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.QuantityAdapter());
    	JacksonCustomizer c2 = JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SystemIdAdapter());
    	JacksonCustomizer c3 = JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierAdapter());
    	JacksonCustomizer c4 = JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.SemanticIdentifierSetAdapter());
    	JacksonCustomizer c5 = JsonUtils.JacksonCustomizer.wrapXmlAdapter(new JaxbAdapters.NamedPathAdapter());
    	JacksonCustomizer c6 = JsonUtils::onlyIncludeNonNull;
    	
        return c1.andThen(c2).andThen(c3).andThen(c4).andThen(c5).andThen(c6)::accept;
    }

    // -- ADOC

    public AsciiDoc adocSourceBlock(@Nullable final String sourceType, @Nullable final String source) {
        var adoc = new AsciiDocBuilder()
            .append(doc->AsciiDocFactory.sourceBlock(doc, sourceType, _Strings.nullToEmpty(source)))
            .buildAsValue();
        return adoc;
    }

    public AsciiDoc adocSourceBlockWithTile(@Nullable final String blockTitle, @Nullable final String sourceType, @Nullable final String source) {
        var adoc = new AsciiDocBuilder()
            .append(doc->{
                var sourceBlock = AsciiDocFactory.sourceBlock(doc, sourceType, _Strings.nullToEmpty(source));
                _Strings.nonEmpty(blockTitle).ifPresent(sourceBlock::setTitle);
            })
            .buildAsValue();
        return adoc;
    }

    // -- NULLABLE CONCAT

    /**
     * Returns String concatenation of given parts, ignoring null parts.
     */
    public String concat(final @Nullable String ... parts) {
        if(_NullSafe.isEmpty(parts)) return "";
        var sum = "";
        for(String part : parts) {
            sum += _Strings.nullToEmpty(part);
        }
        return sum;
    }

    public Can<String> cut(final @Nullable IntStream indexes, final @Nullable String input){
        if(indexes==null
                || _Strings.isEmpty(input)) {
            return Can.empty();
        }
        class Helper {
            int count;
            int startIncl;
            int endExcl;
            Helper next(final int index) {
                if(1==count%2) {
                    endExcl = index;
                } else {
                    startIncl = index;
                }
                count++;
                return this;
            }
            String substring() {
                if(count==0
                        || 1==count%2
                        || startIncl<0
                        || endExcl<=startIncl
                        || startIncl>=input.length()
                        || endExcl>input.length()) {
                    return null;
                }
                return input.substring(startIncl, endExcl);
            }
        }
        var helper = new Helper();
        return indexes
            .mapToObj(helper::next)
            .filter(Objects::nonNull)
            .map(Helper::substring)
            .collect(Can.toCan());
    }

    // -- PREFIXED UNIT

    public String prefixedUnit(final FoodComponent component) {
        return "[%s%s]".formatted(
                component.metricPrefix()!=null
                    ? component.metricPrefix().getSymbol()
                    : "",
                component.componentUnit().symbol());
    }

}
