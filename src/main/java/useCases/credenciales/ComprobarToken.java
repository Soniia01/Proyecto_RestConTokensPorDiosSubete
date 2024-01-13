package useCases.credenciales;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Log4j2
@Provider
public class ComprobarToken implements HttpAuthenticationMechanism {

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {
        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;
        if (!httpMessageContext.isProtected())
            return httpMessageContext.doNothing();

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(" ");

            if (valores[0].equalsIgnoreCase("Bearer")) {
                String accessToken = valores[1];

                c = validarTokenDeAcceso(accessToken);

                if (c.getStatus() == CredentialValidationResult.Status.VALID) {
                    httpServletRequest.getSession().setAttribute("USERLOGIN", c);
                }
            }
        }
        if (!c.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            httpServletRequest.setAttribute("status", c.getStatus());
            return httpMessageContext.doNothing();
        }
        return httpMessageContext.notifyContainerAboutLogin(c);
    }

    private CredentialValidationResult validarTokenDeAcceso(String accessToken) {
        try {
            JWTClaimsSet claimsSet = JWTParser.parse(accessToken).getJWTClaimsSet();

            String username = claimsSet.getSubject();

            String roles = claimsSet.getStringClaim("rol");

            if (TokenRevocationManager.isTokenRevoked(accessToken)) {
                log.warn("El token de acceso ha sido revocado. Se va a generar otro token.");
                return CredentialValidationResult.INVALID_RESULT;
            }
            if (roles == null || roles.isEmpty()) {
                log.warn("El claim de roles en el token es nulo o vacío. Se va a generar otro token.");
                return CredentialValidationResult.INVALID_RESULT;
            }

            Date expirationDate = claimsSet.getExpirationTime();

            if (expirationDate == null || LocalDateTime.now().isAfter(expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                log.warn("El token de acceso ha expirado o la fecha de expiración es nula. Se va a generar otro token.");
                return CredentialValidationResult.INVALID_RESULT;
            }

            return new CredentialValidationResult(username, Collections.singleton(roles));
        } catch (ParseException e) {
            log.error("Error al verificar el token JWT: " + e.getMessage(), e);
            return CredentialValidationResult.INVALID_RESULT;
        }
    }

    @Override
    public AuthenticationStatus secureResponse(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        return HttpAuthenticationMechanism.super.secureResponse(request, response, httpMessageContext);
    }

}
