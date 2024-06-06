package com.rmit.au.server.service;

import com.rmit.au.server.exception.InvalidBookInformation;
import com.rmit.au.server.model.Book;
import com.rmit.au.server.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void addBook(Book book) throws InvalidBookInformation {
        if (!book.getTitle().isEmpty() && !book.getContent().isEmpty()) {
            bookRepository.insert(book);
        } else {
            throw new InvalidBookInformation();
        }
    }

    public List<Book> getAllBooks() throws InvalidBookInformation {
        try {
            return bookRepository.findAll();
        } catch (Exception exception) {
            throw new InvalidBookInformation();
        }
    }

    public void deleteBook(String bookId) throws InvalidBookInformation {
        try {
            bookRepository.deleteById(bookId);
        } catch (Exception exception) {
            throw new InvalidBookInformation();
        }
    }
}