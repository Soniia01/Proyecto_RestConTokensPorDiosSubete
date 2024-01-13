package dao;

import rest.errores.ApiError;
import domain.model.Credenciales;
import io.vavr.control.Either;

public interface CredentialDao {
    Either<ApiError, Credenciales> userLogged(Credenciales credenciales);

    Either<ApiError, Integer> addUser(Credenciales credenciales);

    Either<ApiError, Integer> editarCredenciales(Credenciales credenciales);

    Either<ApiError, Credenciales> getCredenciales(String email);

    Either<ApiError, Credenciales> getCredencialesCodigo(String codigAuth);

    Either<ApiError, Credenciales> getCredencialesUsername(String username);

}
