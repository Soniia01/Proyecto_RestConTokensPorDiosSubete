package domain.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Libro {
    private UUID uuid;
    private String name;
    private List<String> generos;
    private String autor;

    public Libro(String name) {
        this.uuid = java.util.UUID.randomUUID();
        this.name = name;
    }

    public Libro(String name, List<String> generos, String autor) {
        this.uuid = java.util.UUID.randomUUID();
        this.name = name;
        this.generos = generos;
    }

}
