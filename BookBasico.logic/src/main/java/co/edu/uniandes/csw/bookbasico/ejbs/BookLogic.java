package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import co.edu.uniandes.csw.bookbasico.persistence.AuthorPersistence;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @generated
 */
@Stateless
public class BookLogic implements IBookLogic {

    @Inject private BookPersistence persistence;

    @Inject private AuthorPersistence authorPersistence;

    /**
     * @generated
     */
    @Override
    public List<BookEntity> getBooks() {
        return persistence.findAll();
    }

    /**
     * @generated
     */
    @Override
    public BookEntity getBook(Long id) {
        return persistence.find(id);
    }

    /**
     * @generated
     */
    @Override
    public BookEntity createBook(BookEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * @generated
     */
    @Override
    public BookEntity updateBook(BookEntity entity) {
        BookEntity newEntity = entity;
        BookEntity oldEntity = persistence.find(entity.getId());
        newEntity.setAuthors(oldEntity.getAuthors());
        newEntity.setAuthors(oldEntity.getAuthors());
        return persistence.update(newEntity);
    }

    /**
     * @generated
     */
    @Override
    public void deleteBook(Long id) {
        persistence.delete(id);
    }

    /**
     * @generated
     */
    @Override
    public AuthorEntity addAuthor(Long authorId, Long bookId) throws BusinessLogicException {
        BookEntity bookEntity  = persistence.find(bookId);
        AuthorEntity authorEntity = authorPersistence.find(authorId);
        bookEntity.getAuthors().add(authorEntity);
        return authorEntity;
    }
    
    /**
     * @generated
     */
    @Override
    public void removeAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity  = persistence.find(bookId);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        bookEntity.getAuthors().remove(authorEntity);
    }
    
    /**
     * @generated
     */
    @Override
    public List<AuthorEntity> replaceAuthors(List<AuthorEntity> authors, Long bookId) throws BusinessLogicException {
        BookEntity bookEntity  = persistence.find(bookId);
        List<AuthorEntity> authorList = authorPersistence.findAll();
        bookEntity.setAuthors(authors);
        return bookEntity.getAuthors();
    }

    /**
     * @generated
     */
    @Override
    public List<AuthorEntity> getAuthors(Long bookId) {
        return persistence.find(bookId).getAuthors();
    }

    /**
     * @generated
     */
    @Override
    public AuthorEntity getAuthor(Long bookId, Long authorId) {
        List<AuthorEntity> authors = persistence.find(bookId).getAuthors();
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        int index = authors.indexOf(authorEntity);
        if (index >= 0) {
            return authors.get(index);
        }
        return null;
    }
}
