package dita.vault.model;

import java.util.UUID;

public record VaultBlob(
        UUID uuid,
        byte[] blob) {
}
