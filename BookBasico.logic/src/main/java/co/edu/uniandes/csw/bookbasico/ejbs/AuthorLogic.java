package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.converters.AuthorConverter;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.persistence.AuthorPersistence;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthorLogic implements IAuthorLogic{

    @Inject
    private AuthorPersistence persistence;
    
    @Inject
    private BookPersistence bookPersistence;

    public List<AuthorDTO> getAuthors() {
        return AuthorConverter.listEntity2DTO(persistence.findAll());
    }

    public AuthorDTO getAuthor(Long id) {
        return AuthorConverter.basicEntity2DTO(persistence.find(id));
    }

    public AuthorDTO createAuthor(AuthorDTO dto) {
        AuthorEntity entity = AuthorConverter.basicDTO2Entity(dto);
        persistence.create(entity);
        return AuthorConverter.basicEntity2DTO(entity);
    }

    public AuthorDTO updateAuthor(AuthorDTO dto) {
        AuthorEntity entity = persistence.update(AuthorConverter.basicDTO2Entity(dto));
        return AuthorConverter.basicEntity2DTO(entity);
    }

    public void deleteAuthor(Long id) {
        persistence.delete(id);
    }
    
    public BookDTO addBook(Long BookId, Long AuthorId){
        AuthorEntity AuthorEntity = persistence.find(AuthorId);
        BookEntity BookEntity = bookPersistence.find(BookId);
        AuthorEntity.getBooks().add(BookEntity);
        return BookConverter.basicEntity2DTO(BookEntity);
    }
    
    public BookDTO removeBook(Long BookId, Long AuthorId){
        AuthorEntity AuthorEntity = persistence.find(AuthorId);
        BookEntity Book = new BookEntity();
        Book.setId(BookId);
        AuthorEntity.getBooks().remove(Book);
        return null;
    }
    
    public void replaceBooks(List<BookDTO> Books, Long AuthorId){
        AuthorEntity AuthorEntity = persistence.find(AuthorId);
        AuthorEntity.setBooks(BookConverter.listDTO2Entity(Books));
    }
    
    public List<BookDTO> getBooks(Long AuthorId){
        return BookConverter.listEntity2DTO(persistence.find(AuthorId).getBooks());
    }
    
    public BookDTO getBook(Long AuthorId, Long BookId){
        List<BookEntity> Books = persistence.find(AuthorId).getBooks();
        BookEntity Book = new BookEntity();
        Book.setId(BookId);
        int index = Books.indexOf(Book);
        if (index >= 0) {
            return BookConverter.basicEntity2DTO(Books.get(index));
        }
        return null;
    }
}
