/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookbasico.functionalTest;

import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.services.BookService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Jhonatan
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookFunctionalIT {

    private static String URLRESOURCES = "src/main/webapp";
    private static String URLBASE = "http://localhost:8181/BookBasico.web/webresources";
    private static String PATHBOOK = "/books";
    private static WebDriver driver;
    private static int Ok = 200;
    private static int Created = 201;
    private static int OkWithoutContent = 204;
    private static String URLIMAGE = "http://www.seleniumhq.org/images/big-logo.png";
    
    // Mediante la anotacion @ArquillianResource se obtiene la URL de despliegue de la aplicacion
    @ArquillianResource
    URL deploymentURL;

    /**
     * Metodo que crea el empaquetamiento y el despliegue de la aplicacion
     * BookBasico
     *
     * @return Archive - war
     */
    @Deployment
    public static Archive<?> createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        WebArchive war = ShrinkWrap
                // Nombre del Proyecto "BookBasico.web" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "BookBasico.web.war")
                // Se agrega la dependencia a la logica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(resolver.artifact("co.edu.uniandes.csw.bookbasico:BookBasico.logic:1.0")
                        .resolveAsFiles())
                // Se agregan los compilados de los paquetes que se van a probar
                .addPackage(BookService.class.getPackage())
                // Se agrega contenido estatico: html y modulos de javascript. 
                .addAsWebResource(new File(URLRESOURCES, "index.html"))
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/").as(GenericArchive.class), "/src/", Filters.includeAll())
                // El archivo que contiene la configuracion a la base de datos. 
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias. 
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));

        return war;
    }
   
    public List<BookDTO> data = new ArrayList();

    @BeforeClass
    public static void setUp() {
        // Crea una instancia del driver de firefox sobre el que se ejecutara la aplicacion.
        driver = new FirefoxDriver();
    }

    @Before
    public void setUpTest() {        
        insertData();
        // El browser  va a la url de despliegue. Se ejecuta al inicar cada uno de los metodos de prueba indicados con @Test
        driver.get(deploymentURL.toString());
        
    }

    @AfterClass
    public static void tearDown() throws Exception {
        //Se ejecuta al terminar todos los metodos de prueba indicados con @Test Cierra el browser
        driver.quit();
    }
    
    

    private void insertData() {
        for (int i = 0; i < 3; i++) {            
            PodamFactory factory = new PodamFactoryImpl();
            BookDTO book = factory.manufacturePojo(BookDTO.class);
            book.setImage(URLIMAGE);
            Client cliente = ClientBuilder.newClient();
            Response response = cliente.target(URLBASE + PATHBOOK)
                .request()
                .post(Entity.entity(book, MediaType.APPLICATION_JSON));
            if (response.getStatus()== Ok)
                data.add(book);
        }
    }

    @After
    public void clearData() {
        for (int i = 0; i < data.size(); i++) {            
            PodamFactory factory = new PodamFactoryImpl();
            BookDTO book = factory.manufacturePojo(BookDTO.class);
            Client cliente = ClientBuilder.newClient();
            Response response = cliente.target(URLBASE + PATHBOOK + '/' + data.get(i).getId())
                .request()
                .delete();
            if (response.getStatus()== OkWithoutContent)
                data.remove(book);
        }
    }

    /**
     * El test crea un libro y luego verifica que el libro creado haya sido
     * publicado correctamente en la galeria.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    @RunAsClient
    public void t1createBook() throws InterruptedException {
        boolean success = false;
        Thread.sleep(2500);
        driver.findElement(By.id("create-book")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Cien anos de Soledad");
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("Realismo magico");
        driver.findElement(By.id("isbn")).clear();
        driver.findElement(By.id("isbn")).sendKeys("1025789845-13");
        driver.findElement(By.id("imageurl")).clear();
        driver.findElement(By.id("imageurl")).sendKeys("http://image.casadellibro.com/a/l/t0/08/9788497592208.jpg");
        driver.findElement(By.id("save-book")).click();
        Thread.sleep(2000);
        List<WebElement> books = driver.findElements(By.xpath("//div[contains(@ng-repeat,'record in records')]"));
        for (WebElement book : books) {
            List<WebElement> captions = book.findElements(By.xpath("div[contains(@class, 'col-md-4')]/div[contains(@class, 'caption')]/p"));
            if (captions.get(0).getText().contains("Cien anos de Soledad") && captions.get(1).getText().contains("Realismo magico")
                    && captions.get(2).getText().contains("1025789845-13")) {
                success = true;
            }
        }
        assertTrue(success);
        Thread.sleep(1000);
    }

    /**
     * El test edita un libro ya creado y luego verifica que el libro editado
     * haya sido publicado correctamente en la galeria.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    @RunAsClient
    public void t2EditBook() throws InterruptedException {
        Thread.sleep(1500);
        boolean success = false;
        String newDescription = "Nueva descripcion";
        String newIsbn = "5555500000-13";
        driver.findElement(By.id("-edit-btn")).click(); //Hace click en el primer elemento edit-btn encontrado
        Thread.sleep(1000);
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys(newDescription);
        driver.findElement(By.id("isbn")).clear();
        driver.findElement(By.id("isbn")).sendKeys(newIsbn);
        driver.findElement(By.id("save-book")).click();
        Thread.sleep(1000);
        List<WebElement> books = driver.findElements(By.xpath("//div[contains(@ng-repeat,'record in records')]"));
        for (WebElement book : books) {
            List<WebElement> captions = book.findElements(By.xpath("div[contains(@class, 'col-md-4')]/div[contains(@class, 'caption')]/p"));
            if (captions.get(1).getText().contains(newDescription)
                    && captions.get(2).getText().contains(newIsbn)) {
                success = true;
                break;
            }
        }
        assertTrue(success);
    }

    /**
     * El test elimina un libro de la galeria. Luego, verifica que el libro
     * eliminado haya sido removido correctamente de la misma.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    @RunAsClient
    public void t3DeleteBook() throws InterruptedException, IOException {
        Thread.sleep(1500);
        boolean success = false;
        Client cliente = ClientBuilder.newClient();
        Response response = cliente.target(URLBASE + PATHBOOK)
                .request().get();
        String listBook = response.readEntity(String.class);
        List<BookDTO> listBookTest = new ObjectMapper().readValue(listBook, List.class);
        driver.findElement(By.id("-delete-btn")).click();// Elimina el primer elemento con encontrado id -delete-btn 
        Thread.sleep(1000);
        List<WebElement> books = driver.findElements(By.xpath("//div[contains(@ng-repeat,'record in records')]"));
        if (books.size() == (listBookTest.size()-1)) {
            success = true;
        }
        assertTrue(success);
    }

}
