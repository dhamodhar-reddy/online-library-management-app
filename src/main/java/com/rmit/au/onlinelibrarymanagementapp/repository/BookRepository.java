package com.rmit.au.onlinelibrarymanagementapp.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rmit.au.onlinelibrarymanagementapp.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    Optional<Book> findBookByBookId(String bookId);

    Optional<Book> findBookByTitle(String title);

    Optional<List<Book>> findBookByAuthor(String author);

    void deleteBookByBookId(String bookId);
}