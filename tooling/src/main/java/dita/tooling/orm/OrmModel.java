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

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.javapoet.TypeName;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedFunction;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.internal.primitives._Ints;
import org.apache.causeway.commons.io.TextUtils;
import org.apache.causeway.commons.io.YamlUtils;

import dita.commons.types.SneakyRef;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.UtilityClass;

/**
 * Read and write schema model from and to YAML format.
 */
@UtilityClass
public class OrmModel {

    public record Entity(
            String name,
            String namespace,
            String table,
            String title,
            String icon,
            List<String> description,
            List<Field> fields) {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        static Entity parse(final Map.Entry<String, Map> entry) {
            val map = entry.getValue();
            val fieldsAsMap = (Map<String, Map>)map.get("fields");
            final String namespace = (String)map.get("namespace");
            final String name = _Strings.nonEmpty((String)map.get("name"))
                    .orElseGet(()->
                        entry.getKey().startsWith(namespace)
                            ? entry.getKey().substring(namespace.length() + 1)
                            : entry.getKey()
                    );
            val entity = new Entity(name,
                    namespace,
                    (String)map.get("table"),
                    (String)map.get("title"),
                    (String)map.get("icon"),
                    parseMultilineString((String)map.get("description")),
                    new ArrayList<>());
            fieldsAsMap.entrySet().stream()
                    .map(IndexedFunction.zeroBased((index, innerEntry)->Field.parse(entity, index, innerEntry)))
                    .forEach(entity.fields()::add);
            return entity;
        }
        public String key() {
            return String.format("%s.%s", namespace, name);
        }
        String toYaml() {
            val yaml = new YamlWriter();
            yaml.write(key(), ":").nl();
            yaml.ind().write("namespace: ", namespace).nl();
            yaml.ind().write("table: ", table).nl();
            yaml.ind().write("title: ", title).nl();
            yaml.ind().write("icon: ", icon).nl();
            yaml.ind().write("description:").multilineStartIfNotEmtpy(description).nl();
            description.forEach(line->
                yaml.ind().ind().write(line).nl());
            yaml.ind().write("fields:").nl();
            fields.forEach(field->{
                yaml.ind().ind().write(field.name(), ":").nl();
                yaml.ind().ind().ind().write("column: ", field.column()).nl();
                yaml.ind().ind().ind().write("column-type: ", field.columnType()).nl();
                yaml.ind().ind().ind().write("required: ", ""+field.required()).nl();
                yaml.ind().ind().ind().write("unique: ", ""+field.unique()).nl();
                yaml.ind().ind().ind().write("foreignKeys:").multilineStartIfNotEmtpy(field.foreignKeys).nl();
                field.foreignKeys.forEach(line->
                    yaml.ind().ind().ind().ind().write(line).nl());
                yaml.ind().ind().ind().write("description:").multilineStartIfNotEmtpy(field.description).nl();
                field.description.forEach(line->
                    yaml.ind().ind().ind().ind().write(line).nl());
            });
            return yaml.toString();
        }
        public String formatDescription(final String continuation) {
            if(isMultilineStringBlank(description)) {
                return "has no description";
            }
            return description()
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.joining(continuation));
        }
        public Optional<OrmModel.Field> lookupFieldByColumnName(final String columnName) {
            return fields().stream()
                    .filter(f->f.column().equalsIgnoreCase(columnName))
                    .findFirst();
        }
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entity)) {
                return false;
            }
            return this.key().equals(((Entity) o).key());
        }
        @Override
        public int hashCode() {
            return key().hashCode();
        }
    }

    public record Field(
            SneakyRef<Entity> parentRef,
            int ordinal,
            String name,
            String column,
            String columnType,
            boolean required,
            boolean unique,
            boolean plural,
            List<String> foreignKeys,
            List<String> description) {
        @SuppressWarnings("rawtypes")
        static Field parse(final Entity parent, final int ordinal, final Map.Entry<String, Map> entry) {
            val map = entry.getValue();
            return new Field(SneakyRef.of(parent),
                    ordinal,
                    entry.getKey(),
                    (String)map.get("column"),
                    (String)map.get("column-type"),
                    (Boolean)map.get("required"),
                    (Boolean)map.get("unique"),
                    (boolean)Optional.ofNullable((Boolean)map.get("plural")).orElse(false),
                    parseMultilineString((String)map.get("foreignKeys")),
                    parseMultilineString((String)map.get("description")));
        }
        public Entity parentEntity() {
            return parentRef.value();
        }
        public TypeName asJavaType() {
            return _TypeMapping.dbToJava(columnType(), !required);
        }
        public boolean hasForeignKeys() {
            return foreignKeys.size()>0;
        }
        public boolean isBooleanPrimitive() {
            return asJavaType().equals(TypeName.BOOLEAN);
        }
        public String getter() {
            return (isBooleanPrimitive() ? "is" : "get")
                    + _Strings.capitalize(name());
        }
        public int maxLength() {

            if(_TypeMapping.isMaxLengthSuppressedFor(columnType())) {
                return -1;
            }

            val lengthLiteralOrColumnType = TextUtils.cutter(columnType())
                .keepAfter("(")
                .keepBeforeLast(")")
                .getValue();

            final int parsedMaxLength = _Ints.parseInt(lengthLiteralOrColumnType, 10).orElse(-1);

            //H2 max
            if(parsedMaxLength>1000000000) {
                return 1000000000;
            }

            return parsedMaxLength;
        }
        public String formatDescription(final String continuation) {
            if(isMultilineStringBlank(description)) {
                return "has no description";
            }
            return description()
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.joining(continuation));
        }
        public String sequence() {
            return "" + (ordinal + 1);
        }
        public Can<Field> foreignFields(final Schema schema) {
            return foreignKeys().stream()
                    .map(schema::lookupForeignKeyFieldElseFail)
                    .collect(Can.toCan());
        }
        @Deprecated
        public Can<Join> incomingJoins(final Schema schema) {
            val tableDotColumn = parentEntity().table()  + "." + column();
            // incoming relations
            return schema.entities().values().stream()
                .filter(dependant->!dependant.equals(parentEntity()))
                .flatMap(dependant->dependant.fields().stream())
                .filter(dependantField->{
                    if(!dependantField.hasForeignKeys()) return false;
                    return dependantField.foreignKeys().stream()
                            .anyMatch(tableDotColumn::equalsIgnoreCase);
                })
                .map(dependantField->new Join(this, dependantField))
                .collect(Can.toCan());
        }
    }

    @Deprecated
    public record Join(
            Field localField,
            Field foreignField) {
        public Entity localEntity() {
            return localField.parentEntity();
        }
        public Entity foreignEntity() {
            return foreignField.parentEntity();
        }
    }

    public record Schema(Map<String, Entity> entities) {
        public static Schema of(final Iterable<Entity> entities) {
            val schema = new Schema(new LinkedHashMap<String, OrmModel.Entity>());
            for(val entity: entities) {
                schema.entities().put(entity.key(), entity);
            }
            return schema;
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public static Schema fromYaml(final String yaml) {
            val entities = new LinkedHashMap<String, OrmModel.Entity>();
            YamlUtils.tryRead(Map.class, yaml)
            .ifFailureFail()
            .getValue()
            .map(map->(Map<String, Map>)map)
            .ifPresent(map->{
                map.entrySet().stream()
                .map(Entity::parse)
                .forEach(entity->entities.put(entity.key(), entity));
            });
            return new Schema(entities);
        }
        public String toYaml() {
            val sb = new StringBuilder();
            for(val entity : entities().values()) {
                sb.append(entity.toYaml());
            }
            return sb.toString();
        }
        @SneakyThrows
        public void writeToFileAsYaml(final File file) {
            TextUtils.writeLinesToFile(List.of(toYaml()), file, StandardCharsets.UTF_8);
        }
        public Optional<OrmModel.Entity> lookupEntityByTableName(final String tableName) {
            return entities().values()
                    .stream()
                    .filter(e->e.table().equalsIgnoreCase(tableName))
                    .findFirst();
        }
        public Can<Entity> findEntitiesWithoutRelations(){
            val foreignKeyFields = // as table.column literal
                    entities().values().stream().flatMap(fe->fe.fields().stream())
                        .flatMap(ff->ff.foreignKeys().stream())
                        .map(String::toLowerCase)
                        .collect(Collectors.toSet());
            val entitiesWithoutRelations =  entities().values().stream()
                .filter(e->!e.fields().stream().anyMatch(f->f.hasForeignKeys()))
                .filter(e->!e.fields().stream().anyMatch(f->
                    foreignKeyFields.contains(e.table().toLowerCase() + "." + f.column().toLowerCase())))
                .sorted((a, b)->_Strings.compareNullsFirst(a.name(), b.name()))
                .collect(Can.toCan());
            return entitiesWithoutRelations;
        }
        // -- HELPER
        private Optional<OrmModel.Field> lookupForeignKeyField(final String tableDotColumn) {
            val parts = _Strings.splitThenStream(tableDotColumn, ".")
                    .collect(Can.toCan());
            _Assert.assertEquals(2, parts.size(), ()->String.format(
                    "could not parse foreign key '%s'", tableDotColumn));
            val tableName = parts.getElseFail(0);
            val columnName = parts.getElseFail(1);
            return lookupEntityByTableName(tableName)
                    .flatMap(entity->entity.lookupFieldByColumnName(columnName));
        }
        private OrmModel.Field lookupForeignKeyFieldElseFail(final String tableDotColumn) {
            return lookupForeignKeyField(tableDotColumn)
                    .orElseThrow(()->_Exceptions.noSuchElement("foreign key not found '%s'", tableDotColumn));
        }
    }

    /**
     * JUnit support.
     */
    public Can<Schema> examples() {
        val entity = new Entity("FoodList", "dita", "FOODS", "name", "fa-pencil", List.of("Food List and Aliases"),
                new ArrayList<OrmModel.Field>());
        val field = new Field(SneakyRef.of(entity), /*ordinal*/0, "name", "NAME", "nvarchar(100)", true, false, false,
                List.of(), List.of("aa", "bb", "cc"));
        entity.fields().add(field);
        return Can.of(
                Schema.of(List.of(entity)));
    }

    // -- HELPER

    private static class YamlWriter {
        final StringBuilder sb = new StringBuilder();
        @Override public String toString() { return sb.toString(); }
        YamlWriter multilineStartIfNotEmtpy(final List<?> list) {
            if(!_NullSafe.isEmpty(list)) sb.append(" |");
            return this;
        }
        YamlWriter write(final String ...s) {
            for(val str:s) sb.append(str);
            return this;
        }
        YamlWriter ind() {
            sb.append("  ");
            return this;
        }
        YamlWriter nl() {
            sb.append('\n');
            return this;
        }
    }

    private static List<String> parseMultilineString(final String input) {
        return _Strings.splitThenStream(input, "\n")
            .filter(_Strings::isNotEmpty)
            .collect(Collectors.toList());
    }

    private boolean isMultilineStringBlank(final List<String> lines) {
        return _NullSafe.size(lines)==0
            ? true
            : _Strings.isNullOrEmpty(lines.stream().collect(Collectors.joining("")).trim());
    }


}
