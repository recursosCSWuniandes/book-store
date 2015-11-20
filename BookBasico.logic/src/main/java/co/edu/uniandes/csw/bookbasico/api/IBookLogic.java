package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import java.util.List;

public interface IBookLogic {

    public List<BookEntity> getBooks();

    public BookEntity getBook(Long id);

    public BookEntity createBook(BookEntity entity);

    public BookEntity updateBook(BookEntity entity);

    public void deleteBook(Long id);

    public AuthorEntity addAuthor(Long authorId, Long bookId) throws BusinessLogicException;

    public void removeAuthor(Long authorId, Long bookId);

    public List<AuthorEntity> replaceAuthors(List<AuthorEntity> authors, Long bookId) throws BusinessLogicException;

    public List<AuthorEntity> getAuthors(Long bookId);

    public AuthorEntity getAuthor(Long bookId, Long authorId);
}
