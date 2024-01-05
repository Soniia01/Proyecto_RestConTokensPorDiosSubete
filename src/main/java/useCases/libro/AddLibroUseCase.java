package useCases.libro;

import dao.LibroDao;
import domain.model.Autor;
import domain.model.Libro;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class AddLibroUseCase {
    public final LibroDao libroDao;

    @Inject
    public AddLibroUseCase(LibroDao libroDao) {
        this.libroDao = libroDao;
    }
    public Either<RuntimeException, Integer> save(Libro libro, Autor autor){
        return libroDao.save(libro,autor);
    }
}
