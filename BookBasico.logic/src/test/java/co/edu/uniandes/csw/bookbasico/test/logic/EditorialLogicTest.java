package co.edu.uniandes.csw.bookbasico.test.logic;

import co.edu.uniandes.csw.bookbasico.ejbs.EditorialLogic;
import co.edu.uniandes.csw.bookbasico.ejbs.BookLogic;
import co.edu.uniandes.csw.bookbasico.api.IEditorialLogic;
import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.EditorialEntity;
import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
import co.edu.uniandes.csw.bookbasico.persistence.EditorialPersistence;
import java.util.ArrayList;
import java.util.Date;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class EditorialLogicTest {
    public static final String DEPLOY = "Prueba";

    /**
     * @generated
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(EditorialEntity.class.getPackage())
                .addPackage(EditorialLogic.class.getPackage())
                .addPackage(IEditorialLogic.class.getPackage())
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
    @Inject
    private IEditorialLogic editorialLogic;

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
        em.createQuery("delete from EditorialEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<BookEntity> dataBooks = new ArrayList<BookEntity>();
    
    /**
     * @generated
     */
    private List<EditorialEntity> data = new ArrayList<EditorialEntity>();

    /**
     * @generated
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            EditorialEntity entity = factory.manufacturePojo(EditorialEntity.class);
            entity.setId(i+1L);
            em.persist(entity);
            data.add(entity);
            
            BookEntity bookEntity = factory.manufacturePojo(BookEntity.class);
            bookEntity.setId(i+1L);
            em.persist(bookEntity);
            dataBooks.add(bookEntity);
        }
    }

    /**
     * @generated
     */
    @Test
    public void createEditorialTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EditorialEntity entity = factory.manufacturePojo(EditorialEntity.class);
        
        EditorialEntity result = editorialLogic.createEditorial(entity);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), entity.getName());
        Assert.assertEquals(result.getBooks(), entity.getBooks());
    }

    /**
     * @generated
     */
    @Test
    public void getEditorialsTest() {
        List<EditorialEntity> list = editorialLogic.getEditorials();
        Assert.assertEquals(data.size(), list.size());
        for (EditorialEntity entity : list) {
            boolean found = false;
            for (EditorialEntity storedEntity : data) {
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
    public void getEditorialTest() {
        EditorialEntity entity = data.get(0);
        EditorialEntity resultEntity = editorialLogic.getEditorial(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getBooks(), resultEntity.getBooks());
    }

    /**
     * @generated
     */
    @Test
    public void deleteEditorialTest() {
        EditorialEntity entity = data.get(0);
        editorialLogic.deleteEditorial(entity.getId());
        EditorialEntity deleted = em.find(EditorialEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * @generated
     */
    @Test
    public void updateEditorialTest() {
        EditorialEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EditorialEntity pojoEntity = factory.manufacturePojo(EditorialEntity.class);
        pojoEntity.setId(entity.getId());
        
        editorialLogic.updateEditorial(pojoEntity);

        EditorialEntity resp = em.find(EditorialEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getBooks(), resp.getBooks());
    }
    
    /**
     * @generated
     */
    @Test
    public void addBookTest() {
        BookEntity entity = dataBooks.get(0);
        EditorialEntity editorialEntity = data.get(0);
        
        BookEntity response = editorialLogic.addBook(entity.getId(), editorialEntity.getId());
        Assert.assertNotNull(em);
        Assert.assertEquals(response.getName(), entity.getName());
        Assert.assertEquals(response.getId(), entity.getId());
        Assert.assertEquals(response.getImage(), entity.getImage());
        Assert.assertEquals(response.getIsbn(), entity.getIsbn());
    }
    
    /**
     * @generated
     */
    @Test
    public void removeBookTest() {
        BookEntity entity = dataBooks.get(0);
        EditorialEntity editorialEntity = data.get(0);
        Assert.assertNotNull(editorialEntity);
        Assert.assertNotNull(entity);
        editorialLogic.addBook(entity.getId(), editorialEntity.getId());
        editorialLogic.updateEditorial(editorialEntity);
        Assert.assertNotNull(editorialLogic.getBook(editorialEntity.getId(), entity.getId()));
        editorialLogic.removeBook(entity.getId(), editorialEntity.getId());
        Assert.assertNull(editorialLogic.getBook(editorialEntity.getId(), entity.getId()));
    }
    
    /**
     * @generated
     */
    @Test
    public void replaceBooksTest() {
        BookEntity entity = dataBooks.get(0);
        BookEntity entity2 = dataBooks.get(1);
        BookEntity entity3 = dataBooks.get(2);
        EditorialEntity editorialEntity = data.get(0);
        
        Assert.assertEquals(0, editorialEntity.getBooks().size());
        editorialLogic.addBook(entity.getId(), editorialEntity.getId());
        Assert.assertEquals(1, editorialLogic.getBooks(editorialEntity.getId()).size());
        editorialLogic.replaceBooks(new ArrayList<BookEntity>(), editorialEntity.getId());
        Assert.assertEquals(0, editorialLogic.getBooks(editorialEntity.getId()).size());
        
        List<BookEntity> bookList = new ArrayList<BookEntity>();
        bookList.add(entity2);
        bookList.add(entity3);
        
        editorialLogic.replaceBooks(bookList, editorialEntity.getId());
        BookEntity getBookResponse = editorialLogic.getBook(editorialEntity.getId(), entity.getId());
        BookEntity getBookResponse2 = editorialLogic.getBook(editorialEntity.getId(), entity2.getId());
        BookEntity getBookResponse3 = editorialLogic.getBook(editorialEntity.getId(), entity3.getId());
        Assert.assertNull(getBookResponse);
        Assert.assertNotNull(getBookResponse2);
        Assert.assertNotNull(getBookResponse3);
    }
    
    /**
     * @generated
     */
    @Test
    public void getBooksTest() {
        EditorialEntity editorialEntity = data.get(0);
        List<BookEntity> listado = new ArrayList<BookEntity>();

        editorialLogic.replaceBooks(listado, editorialEntity.getId());
        editorialLogic.updateEditorial(editorialEntity);

        Assert.assertEquals(0, editorialLogic.getBooks(editorialEntity.getId()).size());

        BookEntity entity = dataBooks.get(0);
        BookEntity entity2 = dataBooks.get(1);
        BookEntity entity3 = dataBooks.get(2);

        editorialLogic.addBook(entity.getId(), editorialEntity.getId());
        editorialLogic.addBook(entity2.getId(), editorialEntity.getId());
        editorialLogic.addBook(entity3.getId(), editorialEntity.getId());

        editorialLogic.updateEditorial(editorialEntity);
        Assert.assertEquals(3, editorialLogic.getBooks(editorialEntity.getId()).size());      
    }
    
    /**
     * @generated
     */
    @Test
    public void getBookTest() {
        EditorialEntity editorialEntity = data.get(0);
        List<BookEntity> listado = new ArrayList<BookEntity>();
        editorialLogic.replaceBooks(listado, editorialEntity.getId());
        editorialLogic.updateEditorial(editorialEntity);
        Assert.assertEquals(0, editorialLogic.getBooks(editorialEntity.getId()).size());
        
        BookEntity entity = dataBooks.get(0);
        BookEntity entity2 = dataBooks.get(1);
        BookEntity entity3 = dataBooks.get(2);
        
        editorialLogic.addBook(entity.getId(), editorialEntity.getId());
        editorialLogic.addBook(entity2.getId(), editorialEntity.getId());
        editorialLogic.addBook(entity3.getId(), editorialEntity.getId());
        
        Assert.assertEquals(entity, editorialLogic.getBook(editorialEntity.getId(), entity.getId()));
        Assert.assertEquals(entity2, editorialLogic.getBook(editorialEntity.getId(), entity2.getId()));
        Assert.assertEquals(entity3, editorialLogic.getBook(editorialEntity.getId(), entity3.getId()));
    }
}
