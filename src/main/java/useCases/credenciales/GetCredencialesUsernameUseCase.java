package useCases.credenciales;

import dao.CredentialDao;
import domain.model.Credenciales;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import rest.errores.ApiError;

public class GetCredencialesUsernameUseCase {
    private final CredentialDao credentialDao;

    @Inject
    public GetCredencialesUsernameUseCase(CredentialDao credentialDao) {
        this.credentialDao = credentialDao;
    }

    public Either<ApiError, Credenciales> getCredencialesUsername(String username){
        return credentialDao.getCredencialesUsername(username);
    }
}
