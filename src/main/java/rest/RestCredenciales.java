package rest;

import common.Constantes;
import common.StaticLists;
import domain.model.Credenciales;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import rest.errores.ApiError;
import useCases.credenciales.LoginUseCase;
import useCases.credenciales.MandarMail;
import useCases.credenciales.RegisterUseCase;

import java.time.LocalDateTime;

@Log4j2
@Path(Constantes.LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestCredenciales {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final MandarMail mandarMail;

    @Inject
    public RestCredenciales(LoginUseCase loginUseCase, RegisterUseCase registerUseCase, MandarMail mandarMail) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.mandarMail = mandarMail;
    }

    @GET
    public Response doLogin(@QueryParam(Constantes.USERNAME) String username, @QueryParam(Constantes.PASSWORD) String password) {
        Credenciales cred = StaticLists.credenciales.stream().filter(credencial1 -> credencial1.getUsername().equals(username)).findFirst().orElse(null);
        if ((loginUseCase.userLogged(cred))) {
            if (cred.isAutentificado()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ApiError(Constantes.NECESITA_VERIFICAR_SU_EMAIL_ANTES_DE_HACER_LOGIN, LocalDateTime.now())).
                        build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ApiError(Constantes.EL_USUARIO_O_LA_CONTRASEÑA_SON_INCORRECTOS, LocalDateTime.now())).
                    build();
        }
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
