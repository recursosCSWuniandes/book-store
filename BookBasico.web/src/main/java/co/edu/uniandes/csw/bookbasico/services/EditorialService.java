package co.edu.uniandes.csw.bookbasico.services;

import co.edu.uniandes.csw.bookbasico.api.IEditorialLogic;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
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

@Path("/editorials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EditorialService {

    @Inject
    private IEditorialLogic editorialLogic;

    @POST
    @StatusCreated
    public EditorialDTO createEditorial(EditorialDTO dto) {
        return editorialLogic.createEditorial(dto);
    }

    @GET
    public List<EditorialDTO> getEditorials() {
        return editorialLogic.getEditorials();
    }

    @GET
    @Path("{id: \\d+}")
    public EditorialDTO getEditorial(@PathParam("id") Long id) {
        return editorialLogic.getEditorial(id);
    }

    @PUT
    @Path("{id: \\d+}")
    public EditorialDTO updateEditorial(@PathParam("id") Long id, EditorialDTO dto) {
        dto.setId(id);
        return editorialLogic.updateEditorial(dto);
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteEditorial(@PathParam("id") Long id) {
        editorialLogic.deleteEditorial(id);
    }

    @POST
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public BookDTO addBook(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        return editorialLogic.addBook(bookId, editorialId);
    }

    @DELETE
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public void deleteBook(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        editorialLogic.removeBook(bookId, editorialId);
    }

    @PUT
    @Path("{editorialId: \\d+}/books")
    public List<BookDTO> replaceBooks(@PathParam("editorialId") Long editorialId, List<BookDTO> books) {
        return editorialLogic.replaceBooks(books, editorialId);
    }

    @GET
    @Path("{editorialId: \\d+}/books")
    public List<BookDTO> getBooks(@PathParam("editorialId") Long editorialId) {
        return editorialLogic.getBooks(editorialId);
    }

    @GET
    @Path("{editorialId: \\d+}/books/{bookId: \\d+}")
    public void getBook(@PathParam("editorialId") Long editorialId, @PathParam("bookId") Long bookId) {
        editorialLogic.getBook(editorialId, bookId);
    }
}
