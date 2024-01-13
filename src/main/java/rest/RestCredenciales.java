package rest;

import common.Constantes;
import domain.model.Credenciales;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import rest.errores.ApiError;
import useCases.credenciales.AuthResponse;
import useCases.credenciales.LoginUseCase;
import useCases.credenciales.RegisterUseCase;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Log4j2
@Path(Constantes.LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCredenciales {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    @Inject
    public RestCredenciales(LoginUseCase loginUseCase, RegisterUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @GET
    public Response doLogin(@QueryParam(Constantes.USERNAME) String username, @QueryParam(Constantes.PASSWORD) String password) {
        Credenciales cred = loginUseCase.userLogged(username, password);
        if (cred.isAutentificado()) {
            String jwtToken = generarTokenJWT(120, username, cred.getRol());
            String jwtRefreshToken = generarTokenJWT(1800, username, cred.getRol());
            AuthResponse auth = new AuthResponse(jwtToken,jwtRefreshToken);
            return Response.status(Response.Status.OK).entity(auth).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ApiError(Constantes.NECESITA_VERIFICAR_SU_EMAIL_ANTES_DE_HACER_LOGIN, LocalDateTime.now())).
                    build();
        }
    }

    private String generarTokenJWT(int expirationSeconds, String username, String rol) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update("clave".getBytes(StandardCharsets.UTF_8));
        final SecretKeySpec key2 = new SecretKeySpec(
                digest.digest(), 0, 64, "AES");
        SecretKey keyConfig = Keys.hmacShaKeyFor(key2.getEncoded());

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("self")
                .setExpiration(Date
                        .from(LocalDateTime.now().plusSeconds(expirationSeconds).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim("rol", rol)
                .signWith(keyConfig).compact();
    }

    @POST
    public Response Register(@QueryParam(Constantes.USERNAME) String username, @QueryParam(Constantes.PASSWORD) String password, @QueryParam(Constantes.EMAIL) String email) {
        Credenciales cred = new Credenciales(username, password, email);
        if (registerUseCase.registrar(cred).isRight()) {
            System.out.println(cred);
            registerUseCase.mandarMail(email);
            return Response.status(Response.Status.CREATED).entity(cred).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiError(Constantes.ERROR_EN_EL_REGISTRO, LocalDateTime.now())).build();
        }
    }

}
