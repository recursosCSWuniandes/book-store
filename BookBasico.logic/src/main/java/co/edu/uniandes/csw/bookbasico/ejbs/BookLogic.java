package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookbasico.persistence.AuthorPersistence;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BookLogic implements IBookLogic {

    @Inject
    private BookPersistence persistence;

    @Inject
    private AuthorPersistence authorPersistence;

    @Override
    public List<BookEntity> getBooks() {
        return persistence.findAll();
    }

    @Override
    public BookEntity getBook(Long id) {
        return persistence.find(id);
    }

    @Override
    public BookEntity createBook(BookEntity entity) {
        persistence.create(entity);
        return entity;
    }

    @Override
    public BookEntity updateBook(BookEntity entity) {
        BookEntity newEntity = entity;
        BookEntity oldEntity = persistence.find(entity.getId());
        newEntity.setAuthors(oldEntity.getAuthors());
        BookEntity updatedEntity = persistence.update(newEntity);
        return updatedEntity;
    }

    @Override
    public void deleteBook(Long id) {
        persistence.delete(id);
    }

    @Override
    public AuthorEntity addAuthor(Long authorId, Long bookId) throws BusinessLogicException {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity authorEntity = authorPersistence.find(authorId);
        if (authorEntity.getBirthDate() != null && authorEntity.getBirthDate().after(bookEntity.getPublishDate())) {
            throw new BusinessLogicException("Publish date can't be prior to authors birthdate");
        }
        bookEntity.getAuthors().add(authorEntity);
        return authorEntity;
    }

    @Override
    public void removeAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        bookEntity.getAuthors().remove(author);
    }

    @Override
    public List<AuthorEntity> replaceAuthors(List<AuthorEntity> authors, Long bookId) throws BusinessLogicException {
        BookEntity bookEntity = persistence.find(bookId);
        List<AuthorEntity> authorList = authorPersistence.findAll();
        for (AuthorEntity author : authors) {
            int i = authorList.indexOf(author);
            if (authorList.get(i).getBirthDate() == null) {
                continue;
            }
            if (authorList.get(i).getBirthDate().after(bookEntity.getPublishDate())) {
                throw new BusinessLogicException("Publish date can't be prior to authors birthdate");
            }
        }
        bookEntity.setAuthors(authors);
        return bookEntity.getAuthors();
    }

    @Override
    public List<AuthorEntity> getAuthors(Long bookId) {
        return persistence.find(bookId).getAuthors();
    }

    @Override
    public AuthorEntity getAuthor(Long bookId, Long authorId) {
        List<AuthorEntity> authors = persistence.find(bookId).getAuthors();
        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        int index = authors.indexOf(author);
        if (index >= 0) {
            return authors.get(index);
        }
        return null;
    }
}
