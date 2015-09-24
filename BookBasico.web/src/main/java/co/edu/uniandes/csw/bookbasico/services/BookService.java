package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
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

@Path("/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookService {

    @Inject
    private IBookLogic bookLogic;

    @POST
    @StatusCreated
    public BookDTO createBook(BookDTO dto) {
        return bookLogic.createBook(dto);
    }

    @GET
    public List<BookDTO> getBooks() {
        return bookLogic.getBooks();
    }

    @GET
    @Path("{id: \\d+}")
    public BookDTO getBook(@PathParam("id") Long id) {
        return bookLogic.getBook(id);
    }

    @PUT
    @Path("{id: \\d+}")
    public BookDTO updateBook(@PathParam("id") Long id, BookDTO dto) {
        dto.setId(id);
        return bookLogic.updateBook(dto);
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteBook(@PathParam("id") Long id) {
        bookLogic.deleteBook(id);
    }

    @POST
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public AuthorDTO addAuthor(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        return bookLogic.addAuthor(authorId, bookId);
    }

    @DELETE
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public void deleteAuthor(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        bookLogic.removeAuthor(authorId, bookId);
    }

    @PUT
    @Path("{bookId: \\d+}/authors")
    public List<AuthorDTO> replaceAuthors(@PathParam("bookId") Long bookId, List<AuthorDTO> authors) {
        return bookLogic.replaceAuthors(authors, bookId);
    }

    @GET
    @Path("{bookId: \\d+}/authors")
    public List<AuthorDTO> getAuthors(@PathParam("bookId") Long bookId) {
        return bookLogic.getAuthors(bookId);
    }

    @GET
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public void getAuthor(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        bookLogic.getAuthor(bookId, authorId);
    }
}
