package co.edu.uniandes.csw.bookbasico.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@Entity
public class BookEntity extends BaseEntity implements Serializable {

    private String isbn;

    private String image;

    private String description;
    
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    /**
     * Relación de uno a muchos hacia ReviewEntity
     * El parámetro mappedBy indica que no es una relación nueva, sino que 
     * corresponde a una relación ya existente desde ReviewEntity.
     * El parámetro cascade indica que todas las operaciones realizadas sobre
     * BookEntity deben propagarse a los elementos de la relación.
     * El parámetro orphanRemoval indica que se debe eliminar toda instancia
     * de ReviewEntity que no pertenezca a esta relación.
     */
    @PodamExclude
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;

    /**
     * Relación muchos a muchos con AuthorEntity
     * En JPA existe el concepto de "dueño de la relación". Es decir, cuando 
     * hay una relación bidireccional (navegable en ambos sentidos) se establece
     * un dueño de la relación (quien crea la relación) y en la otra entidad
     * se define una relación que depende de la ya existente a través de mappedBy.
     * En este caso, BookEntity es dueño de la anotación, por lo que no se asigna
     * el parámetro mappedBy pero se debe definir en el otro extremo, es decir 
     * en la clase AuthorEntity.
     */
    @PodamExclude
    @ManyToMany
    private List<AuthorEntity> authors;
    
    /**
     * Relación muchos a uno con EditorialEntity. Este tipo de relación crea en
     * la tabla BookEntity una llave foránea hacia EditorialEntity.
     */
    @PodamExclude
    @ManyToOne
    private EditorialEntity editorial;

    /**
     * @generated
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @generated
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @generated
     */
    public String getImage() {
        return image;
    }

    /**
     * @generated
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * @generated
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorEntity> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((BookEntity)obj).getId()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    public EditorialEntity getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialEntity editorial) {
        this.editorial = editorial;
    }
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
