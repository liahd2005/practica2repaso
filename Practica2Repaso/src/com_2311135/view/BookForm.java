/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com_2311135.view;

import com_2311135.model.Book;
import com_2311135.service.BookService;

import javax.swing.*;
import java.awt.*;
public class BookForm extends JDialog{
    
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField yearField;
    private JButton saveButton;
    private JButton deleteButton; 

    public BookForm(JFrame parent, String title, BookService bookService, Book bookToEdit) {
        super(parent, title, true);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        // Campos del formulario
        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("Genre:"));
        genreField = new JTextField();
        add(genreField);

        add(new JLabel("Year:"));
        yearField = new JTextField();
        add(yearField);

        // Botón Guardar
        saveButton = new JButton("Save");
        add(saveButton);
        
        // Botón Eliminar
        deleteButton = new JButton("Delete");
        add(deleteButton);
        deleteButton.setEnabled(false); // Desactivar el botón por defecto


        // Si estás editando un libro, precargar los datos
        if (bookToEdit != null) {
            titleField.setText(bookToEdit.getTitle());
            authorField.setText(bookToEdit.getAuthor());
            genreField.setText(bookToEdit.getGenre());
            yearField.setText(String.valueOf(bookToEdit.getYear()));
            deleteButton.setEnabled(true); // Habilitar el botón de eliminar
        
        }

        saveButton.addActionListener(e -> {
            try {
                String titleInput = titleField.getText();
                String authorInput = authorField.getText();
                String genreInput = genreField.getText();
                int yearInput = Integer.parseInt(yearField.getText());

                if (bookToEdit == null) {
                    // Crear nuevo libro
                    Book newBook = new Book.BookBuilder()
                            .setTitle(titleInput)
                            .setAuthor(authorInput)
                            .setGenre(genreInput)
                            .setYear(yearInput)
                            .build();
                    bookService.saveBook(newBook);
                } else {
                    // Editar libro existente
                    bookToEdit.setTitle(titleInput);
                    bookToEdit.setAuthor(authorInput);
                    bookToEdit.setGenre(genreInput);
                    bookToEdit.setYear(yearInput);
                    bookService.updateBook(bookToEdit);
                }
                dispose(); // Cerrar la ventana
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year must be a valid number.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        // Eliminar libro
        deleteButton.addActionListener(e -> {
            if (bookToEdit != null) {
                deleteButton.setEnabled(true); 
                int confirm = JOptionPane.showConfirmDialog(this, 
                        "Are you sure you want to delete this book?", 
                        "Confirm Delete", 
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    bookService.deleteBook(bookToEdit.getId());
                    dispose();  // Cerrar la ventana después de eliminar
                }
            }
                        System.out.println("booktoedit is: " +bookToEdit);

        });
    }
}


