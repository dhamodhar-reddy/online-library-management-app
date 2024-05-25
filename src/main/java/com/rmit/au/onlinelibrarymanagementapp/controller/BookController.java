package com.rmit.au.onlinelibrarymanagementapp.controller;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import com.rmit.au.onlinelibrarymanagementapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addOrUpdateBooks")
    @ResponseBody
    public ResponseEntity<Void> addOrUpdateBooks(@RequestBody List<Book> books) throws InvalidBookInformation {
        bookService.addOrUpdateBooks(books);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{bookIdentifier}")
    @ResponseBody
    public ResponseEntity<Book> getAllBooksBasedOnSearchParam(@PathVariable String bookIdentifier) throws InvalidBookInformation {
        var book = bookService.getBook(bookIdentifier);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/allBooks")
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBooks() {
        var books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @ResponseBody
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) throws InvalidBookInformation {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}