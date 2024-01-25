package dita.vault.model;

import java.util.UUID;

public record VaultClob(
        UUID uuid,
        char[] clob) {
}
