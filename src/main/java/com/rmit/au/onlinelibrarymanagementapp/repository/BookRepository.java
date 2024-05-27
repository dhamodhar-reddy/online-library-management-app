package com.rmit.au.onlinelibrarymanagementapp.repository;

import com.rmit.au.onlinelibrarymanagementapp.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}