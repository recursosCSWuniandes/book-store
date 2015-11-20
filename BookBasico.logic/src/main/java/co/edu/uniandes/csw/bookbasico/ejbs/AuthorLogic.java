package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.converters.AuthorConverter;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookbasico.persistence.AuthorPersistence;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthorLogic implements IAuthorLogic {

    @Inject
    private AuthorPersistence persistence;

    @Inject
    IBookLogic bookLogic;

    @Inject
    private BookPersistence bookPersistence;

    @Override
    public List<AuthorDTO> getAuthors() {
        return AuthorConverter.listEntity2DTO(persistence.findAll());
    }

    @Override
    public AuthorDTO getAuthor(Long id) {
        return AuthorConverter.basicEntity2DTO(persistence.find(id));
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        AuthorEntity entity = AuthorConverter.basicDTO2Entity(dto);
        persistence.create(entity);
        return AuthorConverter.basicEntity2DTO(entity);
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO dto) {
        AuthorEntity entity = persistence.update(AuthorConverter.basicDTO2Entity(dto));
        return AuthorConverter.basicEntity2DTO(entity);
    }

    @Override
    public void deleteAuthor(Long id) {
        persistence.delete(id);
    }

    @Override
    public BookDTO addBook(Long bookId, Long authorId) throws BusinessLogicException {
        bookLogic.addAuthor(authorId, bookId);
        BookEntity book = bookPersistence.find(bookId);
        return BookConverter.basicEntity2DTO(book);
    }

    @Override
    public void removeBook(Long bookId, Long authorId) {
        bookLogic.removeAuthor(authorId, bookId);
    }

    @Override
    public List<BookDTO> replaceBooks(List<BookDTO> books, Long authorId) throws BusinessLogicException {
        List<BookEntity> bookList = bookPersistence.findAll();
        List<BookEntity> newBookList = BookConverter.listDTO2Entity(books);
        AuthorEntity author = persistence.find(authorId);
        for (BookEntity book : bookList) {
            if (newBookList.contains(book)) {
                if (!book.getAuthors().contains(author)) {
                    bookLogic.addAuthor(authorId, book.getId());
                }
            } else {
                bookLogic.removeAuthor(authorId, book.getId());
            }
        }
        author.setBooks(newBookList);
        return BookConverter.listEntity2DTO(author.getBooks());
    }

    @Override
    public List<BookDTO> getBooks(Long authorId) {
        return BookConverter.listEntity2DTO(persistence.find(authorId).getBooks());
    }

    @Override
    public BookDTO getBook(Long authorId, Long bookId) {
        List<BookEntity> books = persistence.find(authorId).getBooks();
        BookEntity book = new BookEntity();
        book.setId(bookId);
        int index = books.indexOf(book);
        if (index >= 0) {
            return BookConverter.basicEntity2DTO(books.get(index));
        }
        return null;
    }
    
    @Override
    public List<AuthorDTO> findByEditorial(Long editorialId){
        return AuthorConverter.listEntity2DTO(persistence.findByEditorial(editorialId));
    }
}
