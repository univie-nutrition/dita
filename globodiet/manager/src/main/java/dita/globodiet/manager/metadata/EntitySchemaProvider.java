package dita.globodiet.manager.metadata;

import java.util.Map;

import org.apache.causeway.applib.id.LogicalType;

import io.github.causewaystuff.companion.codegen.model.Schema;

public record EntitySchemaProvider(Map<String, Schema.Entity> entitiesByLogicalName) {

    public Schema.Entity get(final LogicalType logicalType) {
        return entitiesByLogicalName().get(logicalType.logicalName());
    }

    public Schema.Domain asDomain(){
        return new Schema.Domain(new Schema.ModuleNaming("", ""), Map.of(), entitiesByLogicalName());
    }
}
