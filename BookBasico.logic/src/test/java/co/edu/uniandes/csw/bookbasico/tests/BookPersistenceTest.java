package co.edu.uniandes.csw.bookbasico.tests;

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
public class BookPersistenceTest {
    public static final String DEPLOY = "Prueba";

    /**
     * @generated
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(BookEntity.class.getPackage())
                .addPackage(BookPersistence.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    @Inject
    private BookPersistence bookPersistence;

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
            entity.setName(generateRandom(String.class));
            entity.setIsbn(generateRandom(String.class));
            entity.setImage(generateRandom(String.class));
            entity.setDescription(generateRandom(String.class));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * @generated
     */
    @Test
    public void createBookTest() {
        BookEntity newEntity = new BookEntity();
        newEntity.setName(generateRandom(String.class));
        newEntity.setIsbn(generateRandom(String.class));
        newEntity.setImage(generateRandom(String.class));
        newEntity.setDescription(generateRandom(String.class));

        BookEntity result = bookPersistence.create(newEntity);

        Assert.assertNotNull(result);

        BookEntity entity = em.find(BookEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getIsbn(), entity.getIsbn());
        Assert.assertEquals(newEntity.getImage(), entity.getImage());
        Assert.assertEquals(newEntity.getDescription(), entity.getDescription());
    }

    /**
     * @generated
     */
    @Test
    public void getBooksTest() {
        List<BookEntity> list = bookPersistence.findAll(null, null);
        Assert.assertEquals(data.size(), list.size());
        for (BookEntity ent : list) {
            boolean found = false;
            for (BookEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
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
        BookEntity newEntity = bookPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
        Assert.assertEquals(entity.getIsbn(), newEntity.getIsbn());
        Assert.assertEquals(entity.getImage(), newEntity.getImage());
        Assert.assertEquals(entity.getDescription(), newEntity.getDescription());
    }

    /**
     * @generated
     */
    @Test
    public void deleteBookTest() {
        BookEntity entity = data.get(0);
        bookPersistence.delete(entity.getId());
        BookEntity deleted = em.find(BookEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * @generated
     */
    @Test
    public void updateBookTest() {
        BookEntity entity = data.get(0);

        BookEntity newEntity = new BookEntity();

        newEntity.setId(entity.getId());
        newEntity.setName(generateRandom(String.class));
        newEntity.setIsbn(generateRandom(String.class));
        newEntity.setImage(generateRandom(String.class));
        newEntity.setDescription(generateRandom(String.class));

        bookPersistence.update(newEntity);

        BookEntity resp = em.find(BookEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
        Assert.assertEquals(newEntity.getIsbn(), resp.getIsbn());
        Assert.assertEquals(newEntity.getImage(), resp.getImage());
        Assert.assertEquals(newEntity.getDescription(), resp.getDescription());
    }

    /**
     * @generated
     */
    @Test
    public void getBookPaginationTest() {
        //Page 1
        List<BookEntity> ent1 = bookPersistence.findAll(1, 2);
        Assert.assertNotNull(ent1);
        Assert.assertEquals(2, ent1.size());
        //Page 2
        List<BookEntity> ent2 = bookPersistence.findAll(2, 2);
        Assert.assertNotNull(ent2);
        Assert.assertEquals(1, ent2.size());

        for (BookEntity ent : ent1) {
            boolean found = false;
            for (BookEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        for (BookEntity ent : ent2) {
            boolean found = false;
            for (BookEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
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
    public void findByName() {
        String name = data.get(0).getName();
        List<BookEntity> cache = new ArrayList<BookEntity>();
        List<BookEntity> list = bookPersistence.findByName(name);

        for (BookEntity entity : data) {
            if (entity.getName().equals(name)) {
                cache.add(entity);
            }
        }
        Assert.assertEquals(list.size(), cache.size());
        for (BookEntity ent : list) {
            boolean found = false;
            for (BookEntity cacheEntity : cache) {
                if (cacheEntity.getName().equals(ent.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Assert.fail();
            }
        }
    }
}
