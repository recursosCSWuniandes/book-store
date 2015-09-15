package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;
import co.edu.uniandes.csw.bookbasico.converters.AuthorConverter;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.persistence.AuthorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthorLogic implements IAuthorLogic{

    @Inject
    private AuthorPersistence persistence;

    public List<AuthorDTO> getAuthors() {
        return AuthorConverter.listEntity2DTO(persistence.findAll());
    }

    public AuthorDTO getAuthor(Long id) {
        return AuthorConverter.basicEntity2DTO(persistence.find(id));
    }

    public AuthorDTO createAuthor(AuthorDTO dto) {
        AuthorEntity entity = AuthorConverter.basicDTO2Entity(dto);
        persistence.create(entity);
        return AuthorConverter.basicEntity2DTO(entity);
    }

    public AuthorDTO updateAuthor(AuthorDTO dto) {
        AuthorEntity entity = persistence.update(AuthorConverter.basicDTO2Entity(dto));
        return AuthorConverter.basicEntity2DTO(entity);
    }

    public void deleteAuthor(Long id) {
        persistence.delete(id);
    }
}
