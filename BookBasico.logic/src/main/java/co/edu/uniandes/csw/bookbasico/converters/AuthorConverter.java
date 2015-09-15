package co.edu.uniandes.csw.bookbasico.converters;

import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class AuthorConverter {
    public static AuthorDTO basicEntity2DTO(AuthorEntity entity){
        AuthorDTO dto = new AuthorDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        
        return dto;
    }
    
    public static AuthorEntity basicDTO2Entity(AuthorDTO dto){
        AuthorEntity entity = new AuthorEntity();
        
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        
        return entity;
    }
    
    public static List<AuthorDTO> listEntity2DTO(List<AuthorEntity> entities){
        List<AuthorDTO> dtos = new ArrayList<AuthorDTO>();
        if (entities != null) {
            for (AuthorEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }
    
    public static List<AuthorEntity> listDTO2Entity(List<AuthorDTO> dtos){
        List<AuthorEntity> entities = new ArrayList<AuthorEntity>();
        if (dtos != null) {
            for (AuthorDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
}
