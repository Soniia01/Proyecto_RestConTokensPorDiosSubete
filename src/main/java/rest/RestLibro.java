package rest;

import common.Constantes;
import domain.model.Autor;
import domain.model.Libro;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import useCases.libro.*;

import java.util.List;

@Path(Constantes.LIBROS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@DeclareRoles({"Admin", "User"})
public class RestLibro {
    private final GetLibroByNameUseCase getLibroByNameUseCase;
    private final GetLibroByAutorUseCase getLibroByAutorUseCase;
    private final GetAllLibrosUseCase getAllLibrosUseCase;
    private final AddLibroUseCase add;
    private final DeleteLibroUseCase delete;
    private final UpdateLibroUseCase update;

    @Inject
    public RestLibro(GetLibroByNameUseCase getLibroByNameUseCase, GetLibroByAutorUseCase getLibroByAutorUseCase, GetAllLibrosUseCase getAllLibrosUseCase, AddLibroUseCase add, DeleteLibroUseCase delete, UpdateLibroUseCase update) {
        this.getLibroByNameUseCase = getLibroByNameUseCase;
        this.getLibroByAutorUseCase = getLibroByAutorUseCase;
        this.getAllLibrosUseCase = getAllLibrosUseCase;
        this.add = add;
        this.delete = delete;
        this.update = update;
    }


    @GET
    @RolesAllowed({"Admin", "User"})
    public List<Libro> getAllLibros() {
        return getAllLibrosUseCase.getAll().getOrNull();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path(Constantes.TITULO)
    public Libro getLibro(@PathParam(Constantes.TITULO_Query) String titulo) {
        return getLibroByNameUseCase.get(titulo).getOrNull();
    }

    @GET
    @RolesAllowed({"Admin", "User"})
    @Path(Constantes.AUTOR_PATH)
    public List<Libro> getLibrosByAutor(@PathParam(Constantes.AUTOR_QUERY) String autorName) {
        return getLibroByAutorUseCase.getAllByAutor(autorName).getOrNull();
    }

    @PUT
    @RolesAllowed({"Admin"})
    public Libro updateLibro(@QueryParam(Constantes.TITULO_Query) String titulo, @QueryParam("libro") Libro libro) {
        Libro libro1 = getLibroByNameUseCase.get(titulo).getOrNull();
        libro1.setGeneros(libro.getGeneros());
        libro1.setName(libro.getName());
        libro1.setAutor(libro.getAutor());
        update.update(libro1);
        return libro;
    }

    @POST
    @RolesAllowed({"Admin", "User"})
    public Response addLibro(@QueryParam(Constantes.NUEVO_LIBRO) Libro nuevoLibro, @QueryParam(Constantes.AUTOR_QUERY) Autor autorCorrespondiente) {
        nuevoLibro.setAutor(autorCorrespondiente.getName());
        add.save(nuevoLibro, autorCorrespondiente);
        return Response.status(Response.Status.CREATED).entity(nuevoLibro).build();
    }

    @DELETE
    @RolesAllowed({"Admin"})
    public Response deleteLibros(@QueryParam(Constantes.LIBROS_QUERY) List<String> libros) {
        List<Libro> lista = getAllLibrosUseCase.getAll().getOrNull();
        for (String titulo : libros) {
            for (Libro libro : lista) {
                if (titulo.equalsIgnoreCase(libro.getName())) {
                    delete.delete(libro);
                }
            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}