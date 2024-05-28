package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.repository.UserRepository;
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

    public void registerUser(User user) throws DuplicateUserException {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            user.setRole("USER");
            userRepository.insert(user);
        } else {
            throw new DuplicateUserException();
        }
    }

    public void forgotPassword(User user) throws InvalidUsernameForPasswordReset {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent() && existingUser.get().getRole().equals("Non-Admin")) {
            existingUser.get().setPassword(user.getPassword());
            userRepository.save(existingUser.get());
        } else {
            throw new InvalidUsernameForPasswordReset();
        }
    }

    public ResponseEntity<Map<String, String>> loginUser(User user) throws InvalidUserCredentials {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (!user.getPassword().equals(existingUser.get().getPassword())) {
                throw new InvalidUserCredentials();
            }
            if (existingUser.get().getRole().equalsIgnoreCase("USER")) {
                Map<String, String> response = new HashMap<>();
                response.put("token", jwtService.generateToken(user.getEmail()));
                response.put("username", existingUser.get().getUsername());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if (existingUser.get().getRole().equalsIgnoreCase("ADMIN")) {
                Map<String, String> response = new HashMap<>();
                response.put("token", jwtService.generateToken(user.getEmail()));
                response.put("username", existingUser.get().getUsername());
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            } else {
                throw new InvalidUserCredentials();
            }
        } else {
            throw new InvalidUserCredentials();
        }
    }
}