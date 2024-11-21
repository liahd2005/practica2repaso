/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com_2311135.service;

import com_2311135.model.Book;
import com_2311135.repository.BookRepository;
import java.util.List;


public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    // Guardar un libro validando el género
    public void saveBook(Book book) {
        if (isValidGenre(book.getGenre())) {
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Invalid genre. Allowed genres: Fiction, Non-Fiction, Mystery, Fantasy, Biography");
        }
    }
    
    // Actualizar un libro con validación
    public void updateBook(Book book) {
        if (isValidGenre(book.getGenre())) {
            bookRepository.update(book);
        } else {
            throw new IllegalArgumentException("Invalid genre. Allowed genres: Fiction, Non-Fiction, Mystery, Fantasy, Biography");
        }
    }
    //obtener todos los libros
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // buscar por autor
    
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    
    // buscar por titulo
    
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
    
    // buscar por genero
    
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
    
    public void deleteBook(int id) {
        bookRepository.delete(id);
    }
    
    //Validacion de genero permitido
    private boolean isValidGenre(String genre){
        return genre.equalsIgnoreCase("Fiction")||
               genre.equalsIgnoreCase("Non-Fiction")||
               genre.equalsIgnoreCase("Mystery")||
               genre.equalsIgnoreCase("Fantasy")||
               genre.equalsIgnoreCase("Biography");
    }

}
