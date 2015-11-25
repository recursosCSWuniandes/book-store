package co.edu.uniandes.csw.bookbasico.serviceTest;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import co.edu.uniandes.csw.bookbasico.services.EditorialService;
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
public class EditorialTest {

    private static final String editorialPath = "editorials";
    private static final int Ok = Status.OK.getStatusCode();
    private static final int Created = Status.CREATED.getStatusCode();
    private static final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();
    private static List<EditorialDTO> oraculo = new ArrayList<>();
    private static Cookie cookieSessionId;
    private static String token;

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
                .addPackage(EditorialService.class.getPackage())
                .addPackage(ApiKeyProperties.class.getPackage())
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
            EditorialDTO editorial = factory.manufacturePojo(EditorialDTO.class);            
            editorial.setId(i+1L);
            oraculo.add(editorial);
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

        if (response.getStatus() == Ok) {
            return response.getCookies().get("jwt-token");
        } else {
            return null;
        }
    }

    @Test
    public void t1CreateEditorialService() throws IOException {
        EditorialDTO editorial = oraculo.get(0);
        WebTarget target = createWebTarget();
        Response response = target.path(editorialPath)
                .request().header("Authorization", token).cookie(cookieSessionId)
                .post(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        EditorialDTO editorialTest = (EditorialDTO) response.readEntity(EditorialDTO.class);
        Assert.assertEquals(editorial.getName(), editorialTest.getName());
        Assert.assertEquals(editorial.getId(), editorialTest.getId());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    public void t2GetEditorialById() {
        WebTarget target = createWebTarget();
        EditorialDTO editorialTest = target.path(editorialPath)
                .path(oraculo.get(0).getId().toString())
                .request().cookie(cookieSessionId).get(EditorialDTO.class);
        Assert.assertEquals(editorialTest.getName(), oraculo.get(0).getName());
    }

    @Test
    public void t3GetEditorialService() throws IOException {
        WebTarget target = createWebTarget();
        Response response = target.path(editorialPath)
                .request().cookie(cookieSessionId).get();
        String listEditorial = response.readEntity(String.class);
        List<EditorialDTO> listEditorialTest = new ObjectMapper().readValue(listEditorial, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listEditorialTest.size());
    }

    @Test
    public void t4UpdateEditorialService() throws IOException {
        EditorialDTO editorial = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EditorialDTO editorialChanged = factory.manufacturePojo(EditorialDTO.class);
        editorial.setName(editorialChanged.getName());
        WebTarget target = createWebTarget();
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .request().cookie(cookieSessionId).put(Entity.entity(editorial, MediaType.APPLICATION_JSON));
        EditorialDTO editorialTest = (EditorialDTO) response.readEntity(EditorialDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(editorial.getName(), editorialTest.getName());

    }

    @Test
    public void t5DeleteEditorialService() {
        WebTarget target = createWebTarget();
        EditorialDTO editorial = oraculo.get(0);
        Response response = target.path(editorialPath).path(editorial.getId().toString())
                .request().cookie(cookieSessionId).delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }

}
