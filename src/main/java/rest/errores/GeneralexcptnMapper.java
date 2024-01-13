package rest.errores;

import domain.error.GeneralErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.time.LocalDateTime;

public class GeneralexcptnMapper implements ExceptionMapper<GeneralErrorException> {
    @Override
    public Response toResponse(GeneralErrorException generalErrorException) {
        ApiError apiError = new ApiError(generalErrorException.getMessage(), LocalDateTime.now());
        return Response.status(Response.Status.UNAVAILABLE_FOR_LEGAL_REASONS).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
