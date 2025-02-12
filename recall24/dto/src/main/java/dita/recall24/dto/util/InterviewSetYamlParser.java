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
import dita.recall24.dto.Respondent24;
import dita.recall24.dto.RespondentSupplementaryData24;
import dita.recall24.dto.Annotated.Annotation;
import io.github.causewaystuff.commons.base.types.internal.ObjectRef;

@UtilityClass
public class InterviewSetYamlParser {

    public InterviewSet24 parseYaml(@Nullable String yaml) {
        if(!StringUtils.hasLength(yaml)) return InterviewSet24.empty();
        
        // put decimal values into quotes, so those are converted to type String and can be used with BigDecimal to full precision
        yaml = TextUtils.readLines(yaml).map(InterviewSetYamlParser::toQuotedDecimal).join("\n");
        
        var rootMap = YamlUtils.tryRead(Map.class, yaml).valueAsNonNullElseFail();
        var parser = new YamlParser(_Casts.uncheckedCast(rootMap));
        return new InterviewSet24(parser.collection("respondents").stream()
                .map(InterviewSetYamlParser::respondent)
                .collect(Can.toCan()),
                parser.annotations()        
            );
    }
    
    // -- HELPER
    
    private final List<String> PRECISE_DECIMALS = List.of("amountConsumed", "rawPerCookedRatio"); 
    private String toQuotedDecimal(String line) {
        for(var key : PRECISE_DECIMALS) {
            if(line.trim().startsWith(key)) {
                return _Strings.splitThenApplyRequireNonEmpty(line, ":", (lhs, rhs)-> lhs + ": \"" + rhs.trim() + "\"").orElseThrow();
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
            respondentSupplementaryData(parser.property("respondentSupplementaryData")), 
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
                parser.decimal("amountConsumed"), parser.consumptionUnit(), parser.decimal("rawPerCookedRatio"),
                /*TODO not implemented
                Optional<TypeOfFatUsed> typeOfFatUsedDuringCooking,
                Optional<TypeOfMilkOrLiquidUsed> typeOfMilkOrLiquidUsedDuringCooking*/    
                Optional.empty(), Optional.empty(), 
                parser.annotations());
            
            case FRYING_FAT -> new Record24.FryingFat(ObjectRef.empty(), 
                recordType, parser.string("name"), parser.sid("sid"), parser.sids("facetSids"),
                parser.decimal("amountConsumed"), parser.consumptionUnit(), parser.decimal("rawPerCookedRatio"), 
                parser.annotations());
            case PRODUCT -> throw new UnsupportedOperationException("Unimplemented case: " + recordType);
            case TYPE_OF_FAT_USED -> throw new UnsupportedOperationException("Unimplemented case: " + recordType);
            case TYPE_OF_MILK_OR_LIQUID_USED -> throw new UnsupportedOperationException("Unimplemented case: " + recordType);
        };
    }
    
    private record YamlParser(Map<String, Object> map) {
        String string(String key) {
            return "" + map.get(key);
        }
        LocalDate localDate(String key) {
            return LocalDate.parse(string(key));
        }
        LocalTime localTime(String key) {
            return LocalTime.parse(string(key));
        }
        BigDecimal decimal(String key) {
            return new BigDecimal(string(key));
        }
        SemanticIdentifier sid(String key) {
            return SemanticIdentifier.parse(string(key));
        }
        SemanticIdentifierSet sids(String key) {
            return SemanticIdentifierSet.parse(string(key));
        }
        ConsumptionUnit consumptionUnit() {
            return ConsumptionUnit.valueOf(string("consumptionUnit"));
        }
        Sex sex() {
            return Sex.valueOf(string("sex"));
        }
        Map<String, Object> property(String key) {
            var v = map.get(key);
            return v!=null 
                ? _Casts.uncheckedCast(v)
                : Map.of();
        }
        List<Map<String, Object>> collection(String key) {
            var v = map.get(key);
            return v!=null 
                ? _Casts.uncheckedCast(v)
                : List.of();
        }
        @SuppressWarnings("rawtypes")
        Map<String, Annotation> annotations() {
            var annotations = new LinkedHashMap<String, Annotation>();
            property("annotations")
                .forEach((k, v)->annotations.put(k, new Annotation(k, (Serializable)((Map) v).get("value"))));
            return annotations;
        }
    }

}