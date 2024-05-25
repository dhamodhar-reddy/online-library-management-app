package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import com.rmit.au.onlinelibrarymanagementapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public void addOrUpdateBooks(List<Book> books) throws InvalidBookInformation {
        if (!books.isEmpty()) {
            books.forEach(book -> {
                var existingBook = bookRepository.findBookByBookId(book.getBookId());
                if (existingBook.isEmpty()) {
                    bookRepository.insert(book);
                } else {
                    existingBook.get().setTitle(book.getTitle());
                    existingBook.get().setContent(book.getContent());
                    bookRepository.save(existingBook.get());
                }
            });
        } else {
            throw new InvalidBookInformation();
        }
    }


    public Book getBook(String bookIdentifier) throws InvalidBookInformation {
        Optional<Book> existingbook = bookRepository.findBookByBookId(bookIdentifier);
        if (existingbook.isEmpty()) {
            existingbook = bookRepository.findBookByTitle(bookIdentifier);
            if (existingbook.isEmpty()) {
                throw new InvalidBookInformation();
            } else {
                return existingbook.get();
            }
        } else {
            return existingbook.get();
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public void deleteBook(String bookId) throws InvalidBookInformation {
        var book = bookRepository.findBookByBookId(bookId);
        if (book.isPresent()) {
            bookRepository.deleteBookByBookId(bookId);
        } else {
            throw new InvalidBookInformation();
        }
    }
}