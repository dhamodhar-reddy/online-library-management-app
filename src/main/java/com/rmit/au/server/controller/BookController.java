package com.rmit.au.server.controller;

import com.rmit.au.server.exception.InvalidBookInformation;
import com.rmit.au.server.exception.InvalidJWTException;
import com.rmit.au.server.model.Book;
import com.rmit.au.server.service.BookService;
import com.rmit.au.server.service.JWTService;
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
        var role = jwtService.validateToken(headers);
        if (role.equalsIgnoreCase("ADMIN")) {
            bookService.addBook(book);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
        var role = jwtService.validateToken(headers);
        if (role.equalsIgnoreCase("ADMIN")) {
            bookService.deleteBook(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}