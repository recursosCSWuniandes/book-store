package co.edu.uniandes.csw.bookbasico.converters;

import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.bookbasico.entities.EditorialEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class EditorialConverter {

    public static EditorialDTO basicEntity2DTO(EditorialEntity entity) {
        if (entity != null) {
            EditorialDTO dto = new EditorialDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        }
        return null;
    }

    public static EditorialEntity basicDTO2Entity(EditorialDTO dto) {
        if (dto != null) {

            EditorialEntity entity = new EditorialEntity();

            entity.setId(dto.getId());
            entity.setName(dto.getName());

            return entity;
        }
        return null;
    }

    public static List<EditorialDTO> listEntity2DTO(List<EditorialEntity> entities) {
        List<EditorialDTO> dtos = new ArrayList<EditorialDTO>();
        if (entities != null) {
            for (EditorialEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

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
