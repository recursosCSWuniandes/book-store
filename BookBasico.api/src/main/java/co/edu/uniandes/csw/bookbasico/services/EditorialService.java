package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.api.IEditorialLogic;
import co.edu.uniandes.csw.bookbasico.converters.AuthorConverter;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.converters.EditorialConverter;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.auth.provider.StatusCreated;
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

@Path("/editorials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EditorialService {

    @Inject
    private IEditorialLogic editorialLogic;

    @Inject
    private IAuthorLogic authorLogic;

    /**
     * Crea un registro de Editorial
     *
     * @param dto Instancia de EditorialDTO con los datos del nuevo registro
     * @return Instancia de EditorialDTO con los datos nuevos y su ID.
     */
    @POST
    @StatusCreated
    public EditorialDTO createEditorial(EditorialDTO dto) {
        return EditorialConverter.basicEntity2DTO(editorialLogic.createEditorial(EditorialConverter.basicDTO2Entity(dto)));
    }

    /**
     * Obtiene la colección de registros de Editorial
     *
     * @return Colección de instancias de EditorialDTO
     */
    @GET
    public List<EditorialDTO> getEditorials() {
        return EditorialConverter.listEntity2DTO(editorialLogic.getEditorials());
    }

    /**
     * Obtiene una instancia de Editorial a partir de su identificador
     *
     * @param id Identificador de la instancia de Editorial
     * @return Instancia de EditorialDTO
     */
    @GET
    @Path("{id: \\d+}")
    public EditorialDTO getEditorial(@PathParam("id") Long id) {
        return EditorialConverter.basicEntity2DTO(editorialLogic.getEditorial(id));
    }

    /**
     * Actualiza un registro de Editorial
     *
     * @param id Identificador de la instancia de Editorial a actualizar
     * @param dto Instancia de EditorialDTO con los nuevos datos
     * @return Instancia de EditorialDTO con los nuevos datos
     */
    @PUT
    @Path("{id: \\d+}")
    public EditorialDTO updateEditorial(@PathParam("id") Long id, EditorialDTO dto) {
        dto.setId(id);
        return EditorialConverter.basicEntity2DTO(editorialLogic.updateEditorial(EditorialConverter.basicDTO2Entity(dto)));
    }

    /**
     * Elimina un registro de Editorial
     *
     * @param id Identificador del registro de Editorial
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEditorial(@PathParam("id") Long id) {
        editorialLogic.deleteEditorial(id);
    }

    /**
     * Asocia una instancia existente de Book a una instancia existente de
     * Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param bookId Identificador de la instancia de Book
     * @return Instancia de BookDTO asociada a la instancia de Editorial
     */
    @POST
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public BookDTO addBook(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        return BookConverter.basicEntity2DTO(editorialLogic.addBook(bookId, editorialId));
    }

    /**
     * Desasocia una instancia de Book de una instancia de Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param bookId Identificador de la instancia de Book
     */
    @DELETE
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public void deleteBook(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        editorialLogic.removeBook(bookId, editorialId);
    }

    /**
     * Remplaza la colección de instancias de Book asociada a una instancia de
     * Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param books Colección de instancias de BookDTO para asociar a la
     * instancia de Editorial
     * @return Colección de instancias de BookDTO asociada a la instancia de
     * Editorial
     */
    @PUT
    @Path("{editorialId: \\d+}/books")
    public List<BookDTO> replaceBooks(@PathParam("editorialId") Long editorialId, List<BookDTO> books) {
        return BookConverter.listEntity2DTO(editorialLogic.replaceBooks(BookConverter.listDTO2Entity(books), editorialId));
    }

    /**
     * Obtiene la colección de Book asociada a una instancia de Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @return Colección de instancias de BookDTO asociadas a la instancia de
     * Editorial
     */
    @GET
    @Path("{editorialId: \\d+}/books")
    public List<BookDTO> getBooks(@PathParam("editorialId") Long editorialId) {
        return BookConverter.listEntity2DTO( editorialLogic.getBooks(editorialId) );
    }

    /**
     * Obtiene una instancia de Book asociada a una instancia de Editorial
     *
     * @param editorialId Identificador de la instancia de Editorial
     * @param bookId Identificador de la instancia de Book
     * @return Instancia de BookDTO asociada a la instancia de Editorial
     */
    @GET
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public BookDTO getBook(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        return BookConverter.basicEntity2DTO(editorialLogic.getBook(editorialId, bookId));
    }

    /**
     * Obtiene la colección de instancias de Autores que tienen al menos una
     * instancia de Book asociada a una Editorial
     *
     * @param editorialId Identificador de la editorial
     * @return Colección de instancias de AuthorDTO que tienen al menos un Book
     * asociado a la Editorial
     */
    @GET
    @Path("{editorialId: \\d+}/authors")
    public List<AuthorDTO> getAuthors(@PathParam("editorialId") Long editorialId) {
        return AuthorConverter.listEntity2DTO(authorLogic.findByEditorial(editorialId));
    }
}
