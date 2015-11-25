package co.edu.uniandes.csw.bookbasico.dtoTest;

import co.edu.uniandes.csw.bookbasico.dtos.ReviewDTO;
import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;

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
public class ReviewDTOTest {
    public static final String DEPLOY = "Prueba";

    /**
     * @generated
     */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
                .addPackage(ReviewDTO.class.getPackage())
                .addPackage(BookDTO.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    @Test
    public void createReviewDTOTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ReviewDTO reviewDto = factory.manufacturePojo(ReviewDTO.class);
        Assert.assertNotNull(reviewDto);
    }

    /**
     * @generated
     */
    @Test
    public void updateReviewDTOTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ReviewDTO reviewDto = factory.manufacturePojo(ReviewDTO.class);
        Assert.assertNotNull(reviewDto);
        
        BookDTO bookDto = factory.manufacturePojo(BookDTO.class);
        reviewDto.setId(Long.valueOf(1));
        reviewDto.setName("Review Name");
        reviewDto.setSource("Review Source");
        reviewDto.setDescription("Review Description");
        reviewDto.setBook(bookDto);
        
        Assert.assertEquals(Long.valueOf(1), reviewDto.getId());
        Assert.assertEquals("Review Name", reviewDto.getName());
        Assert.assertEquals("Review Source", reviewDto.getSource());
        Assert.assertEquals("Review Description", reviewDto.getDescription());
        Assert.assertEquals(bookDto, reviewDto.getBook());
    }
}