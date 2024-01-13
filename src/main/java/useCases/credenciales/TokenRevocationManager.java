package useCases.credenciales;

import java.util.HashSet;
import java.util.Set;

public class TokenRevocationManager {

    private static Set<String> revokedTokens = new HashSet<>();

    public static void revokeToken(String token) {
        revokedTokens.add(token);
    }

    public static boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
}
