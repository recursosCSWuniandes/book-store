package co.edu.uniandes.csw.bookbasico.persistence;

import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import javax.ejb.Stateless;

/**
 * @generated
 */
@Stateless
public class BookPersistence extends CrudPersistence<BookEntity> {

    /**
     * @generated
     */
    public BookPersistence() {
        this.entityClass = BookEntity.class;
    }
}
