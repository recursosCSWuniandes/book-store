/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bookbasico.api;

import co.edu.uniandes.csw.bookbasico.dtos.BookDTO;
import co.edu.uniandes.csw.bookbasico.dtos.EditorialDTO;
import java.util.List;

/**
 *
 * @author kaosterra
 */
public interface IEditorialLogic {

    public List<EditorialDTO> getEditorials();

    public EditorialDTO getEditorial(Long id);

    public EditorialDTO createEditorial(EditorialDTO dto);

    public EditorialDTO updateEditorial(EditorialDTO dto);

    public void deleteEditorial(Long id);
    
    public BookDTO addBook(Long bookId, Long editorialId);

    public void removeBook(Long bookId, Long editorialId);

    public List<BookDTO> replaceBooks(List<BookDTO> books, Long editorialId);

    public List<BookDTO> getBooks(Long editorialId);

    public BookDTO getBook(Long editorialId, Long bookId);
}
