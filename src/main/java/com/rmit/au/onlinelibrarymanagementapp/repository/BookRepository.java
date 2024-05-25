package com.rmit.au.onlinelibrarymanagementapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.au.onlinelibrarymanagementapp.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findBookByBookId(String bookId);

    Optional<Book> findBookByTitle(String title);

    void deleteBookByBookId(String bookId);
}