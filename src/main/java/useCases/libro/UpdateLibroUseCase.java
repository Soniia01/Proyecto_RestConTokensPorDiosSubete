package useCases.libro;

import dao.LibroDao;
import domain.model.Libro;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class UpdateLibroUseCase {
    public final LibroDao libroDao;

    @Inject
    public UpdateLibroUseCase(LibroDao libroDao) {
        this.libroDao = libroDao;
    }

    public Either<RuntimeException, Integer> update(Libro libro) {
        return libroDao.update(libro);
    }
}
