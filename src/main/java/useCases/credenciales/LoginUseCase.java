package useCases.credenciales;

import dao.CredentialDao;
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
    public boolean userLogged(Credenciales credenciales){
        Credenciales cred = credentialDao.userLogged(credenciales).get();
        boolean logged = false;
        if (cred != null) {
            passwordHash.verify(credenciales.getPassword().toCharArray(), cred.getPassword());
            logged=true;
        }
        return logged;
    }

}
