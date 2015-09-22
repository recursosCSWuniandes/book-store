package co.edu.uniandes.csw.bookbasico.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @generated
 */
@Entity
public class BookEntity implements Serializable {

    /**
     * La anotación @Id indica a JPA que este campo es la llave primaria de la entidad
     * La anotación @GeneratedValue indica a JPA que el valor del campo debe ser generado 
     * automáticamente. La secuencia del valor del id dependerá de "Book".
     */
    @Id
    @GeneratedValue(generator = "Book")
    private Long id;

    private String name;

    private String isbn;

    private String image;

    private String description;

    /**
     * Relación de uno a muchos hacia ReviewEntity
     * El parámetro mappedBy indica que no es una relación nueva, sino que 
     * corresponde a una relación ya existente desde ReviewEntity.
     * El parámetro cascade indica que todas las operaciones realizadas sobre
     * BookEntity deben propagarse a los elementos de la relación.
     * El parámetro orphanRemoval indica que se debe eliminar toda instancia
     * de ReviewEntity que no pertenezca a esta relación.
     */
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
    @ManyToMany
    private List<AuthorEntity> authors;
    
    /**
     * Relación muchos a uno con EditorialEntity. Este tipo de relación crea en
     * la tabla BookEntity una llave foránea hacia EditorialEntity.
     */
    @ManyToOne
    private EditorialEntity editorial;

    /**
     * @generated
     */
    public Long getId() {
        return id;
    }

    /**
     * @generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * @generated
     */
    public void setName(String name) {
        this.name = name;
    }

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
        return this.getId().equals(obj); //To change body of generated methods, choose Tools | Templates.
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
}
