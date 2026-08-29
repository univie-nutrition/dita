package dita.recall24.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.io.YamlUtils.YamlWriter;

import dita.commons.sid.SemanticIdentifier;
import dita.commons.sid.SemanticIdentifierSet;
import dita.recall24.dto.Correction24.CompositeCorr;
import dita.recall24.dto.Correction24.CompositeCorr.Addition;
import dita.recall24.dto.Correction24.CompositeCorr.Coordinates;
import dita.recall24.dto.Correction24.CompositeCorr.Deletion;
import dita.recall24.dto.Correction24.FoodByNameCorr;
import dita.recall24.dto.Correction24.RespondentCorr;
import io.github.causewaystuff.commons.base.types.NamedPath;
import lombok.SneakyThrows;

/**
 * Convert {@link Correction24} DTO to YAML,
 * in contrast to Jackson,
 * with support of emitting comments.
 */
record Correction24YamlWriter(
		YamlWriter writer,
    	DateTimeFormatter localDateTimeFormat) {

	Correction24YamlWriter() {
		this(new YamlWriter(),
    		DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

    public String toYaml(final Correction24 correction) {
        if (correction == null)
			return "null";

        // 1. Respondents
        List<RespondentCorr> respondents = correction.respondents();
        if (respondents != null && !respondents.isEmpty()) {
            writer.write("respondents:").nl();
            for (RespondentCorr resp : respondents) {
                writeRespondentCorr(resp);
            }
        } else {
            writer.write("respondents: []").nl();
        }

        // 2. FoodByName
        List<FoodByNameCorr> foodByName = correction.foodByName();
        if (foodByName != null && !foodByName.isEmpty()) {
            writer.write("foodByName:").nl();
            for (FoodByNameCorr food : foodByName) {
                writeFoodByNameCorr(food);
            }
        } else {
            writer.write("foodByName: []").nl();
        }

        // 3. Composites
        List<CompositeCorr> composites = correction.composites();
        if (composites != null && !composites.isEmpty()) {
            writer.write("composites:").nl();
            for (CompositeCorr comp : composites) {
                writeCompositeCorr(comp);
            }
        } else {
            writer.write("composites: []").nl();
        }

        String result = writer.toString();
        return result;
    }

    private void writeRespondentCorr(final RespondentCorr resp) {
    	kv(0, "- alias", resp.alias());
        kv(1, "withdraw", resp.withdraw());
        kv(1, "newAlias", resp.newAlias());
        kv(1, "dateOfBirth", resp.dateOfBirth());
        kv(1, "sex", resp.sex());
    }

    private void writeFoodByNameCorr(final FoodByNameCorr food) {
        kv(0, "- name", food.name());
        kv(1, "sid", food.sid());
    }

    private void writeCompositeCorr(final CompositeCorr comp) {
        writer.sq().write("coordinates:").nl();
        writeCoordinates(comp.coordinates()); // Indent 3 for properties under coordinates
        writeComments(1, comp.comments());

        kv(1, "rename", comp.rename());
        kv(1, "groupSid", comp.groupSid());

        // additions
        List<Addition> additions = comp.additions();
        if (additions != null && !additions.isEmpty()) {
            writer.ind().write("additions:").nl();
            for (Addition add : additions) {
                writeAddition(add);
            }
        } else {
            writer.ind().write("additions: []").nl();
        }
        // deletions
        List<Deletion> deletions = comp.deletions();
        if (deletions != null && !deletions.isEmpty()) {
            writer.ind().write("deletions:").nl();
            for (Deletion del : deletions) {
                writeDeletion(del);
            }
        } else {
            writer.ind().write("deletions: []").nl();
        }
    }

	@SneakyThrows
    private void writeCoordinates(final Coordinates coords) {
        if (coords == null)
        	return;
        kv(2, "sid", coords.sid());
        kv(2, "respondentId", coords.respondentId());
        kv(2, "interviewOrdinal", coords.interviewOrdinal());
        kv(2, "mealHourOfDay", coords.mealHourOfDay());
        kv(2, "name", coords.name());
        kv(2, "source", coords.source());
    }

    private void writeAddition(final Addition add) {
    	writeComments(2, add.comments());
        kv(1, "- sid", add.sid());
        kv(2, "amountGrams", add.amountGrams());
        kv(2, "facets", add.facets());
    }

    private void writeDeletion(final Deletion del) {
    	writeComments(2, del.comments());
    	kv(1, "- sid", del.sid());
    }

    /** key: value w/ newline; line is not emitted if value is null; */
    private <T> void kv(final int indentCount, final String key, final T value) {
    	if(value==null)
    		return;
    	writer.ind(indentCount).write(key, ": ");
    	switch (value) {
    		case BigDecimal v -> writer.write(v.toString());
			case Integer i -> writer.write(""+i);
			case Long i -> writer.write(""+i);
			case Short i -> writer.write(""+i);
			case Byte i -> writer.write(""+i);
			case Double f -> writer.write(""+f);
			case Float f -> writer.write(""+f);
			case Boolean b -> writer.write(""+b);
			case Enum<?> b -> writer.dq(b.name());
			case SemanticIdentifier sid -> writer.dq(sid.toStringNoBox());
			case SemanticIdentifierSet sidSet -> writer.dq(sidSet.toStringNoBox());
			case NamedPath v -> writer.dq(v.toString("/"));
			case LocalTime v -> writer.dq(v.format(localDateTimeFormat));
			default -> writer.dq(value.toString());
		};
		writer.nl();
    }

    private void writeComments(final int indentCount, final List<String> comments) {
    	_NullSafe.stream(comments)
    	.forEach(comment->
    	writer.ind(indentCount).write("# ").write(comment).nl());
    }
}