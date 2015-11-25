package co.edu.uniandes.csw.bookbasico.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
public class EditorialEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(mappedBy = "editorial")
    private List<BookEntity> books;

    

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((EditorialEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
