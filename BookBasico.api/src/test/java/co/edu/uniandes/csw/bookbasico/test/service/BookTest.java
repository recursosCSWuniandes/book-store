package co.edu.uniandes.csw.bookbasico.test.service;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.dtos.ReviewDTO;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.services.BookService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class BookTest {

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private final String bookPath = "books";
    private final static List<BookDTO> oraculo = new ArrayList<>();
    private final String authorPath = "authors";
    private final static List<AuthorDTO> oraculoAuthors = new ArrayList<>();
    private WebTarget target;
    private final String apiPath = "api";
    private final String username = System.getenv("USERNAME_USER");
    private final String password = System.getenv("PASSWORD_USER");

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                // Nombre del Proyecto "BookBasico.api" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "BookBasico.api.war")
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw.bookbasico:BookBasico.logic:1.0-SNAPSHOT")
                        .withTransitivity().asFile())
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw:AuthService:0.0.4-SNAPSHOT")
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(BookService.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }
    
    private WebTarget createWebTarget() {
        ClientConfig config = new ClientConfig();
        config.register(LoggingFilter.class);
        return ClientBuilder.newClient(config).target(deploymentURL.toString()).path(apiPath);
    }

    @BeforeClass
    public static void setUp() {
        insertData();
    }

    public static void insertData() {
        for (int i = 0; i < 5; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            BookDTO book = factory.manufacturePojo(BookDTO.class);
            book.setId(i + 1L);
            List<ReviewDTO> reviewList = new ArrayList<>();
            for (int j = 0; j < 5; j++)
            {
                ReviewDTO review = factory.manufacturePojo(ReviewDTO.class);
                review.setId(i + 1L);
                reviewList.add(review);
            }
            
            book.setReviews(reviewList);
            
            oraculo.add(book);
            
            AuthorDTO author = factory.manufacturePojo(AuthorDTO.class);
            author.setId(i + 1L);
            oraculoAuthors.add(author);

            
        }
    }
    
    public Cookie login(String username, String password) {
        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(password);
        user.setRememberMe(true);
        Response response = target.path("users").path("login").request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Ok) {
            return response.getCookies().get(JWT.cookieName);
        } else {
            return null;
        }
    }

    @Before
    public void setUpTest() {
        target = createWebTarget();
    }

    @Test
    public void t1CreateBookTest() throws IOException {
        BookDTO book = oraculo.get(0);
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookDTO  bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(book.getId(), bookTest.getId());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getImage(), bookTest.getImage());
        Assert.assertEquals(Created, response.getStatus());
    }
    
    @Test
    public void t2GetBookById() {
        Cookie cookieSessionId = login(username, password);
        BookDTO bookTest = target.path(bookPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(BookDTO.class);
        Assert.assertEquals(bookTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(bookTest.getName(), oraculo.get(0).getName());
        Assert.assertEquals(bookTest.getDescription(), oraculo.get(0).getDescription());
        Assert.assertEquals(bookTest.getIsbn(), oraculo.get(0).getIsbn());
        Assert.assertEquals(bookTest.getImage(), oraculo.get(0).getImage());
    }
    
    @Test
    public void t3BookTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId).get();
        String listBook = response.readEntity(String.class);
        List<BookDTO> listBookTest = new ObjectMapper().readValue(listBook, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listBookTest.size());
    }
    
    @Test
    public void t4UpdateBookTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        BookDTO book = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BookDTO bookChanged = factory.manufacturePojo(BookDTO.class);
        book.setName(bookChanged.getName());
        book.setDescription(bookChanged.getDescription());
        book.setIsbn(bookChanged.getIsbn());
        book.setImage(bookChanged.getImage());
        Response response = target.path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookDTO bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getImage(), bookTest.getImage());

    }
    
    @Test
    public void t5AddAuthorBookTest() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        
        AuthorDTO author = oraculoAuthors.get(0);
        BookDTO book = oraculo.get(0);
        
        
        Response response = target.path(authorPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(author, MediaType.APPLICATION_JSON));
        
        AuthorDTO authorTest = (AuthorDTO) response.readEntity(AuthorDTO.class);
        Assert.assertEquals(author.getId(), authorTest.getId());
        Assert.assertEquals(author.getName(), authorTest.getName());
        Assert.assertEquals(Created, response.getStatus());
        
        response = target.path(bookPath).path(book.getId().toString())
                .path(authorPath).path(author.getId().toString())
                .request().cookie(cookieSessionId)
                .post(Entity.entity(author, MediaType.APPLICATION_JSON));
        
        authorTest = (AuthorDTO) response.readEntity(AuthorDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(author.getId(), authorTest.getId());
        Assert.assertEquals(author.getName(), authorTest.getName());
    }
    
    @Test
    public void t6GetAuthorTestList() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        BookDTO book = oraculo.get(0);
        
        Response response = target.path(bookPath)
                .path(book.getId().toString())
                .path(authorPath)
                .request().cookie(cookieSessionId).get();
        
        String authorList = response.readEntity(String.class);
        List<AuthorDTO> authorListTest = new ObjectMapper().readValue(authorList, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, authorListTest.size());
    }
    
    @Test
    public void t7GetAuthorTest() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        AuthorDTO author = oraculoAuthors.get(0);
        BookDTO book = oraculo.get(0);
        
        AuthorDTO authorTest = target.path(bookPath)
                .path(book.getId().toString()).path(authorPath)
                .path(author.getId().toString())
                .request().cookie(cookieSessionId).get(AuthorDTO.class);
                
        Assert.assertEquals(author.getId(), authorTest.getId());
        Assert.assertEquals(author.getName(), authorTest.getName());
    }
    
    @Test
    public void t8RemoveAuthorBookTest() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        
        AuthorDTO author = oraculoAuthors.get(0);
        BookDTO book = oraculo.get(0);
        
        Response response = target.path(bookPath).path(book.getId().toString())
                .path(authorPath).path(author.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
    
    @Test
    public void t9DeleteBookTest() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        BookDTO book = oraculo.get(0);
        Response response = target.path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    } 
    

}
