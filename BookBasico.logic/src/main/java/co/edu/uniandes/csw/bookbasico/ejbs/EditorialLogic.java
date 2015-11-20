package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IEditorialLogic;
import co.edu.uniandes.csw.bookbasico.converters.BookConverter;
import co.edu.uniandes.csw.bookbasico.converters.EditorialConverter;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.entities.EditorialEntity;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import co.edu.uniandes.csw.bookbasico.persistence.EditorialPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EditorialLogic implements IEditorialLogic {

    @Inject
    private EditorialPersistence persistence;

    @Inject
    private BookPersistence bookPersistence;

    @Override
    public List<EditorialDTO> getEditorials() {
        return EditorialConverter.listEntity2DTO(persistence.findAll());
    }

    @Override
    public EditorialDTO getEditorial(Long id) {
        return EditorialConverter.basicEntity2DTO(persistence.find(id));
    }

    @Override
    public EditorialDTO createEditorial(EditorialDTO dto) {
        EditorialEntity entity = EditorialConverter.basicDTO2Entity(dto);
        persistence.create(entity);
        return EditorialConverter.basicEntity2DTO(entity);
    }

    @Override
    public EditorialDTO updateEditorial(EditorialDTO dto) {
        EditorialEntity entity = persistence.update(EditorialConverter.basicDTO2Entity(dto));
        return EditorialConverter.basicEntity2DTO(entity);
    }

    @Override
    public void deleteEditorial(Long id) {
        persistence.delete(id);
    }

    @Override
    public BookDTO addBook(Long bookId, Long editorialId) {
        EditorialEntity editorialEntity = persistence.find(editorialId);
        BookEntity bookEntity = bookPersistence.find(bookId);
        editorialEntity.getBooks().add(bookEntity);
        return BookConverter.basicEntity2DTO(bookEntity);
    }

    @Override
    public void removeBook(Long bookId, Long editorialId) {
        EditorialEntity editorialEntity = persistence.find(editorialId);
        BookEntity book = bookPersistence.find(bookId);
        book.setEditorial(null);
        editorialEntity.getBooks().remove(book);
    }

    @Override
    public List<BookDTO> replaceBooks(List<BookDTO> books, Long editorialId) {
        EditorialEntity editorial = persistence.find(editorialId);
        List<BookEntity> bookList = bookPersistence.findAll();
        List<BookEntity> newBookList = BookConverter.listDTO2Entity(books);
        for (BookEntity book : bookList) {
            if (newBookList.contains(book)) {
                book.setEditorial(editorial);
            } else {
                if (book.getEditorial() != null && book.getEditorial().equals(editorial)) {
                    book.setEditorial(null);
                }
            }
        }
        return books;
    }

    @Override
    public List<BookDTO> getBooks(Long editorialId) {
        return BookConverter.listEntity2DTO(persistence.find(editorialId).getBooks());
    }

    @Override
    public BookDTO getBook(Long editorialId, Long bookId) {
        List<BookEntity> books = persistence.find(editorialId).getBooks();
        BookEntity book = new BookEntity();
        book.setId(bookId);
        int index = books.indexOf(book);
        if (index >= 0) {
            return BookConverter.basicEntity2DTO(books.get(index));
        }
        return null;
    }
}
