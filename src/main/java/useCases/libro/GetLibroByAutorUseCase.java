package useCases.libro;

import dao.LibroDao;
import domain.model.Libro;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetLibroByAutorUseCase {
    public final LibroDao libroDao;

    @Inject
    public GetLibroByAutorUseCase(LibroDao libroDao) {
        this.libroDao = libroDao;
    }
    public Either<RuntimeException, List<Libro>> getAllByAutor(String author){
        return libroDao.getAllByAutor(author);
    }
}
