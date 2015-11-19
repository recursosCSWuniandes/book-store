package co.edu.uniandes.csw.bookbasico.serviceTest;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.providers.EJBExceptionMapper;
import co.edu.uniandes.csw.bookbasico.services.BookService;
import co.edu.uniandes.csw.bookbasico.shiro.ApiKeyProperties;
import java.io.File;
import java.io.IOException;
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
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
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

    private static final String bookPath = "books";
    private static final int Ok = Status.OK.getStatusCode();
    private static final int Created = Status.CREATED.getStatusCode();
    private static final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private static List<BookDTO> oraculo = new ArrayList<>();
    private static Cookie cookieSessionId;

    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                // Nombre del Proyecto "Bookbasico.web" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "BookBasico.web.war")
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw.bookbasico:BookBasico.logic:1.0-SNAPSHOT")
                        .withTransitivity().asFile())
                .addAsLibraries(Maven.resolver()
                        .resolve("co.edu.uniandes.csw:AuthService:0.0.4-SNAPSHOT")
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(BookService.class.getPackage())
                .addPackage(EJBExceptionMapper.class.getPackage())
                .addPackage(ApiKeyProperties.class.getPackage())
                .addPackage(UserDTO.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos. 
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias. 
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    private static WebTarget createWebTarget() {
        String baseUrl = "http://localhost:8181/BookBasico.web/api";
        ClientConfig config = new ClientConfig();
        config.register(LoggingFilter.class);
        return ClientBuilder.newClient(config).target(baseUrl);
    }

    @BeforeClass
    public static void setUp() {
        insertData();
        cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
    }

    public static void insertData() {
        for (int i = 0; i < 5; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            BookDTO book = factory.manufacturePojo(BookDTO.class);
            oraculo.add(book);
        }
    }

    public static Cookie login(String username, String password) {
        WebTarget target = createWebTarget();
        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(password);
        user.setRememberMe(true);
        Response response = target.path("users").path("login").request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        UserDTO foundUser = (UserDTO) response.readEntity(UserDTO.class);

        if (foundUser != null && response.getStatus() == Ok) {
            return response.getCookies().get("JSESSIONID");
        } else {
            return null;
        }
    }

    @Test
    public void t1CreateBookService() throws IOException {
        BookDTO book = oraculo.get(0);
        WebTarget target = createWebTarget();
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookDTO bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getId(), bookTest.getId());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    public void t2GetBookById() {
        WebTarget target = createWebTarget();
        BookDTO bookTest = target.path(bookPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(BookDTO.class);
        Assert.assertEquals(bookTest.getName(), oraculo.get(0).getName());
    }

    @Test
    public void t3GetCountryService() throws IOException {
        WebTarget target = createWebTarget();
        Response response = target.path(bookPath)
                .request().cookie(cookieSessionId).get();
        String listBook = response.readEntity(String.class);
        List<BookDTO> listBookTest = new ObjectMapper().readValue(listBook, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listBookTest.size());
    }

    @Test
    public void t4UpdateBookService() throws IOException {
        BookDTO book = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BookDTO bookChanged = factory.manufacturePojo(BookDTO.class);
        book.setName(bookChanged.getName());
        book.setIsbn(bookChanged.getIsbn());
        WebTarget target = createWebTarget();
        Response response = target.path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookDTO bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());

    }

    @Test
    public void t5DeleteCountryService() {
        WebTarget target = createWebTarget();
        BookDTO book = oraculo.get(0);
        Response response = target.path(bookPath).path(book.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }

}
