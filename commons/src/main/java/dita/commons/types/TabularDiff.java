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
package dita.commons.types;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.IndexedConsumer;
import org.apache.causeway.commons.io.YamlUtils.YamlWriter;
import org.springframework.util.Assert;

import dita.commons.types.TabularData.Column;
import dita.commons.types.TabularData.Row;
import dita.commons.types.TabularData.Table;

public record TabularDiff(
		TabularData main,
		TabularData base,
		TabularData.SecondaryKeyProvider secondaryKeyProvider,
		TabularData.Format formatOptions,
		TableDiff tableDiff) {

	public TabularDiff(
			final TabularData main,
			final TabularData base,
			final TabularData.SecondaryKeyProvider secondaryKeyProvider) {
		this(main, base, secondaryKeyProvider, TabularData.Format.defaults(), new TableDiff());

		var mainTablesByKey = main.dataTables()
			.toMap(Table::key, (_, _)->{throw new UnsupportedOperationException();}, TreeMap::new);
		var baseTablesByKey = base.dataTables()
			.toMap(Table::key, (_, _)->{throw new UnsupportedOperationException();}, TreeMap::new);

		Assert.isTrue(mainTablesByKey.keySet().equals(baseTablesByKey.keySet()),
				()->"table key sets need to be equal (table adding/removing not supported)");

		tableDiff.tableDiff().process(
				mainTablesByKey.values(), baseTablesByKey.values(),
				Table::key, Table::key,
				(a, b)->{
					var rowDiffHolder = rowDiff(a, b);
					var rowDiff = rowDiffHolder.rowDiff();
					var additions = rowDiff.leftOuter();
					var deletions = rowDiff.rightOuter();
					var changes = rowDiff.innerMismatch();
					if(additions.isEmpty()
							&& deletions.isEmpty()
							&& changes.isEmpty())
						return true;
					tableDiff.rowDiffByTableKey.put(rowDiffHolder.tableKey(), rowDiffHolder);
					return false;
				});
	}

	public String toYaml() {
        var yaml = new YamlWriter();
        yaml.write("changed tables:").nl();

        tableDiff.steamRowDiffs()
        	.forEach(rowDiffHolder->{
        		yaml.ind().sq().write(rowDiffHolder.tableKey(), ":").nl();

        		// col header
                yaml.ind().ind().ind().write("cols:").nl();
                rowDiffHolder.columns().forEach(col->
                    yaml.ind().ind().ind().sq().dq(colTitle(col)).nl());

                if(!rowDiffHolder.additions().isEmpty()) {
                	yaml.ind().ind().ind().write("added rows:").nl();
                	rowDiffHolder.additions().stream()
	                	.map(this::formatRow)
	                	.sorted()
	                	.forEach(rowLiteral->{
	                		yaml.ind().ind().ind().sq().dq(rowLiteral).nl();
	                	});
                }
                if(!rowDiffHolder.deletions().isEmpty()) {
                	yaml.ind().ind().ind().write("removed rows:").nl();
                	rowDiffHolder.deletions().stream()
	                	.map(this::formatRow)
	                	.sorted()
	                	.forEach(rowLiteral->{
	                		yaml.ind().ind().ind().sq().dq(rowLiteral).nl();
	                	});
                }
                if(!rowDiffHolder.changes().isEmpty()) {
                	yaml.ind().ind().ind().write("changed rows:").nl();
					rowDiffHolder.changes().forEach((final Pair<Row, Row> change)->{
						var newRow = change.left();
						var oldRow = change.right();
						final var mergedRow = new Row(new ArrayList<>());
						rowDiffHolder.columns().forEach(IndexedConsumer.zeroBased((i, _)->{
							var newCell = formatCell(newRow.cellLiterals().get(i));
							var oldCell = formatCell(oldRow.cellLiterals().get(i));
							mergedRow.cellLiterals().add(newCell.equals(oldCell)
									? newCell
									: "%s->%s".formatted(oldCell, newCell));
						}));
						yaml.ind().ind().ind().sq().dq(formatRow(mergedRow)).nl();
					});
                }
        	});

        return yaml.toString();
	}

	private String formatRow(final Row row) {
		return row.cellLiterals()
	        .stream()
	        .map(this::formatCell)
            .collect(Collectors.joining(formatOptions.columnSeparator()));
	}

	private String colTitle(final Column col) {
		return col.description()
                .map(desc->String.format("%s: %s", col.name(), desc.replace('\n', '|')))
                .orElse(col.name());
	}

	private String formatCell(final String cellValue) {
        return cellValue==null
                ? formatOptions.nullSymbol()
                : cellValue.replaceAll("\"", formatOptions.doubleQuoteSymbol());
    }

	String formatSecondaryKey(final Row row, final BitSet secKey) {
		return row.secondaryKey(secKey)
				.stream()
				.map(this::formatCell)
				.collect(Collectors.joining(formatOptions.columnSeparator()));
	}

	public record TableDiff(
			Diff<Table, Table> tableDiff,
			Map<String, RowDiff> rowDiffByTableKey) {
		TableDiff() {
			this(Diff.typed(Table.class, Table.class), new TreeMap<String, RowDiff>());
		}
		Stream<RowDiff> steamRowDiffs() {
			return rowDiffByTableKey.values().stream();
		}
	}

	public record RowDiff(
			Table mainTable,
			Table baseTable,
			BitSet secKey,
			Diff<Row, Row> rowDiff) {

		Can<Column> columns() { return mainTable().columns(); }
		List<Row> additions() { return rowDiff.leftOuter(); }
		List<Row> deletions() { return rowDiff.rightOuter(); }
		List<Pair<Row, Row>> changes() { return rowDiff.innerMismatch(); }

		String tableKey() { return mainTable().key(); }
	}

	RowDiff rowDiff(final Table leftTable, final Table rightTable) {
		Assert.isTrue(leftTable.columns().equals(rightTable.columns()),
				()->"table columns differ in table %s".formatted(leftTable.key()));
		var secKey = secondaryKeyProvider.lookupSecondaryKeyElseFail(leftTable.key());
		Assert.isTrue(secKey.cardinality()>0, ()->"no secondary key in table " + leftTable.key());
		var rowDiff = Diff.typed(Row.class, Row.class);
		rowDiff.process(
				leftTable.rows(), rightTable.rows(),
				row->formatSecondaryKey(row, secKey),
				row->formatSecondaryKey(row, secKey),
				Row::equals);
		return new RowDiff(leftTable, rightTable, secKey, rowDiff);
	}

}
