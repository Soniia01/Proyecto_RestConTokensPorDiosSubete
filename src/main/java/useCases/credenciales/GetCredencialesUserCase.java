package useCases.credenciales;

import dao.CredentialDao;
import domain.model.Credenciales;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import rest.errores.ApiError;

public class GetCredencialesUserCase {
    private final CredentialDao credentialDao;

    @Inject
    public GetCredencialesUserCase(CredentialDao credentialDao) {
        this.credentialDao = credentialDao;
    }
    public Either<ApiError, Credenciales> getCredenciales(String email){
        return credentialDao.getCredenciales(email);
    }
}
