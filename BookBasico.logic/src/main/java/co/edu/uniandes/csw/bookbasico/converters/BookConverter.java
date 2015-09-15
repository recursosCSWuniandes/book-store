package co.edu.uniandes.csw.bookbasico.converters;

import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class BookConverter {
    
    public static BookDTO refEntity2DTO(BookEntity entity) {
        if (entity != null) {
            BookDTO dto = new BookDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setIsbn(entity.getIsbn());
            dto.setImage(entity.getImage());
            dto.setDescription(entity.getDescription());

            return dto;
        } else {
            return null;
        }
    }

    public static BookEntity refDTO2Entity(BookDTO dto) {
        if (dto != null) {
            BookEntity entity = new BookEntity();
            entity.setId(dto.getId());

            return entity;
        } else {
            return null;
        }
    }

    private static BookDTO basicEntity2DTO(BookEntity entity) {
        if (entity != null) {
            BookDTO dto = refEntity2DTO(entity);

            return dto;
        } else {
            return null;
        }
    }

    private static BookEntity basicDTO2Entity(BookDTO dto) {
        if (dto != null) {
            BookEntity entity = new BookEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setIsbn(dto.getIsbn());
            entity.setImage(dto.getImage());
            entity.setDescription(dto.getDescription());

            return entity;
        } else {
            return null;
        }
    }
    
    public static BookDTO fullEntity2DTO(BookEntity entity){
        BookDTO dto = basicEntity2DTO(entity);
        dto.setReviews(ReviewConverter.listEntity2DTO(entity.getReviews()));
        dto.setAuthors(AuthorConverter.listEntity2DTO(entity.getAuthors()));
        return dto;
    }
    
    public static BookEntity fullDTO2Entity(BookDTO dto){
        BookEntity entity = basicDTO2Entity(dto);
        entity.setReviews(ReviewConverter.childListDTO2Entity(dto.getReviews(), entity));
        entity.setAuthors(AuthorConverter.listDTO2Entity(dto.getAuthors()));
        return entity;
    }

    public static List<BookDTO> listEntity2DTO(List<BookEntity> entities) {
        List<BookDTO> dtos = new ArrayList<BookDTO>();
        if (entities != null) {
            for (BookEntity entity : entities) {
                dtos.add(basicEntity2DTO(entity));
            }
        }
        return dtos;
    }

    public static List<BookEntity> listDTO2Entity(List<BookDTO> dtos) {
        List<BookEntity> entities = new ArrayList<BookEntity>();
        if (dtos != null) {
            for (BookDTO dto : dtos) {
                entities.add(basicDTO2Entity(dto));
            }
        }
        return entities;
    }
}
