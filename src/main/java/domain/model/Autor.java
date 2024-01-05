package domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Autor {
    private UUID uuid;
    private String name;
    private int librosPublicados;

    public Autor(String name, int librosPublicados) {
        this.uuid = java.util.UUID.randomUUID();
        this.name = name;
        this.librosPublicados = librosPublicados;
    }

    public Autor(String name) {
        this.uuid = java.util.UUID.randomUUID();
        this.name = name;
    }
}
