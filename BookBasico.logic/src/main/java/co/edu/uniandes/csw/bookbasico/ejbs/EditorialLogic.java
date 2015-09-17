package co.edu.uniandes.csw.bookbasico.ejbs;

import co.edu.uniandes.csw.bookbasico.api.IEditorialLogic;
import co.edu.uniandes.csw.bookbasico.converters.EditorialConverter;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.bookbasico.entities.EditorialEntity;
import co.edu.uniandes.csw.bookbasico.persistence.EditorialPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EditorialLogic implements IEditorialLogic{

    @Inject
    private EditorialPersistence persistence;

    public List<EditorialDTO> getEditorials() {
        return EditorialConverter.listEntity2DTO(persistence.findAll());
    }

    public EditorialDTO getEditorial(Long id) {
        return EditorialConverter.basicEntity2DTO(persistence.find(id));
    }

    public EditorialDTO createEditorial(EditorialDTO dto) {
        EditorialEntity entity = EditorialConverter.basicDTO2Entity(dto);
        persistence.create(entity);
        return EditorialConverter.basicEntity2DTO(entity);
    }

    public EditorialDTO updateEditorial(EditorialDTO dto) {
        EditorialEntity entity = persistence.update(EditorialConverter.basicDTO2Entity(dto));
        return EditorialConverter.basicEntity2DTO(entity);
    }

    public void deleteEditorial(Long id) {
        persistence.delete(id);
    }
}
