/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookbasico.functionalTest;


import co.edu.uniandes.csw.bookbasico.services.BookService;
import java.io.File;
import java.net.URL;
import java.util.List;
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

/**
 *
 * @author Jhonatan
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookFunctionalIT {
    
     public static String URLRESOURCES = "src/main/webapp";
     public static WebDriver driver;
     // Está variable contendrá la URL que utiliza arquillian para ejecutar el contenedor
    @ArquillianResource URL deploymentURL;
    
    @Deployment
    public static Archive<?> createDeployment(){
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        WebArchive war = ShrinkWrap
                // Nombre del Proyecto "SportClub.service" seguido de ".war". Debe ser el mismo nombre del proyecto web que contiene los javascript y los  servicios Rest
                .create(WebArchive.class, "BookBasico.web.war")
                // Se agrega la dependencia a la lógica con el nombre groupid:artefactid:version (GAV)
                .addAsLibraries(resolver.artifact("co.edu.uniandes.csw.bookbasico:BookBasico.logic:1.0")
                        .resolveAsFiles())
                // Se agregan los compilados de los paquetes que se van a probar
                .addPackage(BookService.class.getPackage())
                // Se agrega contenido estatico: html y módulos de javascript. 
                .addAsWebResource(new File(URLRESOURCES, "index.html"))
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(URLRESOURCES + "/src/").as(GenericArchive.class), "/src/", Filters.includeAll())                               
                // El archivo que contiene la configuracion a la base de datos. 
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injección de dependencias. 
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));

        return war;
    }
    
    
    @BeforeClass
    public static void setUp() {
        // Se crea una instancia del driver de firefox sobre el que se ejecutara la aplicación.
        driver = new FirefoxDriver();
    }
    
    
    @Before
    public void setUpTest() {
        // El browser  a esta url. Se ejecuta al inicar cada uno de los métodos de prueba indicados con @Test
        driver.get(deploymentURL + "index.html");
    }
    
     @AfterClass
    public static void tearDown() throws Exception {
        //Se ejecuta al terminar todos los métodos de prueba indicados con @Test Cierra el browser
        driver.quit();
    }
    
    @Test
    @RunAsClient
    public void t1createBook() throws InterruptedException {
        Thread.sleep(1500);
        boolean success = false;
        Thread.sleep(3000);
        driver.findElement(By.id("create-book")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Cien años de Soledad");
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("Realismo mágico");
        driver.findElement(By.id("isbn")).clear();
        driver.findElement(By.id("isbn")).sendKeys("1025789845-13");
        driver.findElement(By.id("image")).clear();
        driver.findElement(By.id("image")).sendKeys("http://image.casadellibro.com/a/l/t0/08/9788497592208.jpg");          
        driver.findElement(By.id("save-book")).click();
        Thread.sleep(2000);
        List<WebElement> books = driver.findElements(By.xpath("//div[contains(@ng-repeat,'record in records')]"));
        for (WebElement book : books) {
            WebElement image = book.findElement(By.xpath("//img[contains(@ng-src,'http://image.casadellibro.com/a/l/t0/08/9788497592208.jpg')]"));
            List<WebElement> captions = book.findElements(By.xpath("//div[contains(@class, 'caption')]/p"));
            if (captions.get(0).getText().contains("Cien años de Soledad") && captions.get(1).getText().contains("Realismo mágico") && 
                captions.get(2).getText().contains("1025789845-13") && image.isDisplayed()){    
                success = true;
            }
        }
        assertTrue(success);
        Thread.sleep(1000);
    }

    
    
    
    
    
}
