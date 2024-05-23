package com.rmit.au.onlinelibrarymanagementapp.controller;

import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidBookInformation;
import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import com.rmit.au.onlinelibrarymanagementapp.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addOrUpdateBooks")
    @ResponseBody
    public ResponseEntity<HttpStatus> addOrUpdateBooks(@RequestBody List<Book> books) throws InvalidBookInformation {
        bookService.addOrUpdateBooks(books);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{bookIdentifier}")
    @ResponseBody
    public Map<String, List<Book>> getAllBooksBasedOnSearchParam(@PathVariable String bookIdentifier) throws InvalidBookInformation {
        Map<String, List<Book>> data = new HashMap<>();
        data.put("key", bookService.getBook(bookIdentifier));
        return data;
    }

    @GetMapping("/allBooks")
    @ResponseBody
    public Map<String, List<Book>> getAllBooks() {
        Map<String, List<Book>> data = new HashMap<>();
        data.put("key", bookService.getAllBooks());
        return data;
    }

    @DeleteMapping("/{bookId}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable String bookId) throws InvalidBookInformation {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}