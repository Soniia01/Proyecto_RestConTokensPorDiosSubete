package dao;

import domain.model.Autor;
import domain.model.Libro;
import io.vavr.control.Either;

import java.util.List;

public interface LibroDao {
    Either<RuntimeException, List<Libro>> getAll();

    Either<RuntimeException, List<Libro>> getAllByAutor(String author);

    Either<RuntimeException, Libro> get(String titulo);

    Either<RuntimeException, Integer> save(Libro libro, Autor autor);

    Either<RuntimeException, Integer> update(Libro libro);

    Either<RuntimeException, Integer> delete(Libro libro);
}
