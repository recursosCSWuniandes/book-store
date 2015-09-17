package co.edu.uniandes.csw.bookbasico.converters;

import co.edu.uniandes.csw.bookbasico.dtos.ReviewDTO;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.entities.ReviewEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class ReviewConverter {

    public static ReviewDTO basicEntity2DTO(ReviewEntity entity) {
        if (entity != null) {

            ReviewDTO dto = new ReviewDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSource(entity.getSource());
            dto.setDescription(entity.getDescription());
            dto.setBook(BookConverter.refEntity2DTO(entity.getBook()));

            return dto;
        }
        return null;
    }

    public static ReviewEntity basicDTO2Entity(ReviewDTO dto) {
        if (dto != null) {

            ReviewEntity entity = new ReviewEntity();

            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setSource(dto.getSource());
            entity.setDescription(dto.getDescription());
            entity.setBook(BookConverter.refDTO2Entity(dto.getBook()));

            return entity;
        }
        return null;
    }

    public static List<ReviewDTO> listEntity2DTO(List<ReviewEntity> entities) {
        List<ReviewDTO> dtos = new ArrayList<ReviewDTO>();
        if (entities != null) {
            for (ReviewEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    public static List<ReviewEntity> listDTO2Entity(List<ReviewDTO> dtos) {
        List<ReviewEntity> entities = new ArrayList<ReviewEntity>();
        if (dtos != null) {
            for (ReviewDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }

    public static ReviewEntity childDTO2Entity(ReviewDTO dto, BookEntity parent) {
        ReviewEntity entity = basicDTO2Entity(dto);
        entity.setBook(parent);
        return entity;
    }

    public static List<ReviewEntity> childListDTO2Entity(List<ReviewDTO> dtos, BookEntity parent) {
        List<ReviewEntity> entities = new ArrayList<ReviewEntity>();
        if (dtos != null) {
            for (ReviewDTO dto : dtos) {
                entities.add(childDTO2Entity(dto, parent));
            }
        }
        return entities;
    }
}
