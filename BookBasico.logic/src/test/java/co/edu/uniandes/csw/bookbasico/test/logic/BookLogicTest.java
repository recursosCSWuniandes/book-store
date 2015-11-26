package co.edu.uniandes.csw.bookbasico.test.logic;

import co.edu.uniandes.csw.bookbasico.ejbs.BookLogic;
import co.edu.uniandes.csw.bookbasico.api.IBookLogic;
import co.edu.uniandes.csw.bookbasico.api.IAuthorLogic;

import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.entities.AuthorEntity;
import co.edu.uniandes.csw.bookbasico.entities.ReviewEntity;
import co.edu.uniandes.csw.bookbasico.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookbasico.persistence.BookPersistence;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

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
            PodamFactory factory = new PodamFactoryImpl();
            BookEntity entity = factory.manufacturePojo(BookEntity.class);
            
            List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();
            
            for (int j = 0; j < 3; j++)
            {
                ReviewEntity reviewEntity = factory.manufacturePojo(ReviewEntity.class);
                reviews.add(reviewEntity);
            }
            
            entity.setReviews(reviews);
            
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * @generated
     */
    @Test
    public void createBookTest() {
        PodamFactory factory = new PodamFactoryImpl();
        BookEntity entity = factory.manufacturePojo(BookEntity.class);
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
        PodamFactory factory = new PodamFactoryImpl();
        BookEntity pojoEntity = factory.manufacturePojo(BookEntity.class);
        pojoEntity.setId(entity.getId());

        bookLogic.updateBook(pojoEntity);

        BookEntity resp = em.find(BookEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getIsbn(), resp.getIsbn());
        Assert.assertEquals(pojoEntity.getImage(), resp.getImage());
        Assert.assertEquals(pojoEntity.getDescription(), resp.getDescription());
    }

    /**
     * @generated
     */
    @Test
    public void addAuthorTest() {
        BookEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity authorEntity = factory.manufacturePojo(AuthorEntity.class);
        authorEntity = authorLogic.createAuthor(authorEntity);

        try{
            AuthorEntity response = bookLogic.addAuthor(authorEntity.getId(), entity.getId());
            Assert.assertNotNull(em);
            Assert.assertEquals(response.getName(), authorEntity.getName());
            Assert.assertEquals(response.getId(), authorEntity.getId());
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
    public void getAuthorTest() {
        BookEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity authorEntity = factory.manufacturePojo(AuthorEntity.class);
        authorEntity = authorLogic.createAuthor(authorEntity);

        try{
            bookLogic.addAuthor(authorEntity.getId(), entity.getId());
            AuthorEntity getAuthorResponse = bookLogic.getAuthor(entity.getId(), authorEntity.getId());
            Assert.assertEquals(authorEntity.getName(), getAuthorResponse.getName());
            Assert.assertEquals(authorEntity.getId(), getAuthorResponse.getId());

            bookLogic.updateBook(entity);

            AuthorEntity listResponse = entity.getAuthors().get(entity.getAuthors().size() - 1);
            Assert.assertEquals(listResponse.getName(), authorEntity.getName());
            Assert.assertEquals(listResponse.getId(), authorEntity.getId());
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
    public void removeAuthorTest() {
        BookEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity authorEntity = factory.manufacturePojo(AuthorEntity.class);
        authorEntity = authorLogic.createAuthor(authorEntity);

        try{
            bookLogic.addAuthor(authorEntity.getId(), entity.getId());
            bookLogic.updateBook(entity);
            AuthorEntity getAuthorResponse = bookLogic.getAuthor(entity.getId(), authorEntity.getId());
            Assert.assertNotNull(getAuthorResponse);
            bookLogic.removeAuthor(authorEntity.getId(), entity.getId());
            bookLogic.updateBook(entity);
            getAuthorResponse = bookLogic.getAuthor(entity.getId(), authorEntity.getId());
            Assert.assertNull(getAuthorResponse);
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
    public void replaceAuthorsTest() {
        BookEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorEntity authorEntity = factory.manufacturePojo(AuthorEntity.class);

        AuthorEntity authorEntity2 = factory.manufacturePojo(AuthorEntity.class);

        AuthorEntity authorEntity3 = factory.manufacturePojo(AuthorEntity.class);

        authorEntity = authorLogic.createAuthor(authorEntity);
        authorEntity2 = authorLogic.createAuthor(authorEntity2);
        authorEntity3 = authorLogic.createAuthor(authorEntity3);

        try{
            bookLogic.addAuthor(authorEntity.getId(), entity.getId());
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }

        AuthorEntity getAuthorResponse = bookLogic.getAuthor(entity.getId(), authorEntity.getId());
        AuthorEntity getAuthorResponse2 = bookLogic.getAuthor(entity.getId(), authorEntity2.getId());
        AuthorEntity getAuthorResponse3 = bookLogic.getAuthor(entity.getId(), authorEntity3.getId());
        Assert.assertNotNull(getAuthorResponse);
        Assert.assertNull(getAuthorResponse2);
        Assert.assertNull(getAuthorResponse3);

        try{
            List<AuthorEntity> authorList = new ArrayList<AuthorEntity>() {};
            authorList.add(authorEntity2);
            authorList.add(authorEntity3);
            bookLogic.replaceAuthors( authorList, entity.getId());
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }

        getAuthorResponse = bookLogic.getAuthor(entity.getId(), authorEntity.getId());
        getAuthorResponse2 = bookLogic.getAuthor(entity.getId(), authorEntity2.getId());
        getAuthorResponse3 = bookLogic.getAuthor(entity.getId(), authorEntity3.getId());
        Assert.assertNull(getAuthorResponse);
        Assert.assertNotNull(getAuthorResponse2);
        Assert.assertNotNull(getAuthorResponse3);
    }

    /**
     * @generated
     */
    @Test
    public void getAuthorsTest() {
        BookEntity entity = data.get(0);
        List<AuthorEntity> listado = new ArrayList<AuthorEntity>();
        entity.setAuthors(listado);
        bookLogic.updateBook(entity);

        Assert.assertEquals(entity.getAuthors().size(), 0);

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName("newauthor");

        AuthorEntity authorEntity2 = new AuthorEntity();
        authorEntity2.setName("newauthor2");

        AuthorEntity authorEntity3 = new AuthorEntity();
        authorEntity3.setName("newauthor3");

        authorEntity = authorLogic.createAuthor(authorEntity);
        authorEntity2 = authorLogic.createAuthor(authorEntity2);
        authorEntity3 = authorLogic.createAuthor(authorEntity3);

        try{
            bookLogic.addAuthor(authorEntity.getId(), entity.getId());
            bookLogic.addAuthor(authorEntity2.getId(), entity.getId());
            bookLogic.addAuthor(authorEntity3.getId(), entity.getId());
        }
        catch (BusinessLogicException bslexception)
        {
            Assert.fail("No debe haber excepción");
        }

        entity = bookLogic.updateBook(entity);
        Assert.assertEquals(entity.getAuthors().size(), 3);
    }
}
