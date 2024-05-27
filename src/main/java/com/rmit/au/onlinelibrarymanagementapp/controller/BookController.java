package com.rmit.au.onlinelibrarymanagementapp.controller;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidJWTException;
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

    @PostMapping("/addBook")
    @ResponseBody
    public ResponseEntity<Void> addBook(@RequestHeader Map<String, String> headers, @RequestBody Book book) throws InvalidBookInformation, InvalidJWTException {
        jwtService.validateToken(headers);
        bookService.addBook(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allBooks")
    @ResponseBody
    public ResponseEntity<List<Book>> getAllBooks(@RequestHeader Map<String, String> headers) throws InvalidJWTException, InvalidBookInformation {
        jwtService.validateToken(headers);
        var books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @ResponseBody
    public ResponseEntity<Void> deleteBook(@RequestHeader Map<String, String> headers, @PathVariable String bookId) throws InvalidBookInformation, InvalidJWTException {
        jwtService.validateToken(headers);
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}