package co.edu.uniandes.csw.bookbasico.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReviewEntity implements Serializable {

    /**
     * La anotación @Id indica a JPA que este campo es la llave primaria de la entidad
     * La anotación @GeneratedValue indica a JPA que el campo es generado automáticamente. 
     * La secuencia del valor del id dependerá de "Review"
     */
    @Id
    @GeneratedValue(generator = "Review")
    private Long id;

    private String name;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
