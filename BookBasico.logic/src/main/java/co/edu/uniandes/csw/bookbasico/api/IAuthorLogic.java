package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import java.util.List;

public interface IAuthorLogic {

    public List<AuthorDTO> getAuthors();

    public AuthorDTO getAuthor(Long id);

    public AuthorDTO createAuthor(AuthorDTO dto);

    public AuthorDTO updateAuthor(AuthorDTO dto);

    public void deleteAuthor(Long id);
}
