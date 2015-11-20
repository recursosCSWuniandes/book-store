package co.edu.uniandes.csw.bookbasico.ejbs;

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
public class BookLogic implements IBookLogic {

    @Inject
    private BookPersistence persistence;

    @Inject
    private AuthorPersistence authorPersistence;

    @Override
    public List<BookDTO> getBooks() {
        return BookConverter.listEntity2DTO(persistence.findAll());
    }

    @Override
    public BookDTO getBook(Long id) {
        return BookConverter.fullEntity2DTO(persistence.find(id));
    }

    @Override
    public BookDTO createBook(BookDTO dto) {
        BookEntity entity = BookConverter.fullDTO2Entity(dto);
        persistence.create(entity);
        return BookConverter.fullEntity2DTO(entity);
    }

    @Override
    public BookDTO updateBook(BookDTO dto) {
        BookEntity newEntity = BookConverter.fullDTO2Entity(dto);
        BookEntity oldEntity = persistence.find(dto.getId());
        newEntity.setAuthors(oldEntity.getAuthors());
        BookEntity entity = persistence.update(newEntity);
        return BookConverter.fullEntity2DTO(entity);
    }

    @Override
    public void deleteBook(Long id) {
        persistence.delete(id);
    }

    @Override
    public AuthorDTO addAuthor(Long authorId, Long bookId) throws BusinessLogicException {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity authorEntity = authorPersistence.find(authorId);
        if (authorEntity.getBirthDate() != null && authorEntity.getBirthDate().after(bookEntity.getPublishDate())) {
            throw new BusinessLogicException("Publish date can't be prior to authors birthdate");
        }
        bookEntity.getAuthors().add(authorEntity);
        return AuthorConverter.basicEntity2DTO(authorEntity);
    }

    @Override
    public void removeAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity author = new AuthorEntity();
        author.setId(authorId);
        bookEntity.getAuthors().remove(author);
    }

    @Override
    public List<AuthorDTO> replaceAuthors(List<AuthorDTO> authors, Long bookId) throws BusinessLogicException {
        BookEntity bookEntity = persistence.find(bookId);
        List<AuthorEntity> authorList = authorPersistence.findAll();
        List<AuthorEntity> newAuthorList = AuthorConverter.listDTO2Entity(authors);
        for (AuthorEntity author : newAuthorList) {
            int i = authorList.indexOf(author);
            if (authorList.get(i).getBirthDate() == null) {
                continue;
            }
            if (authorList.get(i).getBirthDate().after(bookEntity.getPublishDate())) {
                throw new BusinessLogicException("Publish date can't be prior to authors birthdate");
            }
        }
        bookEntity.setAuthors(newAuthorList);
        return AuthorConverter.listEntity2DTO(bookEntity.getAuthors());
    }

    @Override
    public List<AuthorDTO> getAuthors(Long bookId) {
        return AuthorConverter.listEntity2DTO(persistence.find(bookId).getAuthors());
    }

    @Override
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
