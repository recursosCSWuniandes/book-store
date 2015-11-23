package co.edu.uniandes.csw.bookbasico.dtos;

import co.edu.uniandes.csw.auth.model.DateAdapter;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement 
public class AuthorDTO {
    private Long id;
    private String name;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
