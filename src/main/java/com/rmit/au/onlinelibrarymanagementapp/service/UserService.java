package com.rmit.au.onlinelibrarymanagementapp.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    @SneakyThrows
    public String registerUser(User user) throws DuplicateUserException {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            user.setPassword(bc.encode(user.getPassword()));
            userRepository.insert(user);
        } else {
            throw new DuplicateUserException("User already exists!");
        }
        return "User Registration Successful";
    }

    @SneakyThrows
    public String resetPassword(User user) throws InvalidUsernameForPasswordReset {
        var existingUser = userRepository.findUserByEmail(user.email);
        if (existingUser.isPresent()) {
            existingUser.get().setPassword(bc.encode(user.getPassword()));
            userRepository.save(existingUser.get());
        } else {
            throw new InvalidUsernameForPasswordReset("Invalid Username for resetting password");
        }
        return "Password Reset Successful";
    }

    @SneakyThrows
    public String loginUser(User user) throws InvalidUserCredentials {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (!bc.matches(user.getPassword(), existingUser.get().getPassword())) {
                throw new InvalidUserCredentials("Invalid Username or Password");
            }
        } else {
            throw new InvalidUserCredentials("Invalid Username or Password");
        }
        return "User Login Successful";
    }
}
