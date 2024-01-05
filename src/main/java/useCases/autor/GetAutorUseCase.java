package useCases.autor;

import dao.AutorDao;
import domain.model.Autor;
import io.vavr.control.Either;
import jakarta.inject.Inject;

public class GetAutorUseCase {
    private final AutorDao autorDao;

    @Inject
    public GetAutorUseCase(AutorDao autorDao) {
        this.autorDao = autorDao;
    }
    public Either<RuntimeException, Autor> get(String nombre){
        return autorDao.get(nombre);
    }
}
