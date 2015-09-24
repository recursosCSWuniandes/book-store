package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.converters.AuthorConverter;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BookLogic implements IBookLogic {

    @Inject
    private BookPersistence persistence;

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
        BookEntity newEntity = BookConverter.fullDTO2Entity(dto);
        BookEntity oldEntity = persistence.find(dto.getId());
        newEntity.setAuthors(oldEntity.getAuthors());
        BookEntity entity = persistence.update(newEntity);
        return BookConverter.fullEntity2DTO(entity);
    }

    public void deleteBook(Long id) {
        persistence.delete(id);
    }

    public AuthorDTO addAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        bookEntity.getAuthors().add(authorEntity);
        return AuthorConverter.basicEntity2DTO(authorEntity);
    }

    public void removeAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        bookEntity.getAuthors().remove(author);
    }

    public List<AuthorDTO> replaceAuthors(List<AuthorDTO> authors, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        bookEntity.setAuthors(AuthorConverter.listDTO2Entity(authors));
        return AuthorConverter.listEntity2DTO(bookEntity.getAuthors());
    }

    public List<AuthorDTO> getAuthors(Long bookId) {
        return AuthorConverter.listEntity2DTO(persistence.find(bookId).getAuthors());
    }

    public AuthorDTO getAuthor(Long bookId, Long authorId) {
        List<AuthorEntity> authors = persistence.find(bookId).getAuthors();
        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        int index = authors.indexOf(author);
        if (index >= 0) {
            return AuthorConverter.basicEntity2DTO(authors.get(index));
        }
        return null;
    }
}
