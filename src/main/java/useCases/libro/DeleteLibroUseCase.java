package useCases.libro;

import dao.LibroDao;
import domain.model.Libro;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class DeleteLibroUseCase {
    public final LibroDao libroDao;

    @Inject
    public DeleteLibroUseCase(LibroDao libroDao) {
        this.libroDao = libroDao;
    }
    public Either<RuntimeException, Integer> delete(Libro libro){
        return libroDao.delete(libro);
    }
}
