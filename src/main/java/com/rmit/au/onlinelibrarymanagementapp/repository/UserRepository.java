package com.rmit.au.onlinelibrarymanagementapp.repository;

import com.rmit.au.onlinelibrarymanagementapp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findUserByEmail(String email);
}