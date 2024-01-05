package useCases.autor;


import dao.AutorDao;
import domain.model.Autor;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class GetAllAutoresUseCase {
    private final AutorDao autorDao;

    @Inject
    public GetAllAutoresUseCase(AutorDao autorDao) {
        this.autorDao = autorDao;
    }
    public Either<RuntimeException, List<Autor>> getAll() {
    return autorDao.getAll();
    }
}
