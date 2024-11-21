/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com_2311135.view;

import com_2311135.repository.BookRepository;
import com_2311135.model.Book;
import com_2311135.service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibraryUI extends JFrame {
    private final BookService bookService;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public LibraryUI(BookService bookService) {
        this.bookService = bookService;
        
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        // Tabla para mostrar los libros
        String[] columnNames = {"ID", "Title", "Author", "Genre", "Year"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton searchButton = new JButton("Search Book");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        
        // Cargar libros al iniciar
        loadBooks();

        // Botón para añadir un libro
        addButton.addActionListener(e -> {
            BookForm bookForm = new BookForm(this, "Add Book", bookService, null);
            bookForm.setVisible(true);
            loadBooks();
        });
        
        // Botón para actualizar un libro
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String title = tableModel.getValueAt(selectedRow, 1).toString();
                String author = tableModel.getValueAt(selectedRow, 2).toString();
                String genre = tableModel.getValueAt(selectedRow, 3).toString();
                int year = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());

                Book book = new Book.BookBuilder()
                        .setId(id)
                        .setTitle(title)
                        .setAuthor(author)
                        .setGenre(genre)
                        .setYear(year)
                        .build();

                BookForm bookForm = new BookForm(this, "Update Book", bookService, book);
                bookForm.setVisible(true);
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to update.");
            }
        });
        
        // Botón para eliminar un libro
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                bookService.deleteBook(id);
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            }
        });

        // Botón para buscar un libro
        searchButton.addActionListener(e -> {
            String searchCriteria = JOptionPane.showInputDialog(this, "Enter title, author, or genre:");
            if (searchCriteria != null && !searchCriteria.isEmpty()) {
                List<Book> books = bookService.getBooksByTitle(searchCriteria);
                updateTable(books);
            }
        });
    }
    
    // Cargar libros en la tabla
    private void loadBooks() {
        List<Book> books = bookService.getAllBooks();
        updateTable(books);
    }

    // Actualizar la tabla con libros
    private void updateTable(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getYear()
            });
        }
    }
}
        

