package useCases.libro;

import dao.LibroDao;
import domain.model.Libro;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetAllLibrosUseCase {
    public final LibroDao libroDao;

    @Inject
    public GetAllLibrosUseCase(LibroDao libroDao) {
        this.libroDao = libroDao;
    }
    public Either<RuntimeException, List<Libro>> getAll(){
        return libroDao.getAll();
    }
}
