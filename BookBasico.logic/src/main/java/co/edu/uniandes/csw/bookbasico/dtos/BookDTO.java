package co.edu.uniandes.csw.bookbasico.dtos;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class BookDTO {

    private Long id;
    private String name;
    private String isbn;
    private String image;
    private String description;
    @PodamExclude
    private List<ReviewDTO> reviews;
    @PodamExclude
    private EditorialDTO editorial;

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

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }
}
