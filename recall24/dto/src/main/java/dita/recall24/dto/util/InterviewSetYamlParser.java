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

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jspecify.annotations.Nullable;

import org.springframework.util.StringUtils;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.io.TextUtils;
import org.apache.causeway.commons.io.YamlUtils;

import lombok.experimental.UtilityClass;

import dita.commons.food.consumption.FoodConsumption.ConsumptionUnit;
import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.commons.types.Sex;
import dita.recall24.dto.Interview24;
import dita.recall24.dto.InterviewSet24;
import dita.recall24.dto.Meal24;
import dita.recall24.dto.MemorizedFood24;
import dita.recall24.dto.Record24;
import dita.recall24.dto.Record24.TypeOfFatUsed;
import dita.recall24.dto.Record24.TypeOfMilkOrLiquidUsed;
import dita.recall24.dto.Respondent24;
import dita.recall24.dto.RespondentSupplementaryData24;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
public class InterviewSetYamlParser {

    public InterviewSet24 parseYaml(@Nullable String yaml) {
        if(!StringUtils.hasLength(yaml)) return InterviewSet24.empty();
        // put decimal values into quotes, so those are converted to type String and can be used with BigDecimal to full precision
        yaml = TextUtils.readLines(yaml).map(InterviewSetYamlParser::toQuotedDecimal).join("\n");
        
        var characterCount = yaml.length();
        
        var asMap = YamlUtils
            .tryReadCustomized(LinkedHashMap.class, yaml, loader->{
                loader.setCodePointLimit(characterCount);
                return loader;
            })
            .valueAsNonNullElseFail();
        
        var parser = new YamlParser(_Casts.uncheckedCast(asMap));
        var interviewSet = new InterviewSet24(parser.collection("respondents").stream()
                .map(InterviewSetYamlParser::respondent)
                .collect(Can.toCan()),
                parser.annotations());
        return interviewSet;
    }
    
    // -- HELPER
    
    private final List<String> PRECISE_DECIMALS = List.of("amountConsumed", "heightCM", "weightKG", "rawToCookedCoefficient"); 
    private String toQuotedDecimal(String line) {
        for(var key : PRECISE_DECIMALS) {
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
    
    private Respondent24 respondent(Map<String, Object> map) {
        var parser = new YamlParser(map);
        return new Respondent24(
            parser.string("alias"), 
            parser.localDate("dateOfBirth"), 
            parser.sex(), 
            parser.collection("interviews").stream()
                .map(InterviewSetYamlParser::interview)
                .collect(Can.toCan())
            );
    }
    
    private Interview24 interview(Map<String, Object> map) {
        var parser = new YamlParser(map);
        var interview = new Interview24(
            parser.localDate("interviewDate"), 
            parser.localDate("consumptionDate"), 
            respondentSupplementaryData(parser.property("respondentSupplementaryData").orElseThrow()), 
            parser.collection("meals").stream()
                .map(InterviewSetYamlParser::meal)
                .collect(Can.toCan()));
        parser.annotations().forEach(interview::putAnnotation);
        return interview;
    }
    
    private RespondentSupplementaryData24 respondentSupplementaryData(Map<String, Object> map) {
        var parser = new YamlParser(map);
        return new RespondentSupplementaryData24(
            parser.string("specialDietId"), 
            parser.string("specialDayId"), 
            parser.decimal("heightCM"), 
            parser.decimal("weightKG"));
    }
    
    private Meal24 meal(Map<String, Object> map) {
        var parser = new YamlParser(map);
        return new Meal24(
            parser.localTime("hourOfDay"), 
            parser.string("foodConsumptionOccasionId"), 
            parser.string("foodConsumptionPlaceId"), 
            parser.collection("memorizedFood").stream()
                .map(InterviewSetYamlParser::memorizedFood)
                .collect(Can.toCan()));
    }
    
    private MemorizedFood24 memorizedFood(Map<String, Object> map) {
        var parser = new YamlParser(map);
        return new MemorizedFood24(
            parser.string("name"),
            parser.collection("topLevelRecords").stream()
                .map(InterviewSetYamlParser::record)
                .collect(Can.toCan()));
    }
    
    private Record24 record(Map<String, Object> map) {
        var parser = new YamlParser(map);
        var recordType = Record24.Type.valueOf(parser.string("type"));
        
        return switch(recordType) {
            case COMMENT -> new Record24.Comment(ObjectRef.empty(),
                recordType, parser.string("name"), parser.sid("sid"), parser.sids("facetSids"),
                parser.annotations());
            case COMPOSITE -> new Record24.Composite(ObjectRef.empty(),
                recordType, parser.string("name"), parser.sid("sid"), parser.sids("facetSids"),
                parser.collection("subRecords").stream()
                    .map(InterviewSetYamlParser::record)
                    .collect(Can.toCan()),
                parser.annotations());
            case FOOD -> new Record24.Food(ObjectRef.empty(), 
                recordType, parser.string("name"), parser.sid("sid"), parser.sids("facetSids"), 
                parser.decimal("amountConsumed"), parser.consumptionUnit(), parser.decimal("rawToCookedCoefficient"),
                parser.property("typeOfFatUsedDuringCooking").map(InterviewSetYamlParser::typeOfFatUsed), 
                parser.property("typeOfMilkOrLiquidUsedDuringCooking").map(InterviewSetYamlParser::typeOfMilkOrLiquidUsed), 
                parser.annotations());
            
            case FRYING_FAT -> new Record24.FryingFat(ObjectRef.empty(), 
                recordType, parser.string("name"), parser.sid("sid"), parser.sids("facetSids"),
                parser.decimal("amountConsumed"), parser.consumptionUnit(), parser.decimal("rawToCookedCoefficient"), 
                parser.annotations());
            case PRODUCT -> throw new UnsupportedOperationException("Unimplemented case: " + recordType);
            case TYPE_OF_FAT_USED -> throw new UnsupportedOperationException("Unimplemented case: " + recordType);
            case TYPE_OF_MILK_OR_LIQUID_USED -> throw new UnsupportedOperationException("Unimplemented case: " + recordType);
        };
    }
    
    private TypeOfFatUsed typeOfFatUsed(Map<String, Object> map) {
        var parser = new YamlParser(map);
        return Record24.typeOfFatUsed(parser.string("name"), parser.sid("sid"), parser.sids("facetSids"));
    }
    
    private TypeOfMilkOrLiquidUsed typeOfMilkOrLiquidUsed(Map<String, Object> map) {
        var parser = new YamlParser(map);
        return Record24.typeOfMilkOrLiquidUsed(parser.string("name"), parser.sid("sid"), parser.sids("facetSids"));
    }
    
    private record YamlParser(Map<String, Object> map) {
        String string(String key) {
            return "" + map.get(key);
        }
        LocalDate localDate(String key) {
            var value = map.get(key);
            if(value==null) return null; // preserve null
            return LocalDate.parse(value.toString());
        }
        LocalTime localTime(String key) {
            var value = map.get(key);
            if(value==null) return null; // preserve null
            return LocalTime.parse(value.toString());
        }
        BigDecimal decimal(String key) {
            var value = map.get(key);
            if(value==null) return null; // preserve null
            return switch(value) {
                case String s -> { 
                        try { 
                            yield new BigDecimal(s); 
                        } catch (Exception e) {
                            throw _Exceptions.illegalArgument(e, "not a decimal ‹%s› in key ‹%s›", value, key);
                        }
                    }
                case Double d -> new BigDecimal(d);
                case Integer i -> new BigDecimal(i);
                case Long l -> new BigDecimal(l);
                default -> throw new IllegalArgumentException("Unexpected decimal: " + value.getClass());
            };
        }
        SemanticIdentifier sid(String key) {
            var value = map.get(key);
            if(value==null) return null; // preserve null
            return SemanticIdentifier.parse(value.toString());
        }
        SemanticIdentifierSet sids(String key) {
            var value = map.get(key);
            if(value==null) return null; // preserve null
            return SemanticIdentifierSet.parse(value.toString());
        }
        ConsumptionUnit consumptionUnit() {
            return ConsumptionUnit.valueOf(string("consumptionUnit"));
        }
        Sex sex() {
            return Sex.valueOf(string("sex"));
        }
        Optional<Map<String, Object>> property(String key) {
            var v = map.get(key);
            return v!=null 
                ? Optional.of(_Casts.uncheckedCast(v))
                : Optional.empty();
        }
        List<Map<String, Object>> collection(String key) {
            var v = map.get(key);
            return v!=null 
                ? _Casts.uncheckedCast(v)
                : List.of();
        }
        Map<String, Serializable> annotations() {
            var annotations = new LinkedHashMap<String, Serializable>();
            property("annotations").orElseGet(Map::of)
                .forEach((k, v)->annotations.put(k, (Serializable)v));
            return annotations;
        }
    }

}