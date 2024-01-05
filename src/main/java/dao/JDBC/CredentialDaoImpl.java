package dao.JDBC;

import common.ConstantesErrores;
import common.StaticLists;
import dao.CredentialDao;
import domain.GeneralErrorException;
import domain.model.Credenciales;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import rest.errores.ApiError;

@Log4j2
public class CredentialDaoImpl implements CredentialDao {
    @Override
    public Either<ApiError, Credenciales> userLogged(Credenciales credenciales) {
        Either<ApiError, Credenciales> either;
        try {
            Credenciales cred = StaticLists.credenciales.stream().filter(credencial1 -> credencial1.getUsername().equals(credenciales.getUsername())).findFirst().orElse(null);
            either = Either.right(cred);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.THERE_WAS_AN_ERROR_WHILE_LOGGING_ON);
        }
        return either;
    }

    @Override
    public Either<ApiError, Integer> addUser(Credenciales credenciales) {
        Either<ApiError, Integer> either;
        int either2 = 0;
        try {
            StaticLists.credenciales.add(credenciales);
            either2 = 1;
            either = Either.right(either2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.THERE_WAS_AN_ERROR_ADDING_NEW_USER);
        }
        return either;
    }

    @Override
    public Either<ApiError, Integer> editarCredenciales(Credenciales credenciales) {
        Either<ApiError, Integer> either;
        int rowsAffected=0;
        try {
            Credenciales cred = StaticLists.credenciales.stream().filter(credencial1 -> credencial1.getUuid().equals(credenciales.getUuid())).findFirst().orElse(null);
            if (cred != null) {
                cred.setEmail(credenciales.getEmail());
                cred.setUsername(credenciales.getUsername());
                cred.setPassword(credenciales.getPassword());
                cred.setAutentificado(credenciales.isAutentificado());
                rowsAffected=1;
            }

            either = Either.right(rowsAffected);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.THERE_WAS_AN_ERROR_FETCHING_USER);
        }
        return either;
    }

    @Override
    public Either<ApiError, Credenciales> getCredenciales(String email) {
        Either<ApiError, Credenciales> either;
        try {
            Credenciales cred = StaticLists.credenciales.stream().filter(credencial1 -> credencial1.getEmail().equals(email)).findFirst().orElse(null);
            either = Either.right(cred);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.THERE_WAS_AN_ERROR_FETCHING_USER);
        }
        return either;
    }
    @Override
    public Either<ApiError, Credenciales> getCredencialesCodigo(String codigAuth) {
        Either<ApiError, Credenciales> either;
        try {
            Credenciales cred = StaticLists.credenciales.stream().filter(credencial1 -> credencial1.getCodigoAuth().equals(codigAuth)).findFirst().orElse(null);
            either = Either.right(cred);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException(ConstantesErrores.THERE_WAS_AN_ERROR_FETCHING_USER);
        }
        return either;
    }
}
