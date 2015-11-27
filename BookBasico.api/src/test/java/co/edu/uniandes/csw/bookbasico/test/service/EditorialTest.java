package co.edu.uniandes.csw.bookbasico.test.service;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.services.EditorialService;
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
public class EditorialTest {

    private final String editorialPath = "editorials";
    private final String bookPath = "books";
    private final String authorPath = "authors";
    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private static List<EditorialDTO> oraculo = new ArrayList<>();
    private static List<BookDTO> oraculoBooks = new ArrayList<>();
    private final String username = System.getenv("USERNAME_USER");
    private final String password = System.getenv("PASSWORD_USER");
    private WebTarget target;
    private final String apiPath = "api";

    @ArquillianResource
    private URL deploymentURL;

    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                // Nombre del Proyecto "Bookbasico.web" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "BookBasico.api.war")
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw.bookbasico:BookBasico.logic:1.0-SNAPSHOT")
                        .withTransitivity().asFile())
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw:AuthService:0.0.4-SNAPSHOT")
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(EditorialService.class.getPackage())
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
            EditorialDTO editorial = factory.manufacturePojo(EditorialDTO.class);
            editorial.setId(i + 1L);
            oraculo.add(editorial);
            
            BookDTO book = factory.manufacturePojo(BookDTO.class);
            book.setId(i + 1L);
            oraculoBooks.add(book);
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
    public void t1CreateEditorialService() throws IOException {
        EditorialDTO editorial = oraculo.get(0);
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(editorialPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        EditorialDTO editorialTest = (EditorialDTO) response.readEntity(EditorialDTO.class);
        Assert.assertEquals(editorial.getName(), editorialTest.getName());
        Assert.assertEquals(editorial.getId(), editorialTest.getId());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    public void t2GetEditorialById() {
        Cookie cookieSessionId = login(username, password);
        EditorialDTO editorialTest = target.path(editorialPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(EditorialDTO.class);
        Assert.assertEquals(editorialTest.getName(), oraculo.get(0).getName());
    }

    @Test
    public void t3GetEditorialService() throws IOException {
        Cookie cookieSessionId = login(username, password);
        Response response = target.path(editorialPath)
                .request().cookie(cookieSessionId).get();
        String listEditorial = response.readEntity(String.class);
        List<EditorialDTO> listEditorialTest = new ObjectMapper().readValue(listEditorial, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listEditorialTest.size());
    }

    @Test
    public void t4UpdateEditorialService() throws IOException {
        Cookie cookieSessionId = login(username, password);
        EditorialDTO editorial = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EditorialDTO editorialChanged = factory.manufacturePojo(EditorialDTO.class);
        editorial.setName(editorialChanged.getName());
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        EditorialDTO editorialTest = (EditorialDTO) response.readEntity(EditorialDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(editorial.getName(), editorialTest.getName());

    }
    
    @Test
    public void t5AddBookEditorialService() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        
        EditorialDTO editorial = oraculo.get(0);
        BookDTO book = oraculoBooks.get(0);
        
        // Debemos crear el book y luego agregarlos
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        
        BookDTO bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getImage(), bookTest.getImage());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(Created, response.getStatus());
        
        response = target.path(editorialPath).path(editorial.getId().toString())
                .path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId)
                .post(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        
        bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getDescription(), bookTest.getDescription());
        Assert.assertEquals(book.getImage(), bookTest.getImage());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
    }
    
    @Test
    public void t6GetBooksService() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        EditorialDTO editorial = oraculo.get(0);
        
        Response response = target.path(editorialPath)
                .path(editorial.getId().toString())
                .path(bookPath)
                .request().cookie(cookieSessionId).get();
        
        String listBooks = response.readEntity(String.class);
        List<BookDTO> listBooksTest = new ObjectMapper().readValue(listBooks, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listBooksTest.size());
    }
    
    @Test
    public void t7GetBookService() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        EditorialDTO editorial = oraculo.get(0);
        BookDTO book = oraculoBooks.get(0);
        
        BookDTO bookTest = target.path(editorialPath)
                .path(editorial.getId().toString()).path(bookPath)
                .path(book.getId().toString())
                .request().cookie(cookieSessionId).get(BookDTO.class);
        
        Assert.assertEquals(bookTest.getName(), book.getName());
        Assert.assertEquals(bookTest.getImage(), book.getImage());
        Assert.assertEquals(bookTest.getDescription(), book.getDescription());
        Assert.assertEquals(bookTest.getIsbn(), book.getIsbn());
    }
    
    @Test
    public void t8RemoveBookAuthorService() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        
        EditorialDTO editorial = oraculo.get(0);
        BookDTO book = oraculoBooks.get(0);
        
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }

    @Test
    public void t9DeleteEditorialService() {
        Cookie cookieSessionId = login(username, password);
        EditorialDTO editorial = oraculo.get(0);
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
    
    @Test
    public void t10GetAuthorsService() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        EditorialDTO editorial = oraculo.get(0);
        
        Response response = target.path(editorialPath)
                .path(editorial.getId().toString())
                .path(authorPath)
                .request().cookie(cookieSessionId).get();
        
        String listAuthors = response.readEntity(String.class);
        List<AuthorDTO> listAuthorsTest = new ObjectMapper().readValue(listAuthors, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(0, listAuthorsTest.size());
    }

}
