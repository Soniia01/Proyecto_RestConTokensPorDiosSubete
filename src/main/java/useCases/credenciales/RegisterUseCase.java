package useCases.credenciales;

import dao.CredentialDao;
import domain.model.Credenciales;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import rest.errores.ApiError;

public class RegisterUseCase {
    private final CredentialDao credentialDao;
    private final Pbkdf2PasswordHash passwordHash;
    @Inject
    public RegisterUseCase(CredentialDao credentialDao, Pbkdf2PasswordHash passwordHash) {
        this.credentialDao = credentialDao;
        this.passwordHash = passwordHash;
    }
    public Either<ApiError, Integer> registrar(Credenciales credenciales){
        Either<ApiError, Integer> either;
        credenciales.setPassword(passwordHash.generate(credenciales.getPassword().toCharArray()));
        either=Either.right(credentialDao.addUser(credenciales).get());
        return either;
    }
}
