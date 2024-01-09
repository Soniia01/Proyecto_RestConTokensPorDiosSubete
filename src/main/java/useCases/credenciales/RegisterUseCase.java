package useCases.credenciales;

import dao.CredentialDao;
import domain.model.Credenciales;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.extern.log4j.Log4j2;
import rest.errores.ApiError;
import useCases.utils.RandomCodeGenerator;
@Log4j2
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
    public void mandarMail(String mail){
        if (mail == null || mail.isEmpty()) {
            log.error("Falta email");
        }else {
            MandarMail mandarMail = new MandarMail();
            try {
                RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();
                String randomCode = randomCodeGenerator.randomCode();
                Credenciales cred=credentialDao.getCredenciales(mail).getOrNull();
                cred.setCodigoAuth(randomCode);
                mandarMail.generateAndSendEmail(mail, "<html><body><a href=\"http://localhost:8080/LibrosSoniaSanchez-1.0-SNAPSHOT/Activacion?codigo=" + randomCode + "\">Activación</a></body></html>", "Activación de la cuenta");
            } catch (MessagingException e) {
               log.info("Mensaje enviado");
            }
        }
    }
}
