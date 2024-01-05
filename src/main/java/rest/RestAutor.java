package rest;
import common.Constantes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import domain.model.Autor;
import useCases.autor.GetAllAutoresUseCase;
import useCases.autor.GetAutorUseCase;

import java.util.List;

@Path(Constantes.AUTOR)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestAutor {
    private final GetAutorUseCase getAutorUseCase;
    private final GetAllAutoresUseCase getAllAutoresUseCase;
    @Inject
    public RestAutor(GetAutorUseCase getAutorUseCase, GetAllAutoresUseCase getAllAutoresUseCase) {
        this.getAutorUseCase = getAutorUseCase;
        this.getAllAutoresUseCase = getAllAutoresUseCase;
    }

    @GET
    public List<Autor> getAllAutores() {
        return getAllAutoresUseCase.getAll().getOrNull();
    }

    @GET
    @Path(Constantes.NOMBRE)
    public Autor getAutor(@PathParam(Constantes.NOMBRE_QUERY) String nombre) {
        return getAutorUseCase.get(nombre).getOrNull();
    }
}
