package dita.recall24.reporter.tabular;

import java.util.ArrayList;
import java.util.List;

import org.apache.causeway.commons.collections.Can;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Early draft.
 */
@UtilityClass
public class Tabular {

    public record Column<T>(
            int index,
            @NonNull String name,
            @NonNull String description,
            @NonNull Class<T> javaType) {
    }

    public interface Value<T> {
            String title();
            T pojo();
    }

    public interface Row {
        <T> Can<Value<T>> cellElements(final Column<T> column);
    }

    public record Table(
            @NonNull String name,
            @NonNull Can<Column<?>> columns,
            @NonNull Can<Row> rows) {
    }

    public record TableBuilder(
            @NonNull String name,
            @NonNull Can<Column<?>> columns,
            @NonNull List<Row> rows) {
        public TableBuilder addRow(final Row row) {
            rows.add(row);
            return this;
        }
        public Table build() {
            return new Table(name, columns, Can.ofCollection(rows));
        }
    }

    public static TableBuilder tableBuilder(
            @NonNull final String name,
            @NonNull final Can<Column<?>> columns) {
        return new TableBuilder(name, columns, new ArrayList<>());
    }

    public Can<Column<?>> columns(@NonNull final Class<?> domainType) {
        //var elementType = MetaModelContext.instanceElseFail().specForTypeElseFail(domainType);
        return Can.empty(); //requires meta-model
    }

}
