package useCases.libro;

import dao.LibroDao;
import domain.model.Libro;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class GetLibroByNameUseCase {
    public final LibroDao libroDao;

    @Inject
    public GetLibroByNameUseCase(LibroDao libroDao) {
        this.libroDao = libroDao;
    }
    public Either<RuntimeException, Libro> get(String titulo){
        return libroDao.get(titulo);
    }
}
