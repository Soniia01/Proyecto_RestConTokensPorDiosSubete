package useCases.credenciales;

import dao.CredentialDao;
import domain.model.Credenciales;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import rest.errores.ApiError;

public class GetCredencialesCodigoUseCase {

    private final CredentialDao credentialDao;

    @Inject
    public GetCredencialesCodigoUseCase(CredentialDao credentialDao) {
        this.credentialDao = credentialDao;
    }

    public Either<ApiError, Credenciales> getCredencialesCodigo(String codigAuth) {
        return credentialDao.getCredencialesCodigo(codigAuth);
    }
}
