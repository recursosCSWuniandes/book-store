package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import java.util.List;

public interface IAuthorLogic {

    public List<AuthorDTO> getAuthors();

    public AuthorDTO getAuthor(Long id);

    public AuthorDTO createAuthor(AuthorDTO dto);

    public AuthorDTO updateAuthor(AuthorDTO dto);

    public void deleteAuthor(Long id);
    
    public BookDTO addBook(Long BookId, Long AuthorId) throws BusinessLogicException;
    
    public void removeBook(Long BookId, Long AuthorId);
    
    public List<BookDTO> replaceBooks(List<BookDTO> Books, Long AuthorId) throws BusinessLogicException;
    
    public List<BookDTO> getBooks(Long AuthorId);
    
    public BookDTO getBook(Long AuthorId, Long BookId);
    
    public List<AuthorDTO> findByEditorial(Long editorialId);
}
