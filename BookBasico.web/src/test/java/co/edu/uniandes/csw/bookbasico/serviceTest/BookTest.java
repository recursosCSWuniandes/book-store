/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookbasico.serviceTest;

import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.providers.EJBExceptionMapper;
import co.edu.uniandes.csw.bookbasico.services.BookService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Jhonatan
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class BookTest {

    public static String URLRESOURCES = "src/main/webapp";
    public static String URLBASE = "http://localhost:8181/BookBasico.web/webresources";
    public static String PATHBOOK = "/books";
    public static int Ok = 200;
    public static int Created = 201;
    public static int OkWithoutContent = 204;
    public static List<BookDTO> oraculo = new ArrayList<>();

    @Deployment
    public static Archive<?> createDeployment() {

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        WebArchive war = ShrinkWrap
                // Nombre del Proyecto "Bookbasico.web" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "BookBasico.web.war")
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(resolver.artifact("co.edu.uniandes.csw.bookbasico:BookBasico.logic:1.0")
                        .resolveAsFiles())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(BookService.class.getPackage())
                .addPackage(EJBExceptionMapper.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos. 
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias. 
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));

        return war;
    }
    
    @BeforeClass
    public static void setUp() {
        for (int i = 0; i < 5; i++) {
            PodamFactory factory = new PodamFactoryImpl();
            BookDTO book = factory.manufacturePojo(BookDTO.class);
            oraculo.add(book);
        }
    }


    @Test
    @RunAsClient
    public void t1CreateBookService() throws IOException {
        BookDTO book = oraculo.get(0);
        Client cliente = ClientBuilder.newClient();
        Response response = cliente.target(URLBASE + PATHBOOK)
                .request()
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookDTO bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());
        Assert.assertEquals(book.getId(), bookTest.getId());
        Assert.assertEquals(Created, response.getStatus());
    }

    @Test
    @RunAsClient
    public void t2GetBookById() {
        Client cliente = ClientBuilder.newClient();
        BookDTO bookTest = cliente.target(URLBASE + PATHBOOK).path("/" + oraculo.get(0).getId())
                .request().get(BookDTO.class);
        Assert.assertEquals(bookTest.getName(), oraculo.get(0).getName());
    }

    @Test
    @RunAsClient
    public void t3GetCountryService() throws IOException {
        Client cliente = ClientBuilder.newClient();
        Response response = cliente.target(URLBASE + PATHBOOK)
                .request().get();
        String listBook = response.readEntity(String.class);
        List<BookDTO> listBookTest = new ObjectMapper().readValue(listBook, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(1, listBookTest.size());
    }

    @Test
    @RunAsClient
    public void t4UpdateBookService() throws IOException {
        BookDTO book = oraculo.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BookDTO bookChanged = factory.manufacturePojo(BookDTO.class);
        book.setName(bookChanged.getName());
        book.setIsbn(bookChanged.getIsbn());
        Client cliente = ClientBuilder.newClient();
        Response response = cliente.target(URLBASE + PATHBOOK).path("/" + book.getId())
                .request().put(Entity.entity(book, MediaType.APPLICATION_JSON));
        BookDTO bookTest = (BookDTO) response.readEntity(BookDTO.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(book.getName(), bookTest.getName());
        Assert.assertEquals(book.getIsbn(), bookTest.getIsbn());

    }

    @Test
    @RunAsClient
    public void t5DeleteCountryService() {
        Client cliente = ClientBuilder.newClient();
        BookDTO book = oraculo.get(0);
        Response response = cliente.target(URLBASE + PATHBOOK).path("/" + book.getId())
                .request().delete();
        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
    
    }
