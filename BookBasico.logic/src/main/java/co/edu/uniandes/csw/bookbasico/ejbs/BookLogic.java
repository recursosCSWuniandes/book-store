package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BookLogic implements IBookLogic {

    @Inject private BookPersistence persistence;

    public List<BookDTO> getBooks() {
        return BookConverter.listEntity2DTO(persistence.findAll());
    }

    public BookDTO getBook(Long id) {
        return BookConverter.fullEntity2DTO(persistence.find(id));
    }

    public BookDTO createBook(BookDTO dto) {
        BookEntity entity = BookConverter.fullDTO2Entity(dto);
        persistence.create(entity);
        return BookConverter.fullEntity2DTO(entity);
    }

    public BookDTO updateBook(BookDTO dto) {
        BookEntity entity = persistence.update(BookConverter.fullDTO2Entity(dto));
        return BookConverter.fullEntity2DTO(entity);
    }

    public void deleteBook(Long id) {
        persistence.delete(id);
    }
}
