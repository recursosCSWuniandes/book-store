package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.providers.StatusCreated;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorService {

    @Inject
    private IAuthorLogic bookLogic;

    @POST
    @StatusCreated
    public AuthorDTO createAuthor(AuthorDTO dto) {
        return bookLogic.createAuthor(dto);
    }

    @GET
    public List<AuthorDTO> getAuthors() {
        return bookLogic.getAuthors();
    }

    @GET
    @Path("{authorId: \\d+}")
    public AuthorDTO getAuthor(@PathParam("authorId") Long id) {
        return bookLogic.getAuthor(id);
    }

    @PUT
    @Path("{authorId: \\d+}")
    public AuthorDTO updateAuthor(@PathParam("authorId") Long id, AuthorDTO dto) {
        dto.setId(id);
        return bookLogic.updateAuthor(dto);
    }

    @DELETE
    @Path("{authorId: \\d+}")
    public void deleteAuthor(@PathParam("authorId") Long id) {
        bookLogic.deleteAuthor(id);
    }
}
