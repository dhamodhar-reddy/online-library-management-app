package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) throws DuplicateUserException {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            user.setRole("Non-Admin");
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

    public Boolean loginUser(User user) throws InvalidUserCredentials {
        Boolean isAdmin;
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (!user.getPassword().equals(existingUser.get().getPassword())) {
                throw new InvalidUserCredentials();
            }
            if (existingUser.get().getRole().equals("Non-Admin")) {
                isAdmin = FALSE;
            } else if (existingUser.get().getRole().equalsIgnoreCase("admin")) {
                isAdmin = TRUE;
            } else {
                throw new InvalidUserCredentials();
            }
        } else {
            throw new InvalidUserCredentials();
        }
        return isAdmin;
    }
}