package co.edu.uniandes.csw.bookbasico.tests;

import co.edu.uniandes.csw.bookbasico.ejbs.BookLogic;
import co.edu.uniandes.csw.bookbasico.api.IBookLogic;

import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import static co.edu.uniandes.csw.bookbasico.tests._TestUtil.*;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class BookLogicTest {
    public static final String DEPLOY = "Prueba";

    /**
     * @generated
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(BookEntity.class.getPackage())
                .addPackage(BookLogic.class.getPackage())
                .addPackage(IBookLogic.class.getPackage())
                .addPackage(BookPersistence.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    @Inject
    private IBookLogic bookLogic;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    UserTransaction utx;

    /**
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from BookEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<BookEntity> data = new ArrayList<BookEntity>();

    /**
     * @generated
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BookEntity entity = new BookEntity();
            entity.setName("entityName" + i);
            entity.setIsbn("entityIsbn" + i);
            entity.setImage("entityImage" + i);
            entity.setDescription("entityDesc" + i);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * @generated
     */
    @Test
    public void createBookTest() {
        BookEntity entity = new BookEntity();
        entity.setName("varname1");
        entity.setIsbn("varisbn2");
        entity.setImage("varimagen3");
        entity.setDescription("vardesc4");
        BookEntity result = bookLogic.createBook(entity);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), entity.getName());
        Assert.assertEquals(result.getIsbn(), entity.getIsbn());
        Assert.assertEquals(result.getImage(), entity.getImage());
        Assert.assertEquals(result.getDescription(), entity.getDescription());
    }

    /**
     * @generated
     */
    @Test
    public void getBooksTest() {
        List<BookEntity> list = bookLogic.getBooks();
        Assert.assertEquals(data.size(), list.size());
        for (BookEntity entity : list) {
            boolean found = false;
            for (BookEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * @generated
     */
    @Test
    public void getBookTest() {
        BookEntity entity = data.get(0);
        BookEntity resultEntity = bookLogic.getBook(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getIsbn(), resultEntity.getIsbn());
        Assert.assertEquals(entity.getImage(), resultEntity.getImage());
        Assert.assertEquals(entity.getDescription(), resultEntity.getDescription());
    }

    /**
     * @generated
     */
    @Test
    public void deleteBookTest() {
        BookEntity entity = data.get(0);
        bookLogic.deleteBook(entity.getId());
        BookEntity deleted = em.find(BookEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * @generated
     */
    @Test
    public void updateBookTest() {
        BookEntity entity = data.get(0);
        BookEntity pojoEntity = new BookEntity();
        pojoEntity.setName("newpojoname");
        pojoEntity.setIsbn("newpojoisbn");
        pojoEntity.setImage("newpojoimage");
        pojoEntity.setDescription("newpojodesc");
        pojoEntity.setId(entity.getId());
        
        bookLogic.updateBook(pojoEntity);

        BookEntity resp = em.find(BookEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getIsbn(), resp.getIsbn());
        Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
        Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
    }
}
