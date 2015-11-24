package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.converters.AuthorConverter;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Path("/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorService {

    @Inject
    private IAuthorLogic authorLogic;

    /**
     * Crea un registro de Author y retorna la instancia de este registro.
     * @param entity Instancia de AuthorEntity con los datos a guardar
     * @return Instancia de AuthorDTO con los nuevos datos y el ID.
     */
    @POST
    @StatusCreated
    public AuthorDTO createAuthor(AuthorDTO dto) {
        AuthorEntity authorEntity = authorLogic.createAuthor(AuthorConverter.basicDTO2Entity(dto));
        return AuthorConverter.basicEntity2DTO(authorEntity);
    }

    /**
     * Obtiene una colección de instancias de AuthorDTO existentes.
     * @return Colección de instancias de AuthorDTO.
     */
    @GET
    public List<AuthorDTO> getAuthors() {
        return AuthorConverter.listEntity2DTO(authorLogic.getAuthors());
    }

    /**
     * Obtiene una instancia de AuthorDTO a partir de su identificador.
     * @param id Identificador de la instancia de Author
     * @return Instancia de AuthorDTO asociada al id provisto.
     */
    @GET
    @Path("{authorId: \\d+}")
    public AuthorDTO getAuthor(@PathParam("authorId") Long id) {
        return AuthorConverter.basicEntity2DTO(authorLogic.getAuthor(id));
    }

    /**
     * Actualiza los datos de un registro de Author
     * @param id Identificador del registro de Author a actualizar
     * @param entity Instancia de AuthorEntity con los datos nuevos.
     * @return Instancia de AuthorDTO con los datos nuevos.
     */
    @PUT
    @Path("{authorId: \\d+}")
    public AuthorDTO updateAuthor(@PathParam("authorId") Long id, AuthorDTO dto) {
        dto.setId(id);
        AuthorEntity authorEntity = authorLogic.updateAuthor(AuthorConverter.basicDTO2Entity(dto));
        return AuthorConverter.basicEntity2DTO(authorEntity);
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
        try {
            return BookConverter.basicEntity2DTO( authorLogic.addBook(bookId, authorId) );
        } catch (BusinessLogicException ex) {
            Logger.getLogger(AuthorService.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(ex, 409);
        }
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
        try {
            return BookConverter.listEntity2DTO( authorLogic.replaceBooks(BookConverter.listDTO2Entity(books), authorId) );
        } catch (BusinessLogicException ex) {
            Logger.getLogger(AuthorService.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(ex, 409);
        }
    }

    /**
     * Obtiene la colección de instancias de BookDTO asociadas a una instancia de Author
     * @param authorId Identificador de la instancia de Author
     * @return Colección de instancias de BookDTO asociadas a instancia de Author
     */
    @GET
    @Path("{authorId: \\d+}/books")
    public List<BookDTO> getBooks(@PathParam("authorId") Long authorId) {
        return BookConverter.listEntity2DTO(authorLogic.getBooks(authorId));
    }

    /**
     * Obtiene una instancia de BookDTO asociada a una instancia de Author
     * @param authorId Identificador de la instancia de Author
     * @param bookId Identificador de la instancia de Book
     */
    @GET
    @Path("{authorId: \\d+}/books/{bookId: \\d+}")
    public BookDTO getBook(@PathParam("authorId") Long authorId, @PathParam("bookId") Long bookId) {
        return BookConverter.basicEntity2DTO(authorLogic.getBook(authorId, bookId));
    }
}
