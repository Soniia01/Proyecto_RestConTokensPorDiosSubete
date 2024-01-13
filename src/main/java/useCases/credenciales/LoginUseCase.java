package useCases.credenciales;

import dao.CredentialDao;
import domain.error.GeneralErrorException;
import domain.model.Credenciales;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

public class LoginUseCase {
    private final CredentialDao credentialDao;
    private final Pbkdf2PasswordHash passwordHash;
    @Inject
    public LoginUseCase(CredentialDao credentialDao, Pbkdf2PasswordHash passwordHash) {
        this.credentialDao = credentialDao;
        this.passwordHash = passwordHash;
    }
    public Credenciales userLogged(String username, String password){
        Credenciales cred = credentialDao.getCredencialesUsername(username).getOrNull();
        if (cred != null && passwordHash.verify(password.toCharArray(), cred.getPassword())) {
                return cred;
        }else {
            throw new GeneralErrorException("No se ha podido loggear");
        }
    }

}
