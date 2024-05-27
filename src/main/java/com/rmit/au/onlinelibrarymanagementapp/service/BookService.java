package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import com.rmit.au.onlinelibrarymanagementapp.repository.BookRepository;
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
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            bookRepository.deleteBookByBookId(bookId);
        } else {
            throw new InvalidBookInformation();
        }
    }
}