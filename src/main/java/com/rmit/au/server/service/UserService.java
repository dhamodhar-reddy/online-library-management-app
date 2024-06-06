package com.rmit.au.server.service;

import com.rmit.au.server.exception.DuplicateUserException;
import com.rmit.au.server.exception.InvalidJWTException;
import com.rmit.au.server.exception.InvalidUserCredentials;
import com.rmit.au.server.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.server.model.User;
import com.rmit.au.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public ResponseEntity<Void> registerUser(User user) throws DuplicateUserException {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            userRepository.insert(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            throw new DuplicateUserException();
        }
    }

    public ResponseEntity<Void> forgotPassword(User user) throws InvalidUsernameForPasswordReset {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            existingUser.get().setPassword(user.getPassword());
            userRepository.save(existingUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new InvalidUsernameForPasswordReset();
        }
    }

    public ResponseEntity<Map<String, String>> loginUser(User user) throws InvalidUserCredentials, InvalidJWTException {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (!user.getPassword().equals(existingUser.get().getPassword())) {
                throw new InvalidUserCredentials();
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("token", jwtService.generateToken(existingUser.get()));
                response.put("username", existingUser.get().getUsername());
                response.put("role", existingUser.get().getRole());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            throw new InvalidUserCredentials();
        }
    }
}