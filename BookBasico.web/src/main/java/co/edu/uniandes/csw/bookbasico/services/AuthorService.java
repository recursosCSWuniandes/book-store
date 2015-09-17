package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
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
    private IAuthorLogic authorLogic;

    @POST
    @StatusCreated
    public AuthorDTO createAuthor(AuthorDTO dto) {
        return authorLogic.createAuthor(dto);
    }

    @GET
    public List<AuthorDTO> getAuthors() {
        return authorLogic.getAuthors();
    }

    @GET
    @Path("{authorId: \\d+}")
    public AuthorDTO getAuthor(@PathParam("authorId") Long id) {
        return authorLogic.getAuthor(id);
    }

    @PUT
    @Path("{authorId: \\d+}")
    public AuthorDTO updateAuthor(@PathParam("authorId") Long id, AuthorDTO dto) {
        dto.setId(id);
        return authorLogic.updateAuthor(dto);
    }

    @DELETE
    @Path("{authorId: \\d+}")
    public void deleteAuthor(@PathParam("authorId") Long id) {
        authorLogic.deleteAuthor(id);
    }

    @POST
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookDTO addBook(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return authorLogic.addBook(bookId, authorId);
    }

    @DELETE
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookDTO deleteBook(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return authorLogic.removeBook(bookId, authorId);
    }

    @PUT
    @Path("{authorId: \\d+}/books")
    public void replaceBooks(@PathParam("authorId") Long authorId, List<BookDTO> books) {
        authorLogic.replaceBooks(books, authorId);
    }

    @GET
    @Path("{authorId: \\d+}/books")
    public List<BookDTO> getBooks(@PathParam("authorId") Long authorId) {
        return authorLogic.getBooks(authorId);
    }

    @GET
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public void replaceBooks(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        authorLogic.getBook(authorId, bookId);
    }
}
