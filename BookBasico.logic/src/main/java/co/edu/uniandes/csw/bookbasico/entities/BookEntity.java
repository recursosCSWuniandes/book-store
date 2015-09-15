package co.edu.uniandes.csw.bookbasico.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @generated
 */
@Entity
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "Book")
    private Long id;

    private String name;

    private String isbn;

    private String image;

    private String description;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;
    
    @OneToMany
    private List<AuthorEntity> authors;

    /**
     * @generated
     */
    public Long getId(){
        return id;
    }

    /**
     * @generated
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * @generated
     */
    public String getName(){
        return name;
    }

    /**
     * @generated
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @generated
     */
    public String getIsbn(){
        return isbn;
    }

    /**
     * @generated
     */
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    /**
     * @generated
     */
    public String getImage(){
        return image;
    }

    /**
     * @generated
     */
    public void setImage(String image){
        this.image = image;
    }

    /**
     * @generated
     */
    public String getDescription(){
        return description;
    }

    /**
     * @generated
     */
    public void setDescription(String description){
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

}
