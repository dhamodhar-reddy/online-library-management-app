package com.rmit.au.onlinelibrarymanagementapp.controller;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import com.rmit.au.onlinelibrarymanagementapp.service.BookService;
import com.rmit.au.onlinelibrarymanagementapp.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/addOrUpdateBooks")
    @ResponseBody
    public ResponseEntity<Void> addOrUpdateBooks(@RequestHeader Map<String, String> headers, @RequestBody List<Book> books) throws InvalidBookInformation, InvalidUserCredentials {
        jwtService.validateToken(headers);
        bookService.addOrUpdateBooks(books);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allBooks")
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBooks(@RequestHeader Map<String, String> headers) throws InvalidUserCredentials {
        jwtService.validateToken(headers);
        var books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @ResponseBody
    public ResponseEntity<Void> deleteBook(@RequestHeader Map<String, String> headers, @PathVariable String bookId) throws InvalidBookInformation, InvalidUserCredentials {
        jwtService.validateToken(headers);
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}