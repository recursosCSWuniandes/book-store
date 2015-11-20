package co.edu.uniandes.csw.bookbasico.converters;

import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class AuthorConverter {

    private AuthorConverter() {
    }

    /**
     * Convierte una instancia de AuthorEntity a AuthorDTO Convierte Únicamente
     * los atributos propios de la entidad y no tiene en cuenta las relaciones
     * con otras entidades.
     *
     * @param entity instancia de AuthorEntity a convertir
     * @return Instancia de AuthorDTO con los datos básicos de entity
     */
    public static AuthorDTO basicEntity2DTO(AuthorEntity entity) {
        if (entity != null) {

            AuthorDTO dto = new AuthorDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setBirthDate(entity.getBirthDate());

            return dto;
        }
        return null;
    }

    /**
     * Convierte una instancia de AuthorDTO a AuthorEntity Convierte todos los
     * atributos propios de AuthorDTO a la entidad y no tiene en cuenta las
     * relaciones con otras entidades.
     *
     * @param dto Instancia de AuthorDTO a convertir
     * @return Instancia de AuthorEntity con los datos básicos de dto
     */
    public static AuthorEntity basicDTO2Entity(AuthorDTO dto) {
        if (dto != null) {

            AuthorEntity entity = new AuthorEntity();

            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setBirthDate(dto.getBirthDate());

            return entity;
        }
        return null;
    }

    /**
     * Convierte una colección de instancias de AuthorEntity a AuthorDTO Por
     * cada instancia en la colección invoca basicEntity2DTO (por cada instancia
     * solo convierte sus atributos básicos) y guarda el resultado en una nueva
     * colección
     *
     * @param entities Colección de instancias de AuthorEntity
     * @return Colección de instancias de AuthorDTO
     */
    public static List<AuthorDTO> listEntity2DTO(List<AuthorEntity> entities) {
        List<AuthorDTO> dtos = new ArrayList<AuthorDTO>();
        if (entities != null) {
            for (AuthorEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    /**
     * Convierte una colección de instancias de AuthorDTO a AuthorEntity Por
     * cada instancia en la colección invoca basicDTO2Entity (solo los atributos
     * básicos) y guarda el resultado en una nueva colección.
     *
     * @param dtos Colección de instancias de AuthorDTO
     * @return Colección de instancias de AuthorEntity
     */
    public static List<AuthorEntity> listDTO2Entity(List<AuthorDTO> dtos) {
        List<AuthorEntity> entities = new ArrayList<AuthorEntity>();
        if (dtos != null) {
            for (AuthorDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
}
