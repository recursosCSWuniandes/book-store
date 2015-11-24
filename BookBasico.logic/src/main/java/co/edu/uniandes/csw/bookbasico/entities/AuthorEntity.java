package co.edu.uniandes.csw.bookbasico.entities;

import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "AuthorEntity.findByEditorial",
            query = "SELECT DISTINCT a FROM AuthorEntity a join a.books b where b.editorial.id = :editorial_id")
})
public class AuthorEntity extends BaseEntity implements Serializable {

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * Relación muchos a muchos entre AuthorEntity y BookEntity. Dado que la
     * misma relación ya está definida en BookEntity, se debe agregar el
     * parámetro mappedBy, cuyo valor es el nombre del atributo en BookEntity
     * que define la relación. Si no se hace esto, JPA crea una nueva relación
     * con BookEntity.
     */
    @ManyToMany(mappedBy = "authors")
    private List<BookEntity> books;
    

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((AuthorEntity) obj).getId()); //To change body of generated methods, choose Tools | Templates.
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
