package dao.JDBC;


import common.ConstantesErrores;
import common.StaticLists;
import dao.LibroDao;
import domain.error.GeneralErrorException;
import domain.model.Autor;
import domain.model.Libro;
import io.vavr.control.Either;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Data
public class LibroDaoImpl implements LibroDao {


    @Override
    public Either<RuntimeException, List<Libro>> getAll() {
        Either<RuntimeException, List<Libro>> either;
        try {
            List<Libro> listaLibros = StaticLists.libros;
            either = Either.right(listaLibros);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.UNABLE_TO_GET_ALL_BOOKS);
        }
        return either;
    }

    @Override
    public Either<RuntimeException, List<Libro>> getAllByAutor(String author) {
        Either<RuntimeException, List<Libro>> either;
        List<Libro> librosPorAutor = new ArrayList<>();
        try {
            for (Libro libro1 : StaticLists.libros) {
                if (libro1.getAutor().equalsIgnoreCase(author)) {
                    librosPorAutor.add(libro1);
                }
            }
            either = Either.right(librosPorAutor);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.ERROR_FINDING_BOOKS_BY + author);
        }
        return either;
    }

    @Override
    public Either<RuntimeException, Libro> get(String titulo) {
        Either<RuntimeException, Libro> either;
        Libro libro = null;
        try {
            for (Libro libro1 : StaticLists.libros) {
                if (libro1.getName().equalsIgnoreCase(titulo)) {
                    libro = libro1;
                }
            }
            either = Either.right(libro);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.NO_RESULTS_FOR + titulo);
        }
        return either;
    }

    @Override
    public Either<RuntimeException, Integer> save(Libro libro, Autor autor) {
        Either<RuntimeException, Integer> either = null;
        int rowsAffected = 0;
        for (Autor autor1 : StaticLists.autores) {
            if (!autor1.getName().equalsIgnoreCase(autor.getName())) {
                StaticLists.autores.add(autor);
            }
        }
        for (Libro libro1 : StaticLists.libros) {
            if (!libro1.getName().equalsIgnoreCase(libro.getName())) {
                StaticLists.libros.add(libro);
                rowsAffected = 1;
                either = Either.right(rowsAffected);
            } else {
                throw new GeneralErrorException(ConstantesErrores.BOOK_ALREADY_EXISTS);
            }
        }
        return either;
    }

    @Override
    public Either<RuntimeException, Integer> update(Libro libro) {
        Either<RuntimeException, Integer> either;
        int rowsAffected = 0;
        try {
            for (Libro libro1 : StaticLists.libros) {
                if (libro1.getUuid() == libro.getUuid()) {
                    libro1.setAutor(libro.getAutor());
                    libro1.setName(libro.getName());
                    libro1.setGeneros(libro.getGeneros());
                    rowsAffected = 1;
                }
            }
            either = Either.right(rowsAffected);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.ERROR_EDITING_BOOK_INFO);
        }
        return either;
    }

    @Override
    public Either<RuntimeException, Integer> delete(Libro libro) {
        Either<RuntimeException, Integer> either = null;
        int rowsAffected;
        for (Libro libro1 : StaticLists.libros) {
            if (libro1.getUuid() == libro.getUuid()) {
                StaticLists.libros.remove(libro1);
                rowsAffected=1;
                either=Either.right(rowsAffected);
            } else {
                throw new GeneralErrorException(ConstantesErrores.ERROR_DELETING_BOOK);
            }
        }
        return either;
    }

}