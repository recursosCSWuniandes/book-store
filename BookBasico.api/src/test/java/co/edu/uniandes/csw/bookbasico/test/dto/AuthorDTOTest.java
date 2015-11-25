package co.edu.uniandes.csw.bookbasico.test.dto;

import co.edu.uniandes.csw.bookbasico.dtos.AuthorDTO;

import java.util.Date;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AuthorDTOTest {
    public static final String DEPLOY = "Prueba";

    /**
     * @generated
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(AuthorDTO.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    @Test
    public void createAuthorDTOTest() {
        PodamFactory factory = new PodamFactoryImpl();
        AuthorDTO authorDto = factory.manufacturePojo(AuthorDTO.class);
        Assert.assertNotNull(authorDto);
    }

    /**
     * @generated
     */
    @Test
    public void updateAuthorDTOTest() {
        PodamFactory factory = new PodamFactoryImpl();
        AuthorDTO authorDto = factory.manufacturePojo(AuthorDTO.class);
        Assert.assertNotNull(authorDto);
        
        authorDto.setId(Long.valueOf(1));
        authorDto.setName("Author Name");
        authorDto.setBirthDate(new Date(2015, 11, 11));
        Assert.assertEquals("Author Name", authorDto.getName());
        Assert.assertEquals(Long.valueOf(1), authorDto.getId());
        Assert.assertEquals(new Date(2015, 11, 11), authorDto.getBirthDate());
    }
}