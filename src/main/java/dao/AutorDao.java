package dao;

import domain.model.Autor;
import io.vavr.control.Either;

import java.util.List;

public interface AutorDao {
    Either<RuntimeException, List<Autor>> getAll();
    Either<RuntimeException, Autor> get(String nombre);
}
