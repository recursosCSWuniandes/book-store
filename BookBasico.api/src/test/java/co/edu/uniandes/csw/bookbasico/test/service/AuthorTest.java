package co.edu.uniandes.csw.bookbasico.test.service;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookbasico.services.AuthorService;
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
public class AuthorTest {

    private final String authorPath = "authors";
    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private static List<AuthorDTO> oraculo = new ArrayList<>();
    private WebTarget target;

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
                .addPackage(AuthorService.class.getPackage())
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
        String baseUrl = "http://localhost:8181/BookBasico.web/api";
        ClientConfig config = new ClientConfig();
        config.register(LoggingFilter.class);
        return ClientBuilder.newClient(config).target(baseUrl);
    }

    @BeforeClass
    public static void setUp() {
        insertData();
    }

    public static void insertData() {
        for (int i = 0; i < 5; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            AuthorDTO author = factory.manufacturePojo(AuthorDTO.class);
            author.setId(i + 1L);
            oraculo.add(author);
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
            return response.getCookies().get("jwt-token");
        } else {
            return null;
        }
    }

    @Before
    public void setUpTest(){
        target = createWebTarget();
    }

    @Test
    public void t1CreateAuthorService() throws IOException {
        AuthorDTO author = oraculo.get(0);
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        Response response = target.path(authorPath)
                .request().cookie(cookieSessionId)
                .post(Entity.entity(author, MediaType.APPLICATION_JSON));
        AuthorDTO authorTest = (AuthorDTO) response.readEntity(AuthorDTO.class);
        Assert.assertEquals(author.getName(), authorTest.getName());
        Assert.assertEquals(author.getId(), authorTest.getId());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    public void t2GetAuthorById() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        AuthorDTO authorTest = target.path(authorPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(AuthorDTO.class);
        Assert.assertEquals(authorTest.getName(), oraculo.get(0).getName());
    }

    @Test
    public void t3GetAuthorService() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        Response response = target.path(authorPath)
                .request().cookie(cookieSessionId).get();
        String listAuthor = response.readEntity(String.class);
        List<AuthorDTO> listAuthorTest = new ObjectMapper().readValue(listAuthor, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listAuthorTest.size());
    }

    @Test
    public void t4UpdateAuthorService() throws IOException {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        AuthorDTO author = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AuthorDTO authorChanged = factory.manufacturePojo(AuthorDTO.class);
        author.setName(authorChanged.getName());
        Response response = target.path(authorPath).path(author.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(author, MediaType.APPLICATION_JSON));
        AuthorDTO authorTest = (AuthorDTO) response.readEntity(AuthorDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(author.getName(), authorTest.getName());

    }

    @Test
    public void t5DeleteAuthorService() {
        Cookie cookieSessionId = login(System.getenv("USERNAME_USER"), System.getenv("PASSWORD_USER"));
        AuthorDTO author = oraculo.get(0);
        Response response = target.path(authorPath).path(author.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }

}