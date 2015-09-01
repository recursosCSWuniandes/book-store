package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class BookLogic implements IBookLogic {

    @Inject private BookPersistence persistence;

    /**
     * @generated
     */
    public int countBooks() {
        return persistence.count();
    }

    /**
     * @generated
     */
    public List<BookDTO> getBooks(Integer page, Integer maxRecords) {
        return BookConverter.listEntity2DTO(persistence.findAll(page, maxRecords));
    }

    /**
     * @generated
     */
    public BookDTO getBook(Long id) {
        return BookConverter.fullEntity2DTO(persistence.find(id));
    }

    /**
     * @generated
     */
    public BookDTO createBook(BookDTO dto) {
        BookEntity entity = BookConverter.fullDTO2Entity(dto);
        persistence.create(entity);
        return BookConverter.fullEntity2DTO(entity);
    }

    /**
     * @generated
     */
    public BookDTO updateBook(BookDTO dto) {
        BookEntity entity = persistence.update(BookConverter.fullDTO2Entity(dto));
        return BookConverter.fullEntity2DTO(entity);
    }

    /**
     * @generated
     */
    public void deleteBook(Long id) {
        persistence.delete(id);
    }

    /**
     * @generated
     */
    public List<BookDTO> findByName(String name) {
        return BookConverter.listEntity2DTO(persistence.findByName(name));
    }
}
