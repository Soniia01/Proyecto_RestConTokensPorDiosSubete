package useCases.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomCodeGenerator {

    public String randomCode() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Base64.getUrlEncoder().encodeToString(bits);
    }
}
