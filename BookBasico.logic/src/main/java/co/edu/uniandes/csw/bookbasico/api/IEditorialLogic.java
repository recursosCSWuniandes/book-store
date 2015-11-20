/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.entities.BookEntity;
import co.edu.uniandes.csw.bookbasico.entities.EditorialEntity;
import java.util.List;

/**
 *
 * @author kaosterra
 */
public interface IEditorialLogic {

    public List<EditorialEntity> getEditorials();

    public EditorialEntity getEditorial(Long id);

    public EditorialEntity createEditorial(EditorialEntity entity);

    public EditorialEntity updateEditorial(EditorialEntity entity);

    public void deleteEditorial(Long id);
    
    public BookEntity addBook(Long bookId, Long editorialId);

    public void removeBook(Long bookId, Long editorialId);

    public List<BookEntity> replaceBooks(List<BookEntity> books, Long editorialId);

    public List<BookEntity> getBooks(Long editorialId);

    public BookEntity getBook(Long editorialId, Long bookId);
}
