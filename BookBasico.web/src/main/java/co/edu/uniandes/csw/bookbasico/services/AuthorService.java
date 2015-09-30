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

    /**
     * Crea un registro de Author y retorna la instancia de este registro.
     * @param dto Instancia de AuthorDTO con los datos a guardar
     * @return Instancia de AuthorDTO con los nuevos datos y el ID.
     */
    @POST
    @StatusCreated
    public AuthorDTO createAuthor(AuthorDTO dto) {
        return authorLogic.createAuthor(dto);
    }

    /**
     * Obtiene una colección de instancias de AuthorDTO existentes.
     * @return Colección de instancias de AuthorDTO.
     */
    @GET
    public List<AuthorDTO> getAuthors() {
        return authorLogic.getAuthors();
    }

    /**
     * Obtiene una instancia de AuthorDTO a partir de su identificador.
     * @param id Identificador de la instancia de Author
     * @return Instancia de AuthorDTO asociada al id provisto.
     */
    @GET
    @Path("{authorId: \\d+}")
    public AuthorDTO getAuthor(@PathParam("authorId") Long id) {
        return authorLogic.getAuthor(id);
    }

    /**
     * Actualiza los datos de un registro de Author
     * @param id Identificador del registro de Author a actualizar
     * @param dto Instancia de AuthorDTO con los datos nuevos.
     * @return Instancia de AuthorDTO con los datos nuevos.
     */
    @PUT
    @Path("{authorId: \\d+}")
    public AuthorDTO updateAuthor(@PathParam("authorId") Long id, AuthorDTO dto) {
        dto.setId(id);
        return authorLogic.updateAuthor(dto);
    }

    /**
     * Elimina un registro de Author a partir de su identificador.
     * @param id Identificador de la instancia de Author
     */
    @DELETE
    @Path("{authorId: \\d+}")
    public void deleteAuthor(@PathParam("authorId") Long id) {
        authorLogic.deleteAuthor(id);
    }

    /**
     * Asocia una instancia de Book existente a una instancia de Author existente
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     * @return Instancia de Book asociada a la instancia de Author.
     */
    @POST
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookDTO addBook(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return authorLogic.addBook(bookId, authorId);
    }

    /**
     * Desasocia una instancia de Book existente de una instancia de Author
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     */
    @DELETE
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public void deleteBook(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        authorLogic.removeBook(bookId, authorId);
    }

    /**
     * Remplaza la colección de instancias de Book asociadas a una instancia de Author
     * @param authorId Identificador de la instancia de Author
     * @param books Colección de instancias de BookDTO a asociar con instancia de Author
     * @return Colección de instancias de BookDTO asociadas a instancia de Author
     */
    @PUT
    @Path("{authorId: \\d+}/books")
    public List<BookDTO> replaceBooks(@PathParam("authorId") Long authorId, List<BookDTO> books) {
        return authorLogic.replaceBooks(books, authorId);
    }

    /**
     * Obtiene la colección de instancias de BookDTO asociadas a una instancia de Author
     * @param authorId Identificador de la instancia de Author
     * @return Colección de instancias de BookDTO asociadas a instancia de Author
     */
    @GET
    @Path("{authorId: \\d+}/books")
    public List<BookDTO> getBooks(@PathParam("authorId") Long authorId) {
        return authorLogic.getBooks(authorId);
    }

    /**
     * Obtiene una instancia de BookDTO asociada a una instancia de Author
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     */
    @GET
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookDTO getBook(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return authorLogic.getBook(authorId, bookId);
    }
}
