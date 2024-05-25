package com.rmit.au.onlinelibrarymanagementapp.repository;

import com.rmit.au.onlinelibrarymanagementapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByEmail(String email);
}