package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import java.util.List;

public interface IBookLogic {

    public List<BookDTO> getBooks();

    public BookDTO getBook(Long id);

    public BookDTO createBook(BookDTO dto);

    public BookDTO updateBook(BookDTO dto);

    public void deleteBook(Long id);

    public AuthorDTO addAuthor(Long authorId, Long bookId);

    public void removeAuthor(Long authorId, Long bookId);

    public List<AuthorDTO> replaceAuthors(List<AuthorDTO> authors, Long bookId);

    public List<AuthorDTO> getAuthors(Long bookId);

    public AuthorDTO getAuthor(Long bookId, Long authorId);
}
