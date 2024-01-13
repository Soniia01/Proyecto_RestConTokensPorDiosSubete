package useCases.credenciales;

import lombok.Data;

@Data
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;
}
