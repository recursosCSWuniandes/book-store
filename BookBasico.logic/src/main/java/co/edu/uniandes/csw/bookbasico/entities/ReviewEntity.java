package co.edu.uniandes.csw.bookbasico.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ReviewEntity extends BaseEntity implements Serializable {

    private String source;
    
    private String description;
    
    /**
     * Relación Muchos a uno con BookEntity
     * Esta relación es mapeada desde BookEntity por la relación en el atributo
     * reviews.
     * La anotación crea una llave foránea en la base de datos que
     * apunta a la tabla de BookEntity
     */
    @ManyToOne
    private BookEntity book;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}
