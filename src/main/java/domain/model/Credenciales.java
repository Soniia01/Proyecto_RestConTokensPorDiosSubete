package domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credenciales {
    private String uuid;
    private String username;
    private String password;
    private String email;
    private boolean autentificado;
    private String codigoAuth;
    private String rol;

    public Credenciales(String username, String password, String email) {
        uuid = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Credenciales(String username, String password) {
        uuid = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
    }
}
