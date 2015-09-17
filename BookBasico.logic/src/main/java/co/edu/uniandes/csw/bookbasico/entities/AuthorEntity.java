package co.edu.uniandes.csw.bookbasico.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class AuthorEntity implements Serializable{

    @Id
    @GeneratedValue(generator = "Author")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<BookEntity> books;

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

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
}
