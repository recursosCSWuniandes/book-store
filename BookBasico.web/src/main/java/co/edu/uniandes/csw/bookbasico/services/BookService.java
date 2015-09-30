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

    /**
     * Se encarga de crear un book en la base de datos
     * @param dto Objeto de BookDTO con los datos nuevos
     * @return Objeto de BookDTO con los datos nuevos y su ID.
     */
    @POST
    @StatusCreated
    public BookDTO createBook(BookDTO dto) {
        return bookLogic.createBook(dto);
    }

    /**
     * Obtiene la lista de los registros de Book
     * @return Colección de objetos de BookDTO
     */
    @GET
    public List<BookDTO> getBooks() {
        return bookLogic.getBooks();
    }

    /**
     * Obtiene los datos de una instancia de Book a partir de su ID
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BookDTO con los datos del Book consultado
     */
    @GET
    @Path("{id: \\d+}")
    public BookDTO getBook(@PathParam("id") Long id) {
        return bookLogic.getBook(id);
    }

    /**
     * Actualiza la información de una instancia de Book
     * @param id Identificador de la instancia de Book a modificar
     * @param dto Instancia de BookDTO con los nuevos datos.
     * @return Instancia de BookDTO con los datos actualizados.
     */
    @PUT
    @Path("{id: \\d+}")
    public BookDTO updateBook(@PathParam("id") Long id, BookDTO dto) {
        dto.setId(id);
        return bookLogic.updateBook(dto);
    }

    /**
     * Elimina una instancia de Book de la base de datos.
     * @param id Identificador de la instancia a eliminar.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteBook(@PathParam("id") Long id) {
        bookLogic.deleteBook(id);
    }

    /**
     * Asocia un Author existente con un Book
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @return Instancia de AuthorDTO que fue asociada a Book
     */
    @POST
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public AuthorDTO addAuthor(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        return bookLogic.addAuthor(authorId, bookId);
    }

    /**
     * Desasocia un Author existente de un Book existente
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     */
    @DELETE
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public void deleteAuthor(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        bookLogic.removeAuthor(authorId, bookId);
    }

    /**
     * Remplaza las instancias de Author asociadas a una instancia de Book
     * @param bookId Identificador de la instancia de Book
     * @param authors Colección de instancias de AuthorDTO a asociar a instancia de Book
     * @return Nueva colección de AuthorDTO asociada a la instancia de Book
     */
    @PUT
    @Path("{bookId: \\d+}/authors")
    public List<AuthorDTO> replaceAuthors(@PathParam("bookId") Long bookId, List<AuthorDTO> authors) {
        return bookLogic.replaceAuthors(authors, bookId);
    }

    /**
     * Obtiene una colección de instancias de AuthorDTO asociadas a una instancia de Book
     * @param bookId Identificador de la instancia de Book
     * @return Colección de instancias de AuthorDTO asociadas a la instancia de Book
     */
    @GET
    @Path("{bookId: \\d+}/authors")
    public List<AuthorDTO> getAuthors(@PathParam("bookId") Long bookId) {
        return bookLogic.getAuthors(bookId);
    }

    /**
     * Obtiene una instancia de Author asociada a una instancia de Book
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     */
    @GET
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public void getAuthor(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        bookLogic.getAuthor(bookId, authorId);
    }
}
