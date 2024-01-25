package dita.vault.model;

import java.util.UUID;

import org.apache.causeway.applib.value.NamedWithMimeType;

public record VaultDocument(
        UUID uuid,
        String checkSum,
        NamedWithMimeType data) {
}
