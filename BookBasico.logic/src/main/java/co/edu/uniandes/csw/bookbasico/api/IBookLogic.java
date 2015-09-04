package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import java.util.List;

public interface IBookLogic {
    public List<BookDTO> getBooks();
    public BookDTO getBook(Long id);
    public BookDTO createBook(BookDTO dto);
    public BookDTO updateBook(BookDTO dto);
    public void deleteBook(Long id);
}
