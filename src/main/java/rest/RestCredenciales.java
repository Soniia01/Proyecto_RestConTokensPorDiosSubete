package rest;

import common.Constantes;
import domain.GeneralErrorException;
import domain.model.Credenciales;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import rest.errores.ApiError;
import useCases.credenciales.LoginUseCase;
import useCases.credenciales.RegisterUseCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

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
        Credenciales credenciales = new Credenciales(username, password);
        if ((loginUseCase.userLogged(credenciales))) {
            if (credenciales.isAutentificado()) {
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
            mandarCorreo(email);
            return Response.status(Response.Status.CREATED).entity(cred).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiError(Constantes.ERROR_EN_EL_REGISTRO, LocalDateTime.now())).build();
        }
    }

    private void mandarCorreo(String mail) {
        try {
            HttpURLConnection connection = getHttpURLConnection(mail);

            // Obtener la respuesta del servidor
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Manejar la respuesta del servidor
                System.out.println("Respuesta del servidor: " + response);
            } else {
                System.out.println("Error en la solicitud: " + responseCode);
            }

            // Cerrar la conexión
            connection.disconnect();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new GeneralErrorException("Error al mandar correo");
        }
    }

    private static HttpURLConnection getHttpURLConnection(String mail) throws IOException {
        String servletUrl = "http://localhost:8080/LibrosSoniaSanchez-1.0-SNAPSHOT/Mail";
        String emailParam = "email=" + mail;
        // Crear la URL completa con el parámetro
        String fullUrl = servletUrl + "?" + emailParam;

        // Crear la conexión HTTP
        URL url = new URL(fullUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Configurar el método de solicitud (GET)
        connection.setRequestMethod("GET");
        return connection;
    }
}
