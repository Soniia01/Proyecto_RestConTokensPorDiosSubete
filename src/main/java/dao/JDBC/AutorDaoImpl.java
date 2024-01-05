package dao.JDBC;

import common.ConstantesErrores;
import common.StaticLists;
import dao.AutorDao;
import domain.GeneralErrorException;
import domain.model.Autor;
import io.vavr.control.Either;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Data
@Log4j2
public class AutorDaoImpl implements AutorDao {

    @Override
    public Either<RuntimeException, List<Autor>> getAll() {
        Either<RuntimeException, List<Autor>> either;
        try {
            List<Autor> listaAutores = StaticLists.autores;
            either = Either.right(listaAutores);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.UNABLE_TO_GET_AUTHORS);
        }
        return either;
    }

    @Override
    public Either<RuntimeException, Autor> get(String nombre) {
        Either<RuntimeException, Autor> either;
        try {
            Autor autor = null;
            for (Autor autor1 : StaticLists.autores) {
                if (autor1.getName().equalsIgnoreCase(nombre)) {
                    autor = autor1;
                }
            }
            either = Either.right(autor);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.UNABLE_TO_FIND + nombre);
        }
        return either;
    }
}

