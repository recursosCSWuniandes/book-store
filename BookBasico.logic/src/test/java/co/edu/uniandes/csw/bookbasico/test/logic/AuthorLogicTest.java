package co.edu.uniandes.csw.bookbasico.test.logic;

import co.edu.uniandes.csw.bookbasico.ejbs.BookLogic;
import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;

import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
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
public class AuthorLogicTest {
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
    @Inject
    private IAuthorLogic authorLogic;

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
        em.createQuery("delete from AuthorEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<BookEntity> dataBooks = new ArrayList<BookEntity>();
    
    /**
     * @generated
     */
    private List<AuthorEntity> data = new ArrayList<AuthorEntity>();

    /**
     * @generated
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            AuthorEntity entity = factory.manufacturePojo(AuthorEntity.class);
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
    public void createAuthorsTest() {
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity entity = factory.manufacturePojo(AuthorEntity.class);
        
        AuthorEntity result = authorLogic.createAuthor(entity);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), entity.getName());
        Assert.assertEquals(result.getBooks(), entity.getBooks());
        Assert.assertEquals(result.getBirthDate(), entity.getBirthDate());
    }

    /**
     * @generated
     */
    @Test
    public void getAuthorsTest() {
        List<AuthorEntity> list = authorLogic.getAuthors();
        Assert.assertEquals(data.size(), list.size());
        for (AuthorEntity entity : list) {
            boolean found = false;
            for (AuthorEntity storedEntity : data) {
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
    public void getAuthorTest() {
        AuthorEntity entity = data.get(0);
        AuthorEntity resultEntity = authorLogic.getAuthor(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getBooks(), resultEntity.getBooks());
    }

    /**
     * @generated
     */
    @Test
    public void deleteAuthorTest() {
        AuthorEntity entity = data.get(0);
        authorLogic.deleteAuthor(entity.getId());
        AuthorEntity deleted = em.find(AuthorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * @generated
     */
    @Test
    public void updateAuthorTest() {
        AuthorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity pojoEntity = factory.manufacturePojo(AuthorEntity.class);
        pojoEntity.setId(entity.getId());
        
        authorLogic.updateAuthor(pojoEntity);

        AuthorEntity resp = em.find(AuthorEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }
    
    /**
     * @generated
     */
    @Test
    public void addBookTest() {
        BookEntity entity = dataBooks.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity authorEntity = factory.manufacturePojo(AuthorEntity.class);
        authorEntity = authorLogic.createAuthor(authorEntity);
        
        try{
            BookEntity response = authorLogic.addBook(entity.getId(), authorEntity.getId());
            Assert.assertNotNull(em);
            Assert.assertEquals(response.getName(), entity.getName());
            Assert.assertEquals(response.getId(), entity.getId());
            Assert.assertEquals(response.getImage(), entity.getImage());
            Assert.assertEquals(response.getIsbn(), entity.getIsbn());
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }
    }
    
    /**
     * @generated
     */
    @Test
    public void removeBookTest() {
        try{
            BookEntity entity = dataBooks.get(0);
            AuthorEntity authorEntity = data.get(0);
            Assert.assertNotNull(authorEntity);
            Assert.assertNotNull(entity);
            authorLogic.addBook(entity.getId(), authorEntity.getId());
            Assert.assertNotNull(authorLogic.getBook(authorEntity.getId(), entity.getId()));
            authorLogic.removeBook(entity.getId(), authorEntity.getId());
            Assert.assertNull(authorLogic.getBook(authorEntity.getId(), entity.getId()));
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }
    }
    
    /**
     * @generated
     */
    @Test
    public void replaceBooksTest() {
        
        
        BookEntity entity = dataBooks.get(0);
        BookEntity entity2 = dataBooks.get(1);
        BookEntity entity3 = dataBooks.get(2);
        AuthorEntity authorEntity = data.get(0);
        
        try{
            Assert.assertEquals(0, authorEntity.getBooks().size());
            authorLogic.addBook(entity.getId(), authorEntity.getId());
            authorLogic.updateAuthor(authorEntity);
            Assert.assertEquals(1, authorLogic.getBooks(authorEntity.getId()).size());
            List<BookEntity> bookList = new ArrayList<BookEntity>();
            bookList.add(entity2);
            bookList.add(entity3);
            authorLogic.replaceBooks(bookList, authorEntity.getId());
            authorLogic.updateAuthor(authorEntity);
            BookEntity getBookResponse = authorLogic.getBook(authorEntity.getId(), entity.getId());
            BookEntity getBookResponse2 = authorLogic.getBook(authorEntity.getId(), entity2.getId());
            BookEntity getBookResponse3 = authorLogic.getBook(authorEntity.getId(), entity3.getId());
            Assert.assertNull(getBookResponse);
            Assert.assertNotNull(getBookResponse2);
            Assert.assertNotNull(getBookResponse3);
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }
    }
    
    /**
     * @generated
     */
    @Test
    public void getBooksTest() {
        AuthorEntity authorEntity = data.get(0);
        List<BookEntity> listado = new ArrayList<BookEntity>();
        
        try{
            authorLogic.replaceBooks(listado, authorEntity.getId());
            authorLogic.updateAuthor(authorEntity);
        
            Assert.assertEquals(0, authorLogic.getBooks(authorEntity.getId()).size());

            BookEntity entity = dataBooks.get(0);
            BookEntity entity2 = dataBooks.get(1);
            BookEntity entity3 = dataBooks.get(2);
            
            authorLogic.addBook(entity.getId(), authorEntity.getId());
            authorLogic.addBook(entity2.getId(), authorEntity.getId());
            authorLogic.addBook(entity3.getId(), authorEntity.getId());
            
            authorLogic.updateAuthor(authorEntity);
            Assert.assertEquals(authorLogic.getBooks(authorEntity.getId()).size(), 3);
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }        
    }
    
    /**
     * @generated
     */
    @Test
    public void getBookTest() {
        AuthorEntity authorEntity = data.get(0);
        List<BookEntity> listado = new ArrayList<BookEntity>();
        authorEntity.setBooks(listado);
        authorLogic.updateAuthor(authorEntity);
        Assert.assertEquals(0, authorLogic.getBooks(authorEntity.getId()).size());
        
        BookEntity entity = dataBooks.get(0);
        BookEntity entity2 = dataBooks.get(1);
        BookEntity entity3 = dataBooks.get(2);
        
        try{
            authorLogic.addBook(entity.getId(), authorEntity.getId());
            authorLogic.addBook(entity2.getId(), authorEntity.getId());
            authorLogic.addBook(entity3.getId(), authorEntity.getId());
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }
        
        Assert.assertEquals(entity, authorLogic.getBook(authorEntity.getId(), entity.getId()));
        Assert.assertEquals(entity2, authorLogic.getBook(authorEntity.getId(), entity2.getId()));
        Assert.assertEquals(entity3, authorLogic.getBook(authorEntity.getId(), entity3.getId()));
    }
}
