package co.edu.uniandes.csw.bookbasico.converters;

import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.bookbasico.entities.EditorialEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class EditorialConverter {

    private EditorialConverter() {
    }

    /**
     * Convierte una instancia de EditorialEntity a EditorialDTO
     *
     * @param entity Instancia de EditorialEntity a convertir
     * @return Instancia de AuthorDTO con los datos de entity
     */
    public static EditorialDTO basicEntity2DTO(EditorialEntity entity) {
        if (entity != null) {
            EditorialDTO dto = new EditorialDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        }
        return null;
    }

    /**
     * Convierte una instancia de EditorialDTO a EditorialEntity
     *
     * @param dto Instancia de EditorialDTO a convertir
     * @return Instancia de EditorialEntity con los datos de dto
     */
    public static EditorialEntity basicDTO2Entity(EditorialDTO dto) {
        if (dto != null) {

            EditorialEntity entity = new EditorialEntity();

            entity.setId(dto.getId());
            entity.setName(dto.getName());

            return entity;
        }
        return null;
    }

    /**
     * Convierte una colección de instancias de EditorialEntity a EditorialDTO
     * Por cada instancia de EditorialEntity invoca basicEntity2DTO y guarda el
     * resultado en una colección nueva
     *
     * @param entities Colección de instancias de EditorialEntity
     * @return Colección de instancias de EditorialDTO
     */
    public static List<EditorialDTO> listEntity2DTO(List<EditorialEntity> entities) {
        List<EditorialDTO> dtos = new ArrayList<EditorialDTO>();
        if (entities != null) {
            for (EditorialEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    /**
     * Convierte una colección dde instancias de EditorialDTO a EditorialEntity
     * Por cada instancia de EditorialDTO invoca basicDTO2Entity y guarda el
     * resultado en una colección nueva
     *
     * @param dtos Colección de instancias de EditorialDTO
     * @return Colección de instancias de EditorialEntity
     */
    public static List<EditorialEntity> listDTO2Entity(List<EditorialDTO> dtos) {
        List<EditorialEntity> entities = new ArrayList<EditorialEntity>();
        if (dtos != null) {
            for (EditorialDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
}
