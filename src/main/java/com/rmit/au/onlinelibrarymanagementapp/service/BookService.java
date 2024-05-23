package com.rmit.au.onlinelibrarymanagementapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import com.rmit.au.onlinelibrarymanagementapp.repository.BookRepository;

import lombok.SneakyThrows;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @SneakyThrows
    public void addOrUpdateBooks(List<Book> books) throws InvalidBookInformation {
        if (!books.isEmpty()) {
            books.forEach(book -> {
                var existingBook = bookRepository.findBookByBookId(book.getBookId());
                if (existingBook.isEmpty()) {
                    bookRepository.insert(book);
                } else {
                    existingBook.get().setTitle(book.getTitle());
                    existingBook.get().setAuthor(book.getAuthor());
                    existingBook.get().setYear(book.getYear());
                    bookRepository.save(existingBook.get());
                }
            });
        } else {
            throw new InvalidBookInformation("Please provide books data to add or update!");
        }
    }

    @SneakyThrows
    public List<Book> getBook(String bookIdentifier) throws InvalidBookInformation {
        Optional<Book> existingbook = bookRepository.findBookByBookId(bookIdentifier);
        if (existingbook.isEmpty()) {
            existingbook = bookRepository.findBookByTitle(bookIdentifier);
            if (existingbook.isEmpty()) {
                var existingBookList = bookRepository.findBookByAuthor(bookIdentifier);
                if (existingBookList.isEmpty()) {
                    throw new InvalidBookInformation("Please provide a valid Book Identifier to access it!");
                } else {
                    return existingBookList.get();
                }
            }
        }
        return List.of(existingbook.get());
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @SneakyThrows
    public void deleteBook(String bookId) throws InvalidBookInformation {
        var book = bookRepository.findBookByBookId(bookId);
        if (book.isPresent()) {
            bookRepository.deleteBookByBookId(bookId);
        } else {
            throw new InvalidBookInformation("Book doesn't exists to delete!");
        }
    }
}
