package useCases.credenciales;

import dao.CredentialDao;
import domain.model.Credenciales;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import rest.errores.ApiError;

public class EditarCredencialesUserCase {
    private final CredentialDao credentialDao;

    @Inject
    public EditarCredencialesUserCase(CredentialDao credentialDao) {
        this.credentialDao = credentialDao;
    }

    public Either<ApiError, Integer> editarCredenciales(Credenciales credenciales) {
        return credentialDao.editarCredenciales(credenciales);
    }
}
