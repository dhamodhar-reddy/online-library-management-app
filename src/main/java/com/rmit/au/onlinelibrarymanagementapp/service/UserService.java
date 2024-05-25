package com.rmit.au.onlinelibrarymanagementapp.service;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void registerUser(User user) throws DuplicateUserException {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isEmpty()) {
            userRepository.insert(user);
        } else {
            throw new DuplicateUserException();
        }
    }


    public void resetPassword(User user) throws InvalidUsernameForPasswordReset {
        var existingUser = userRepository.findUserByEmail(user.email);
        if (existingUser.isPresent()) {
            userRepository.save(existingUser.get());
        } else {
            throw new InvalidUsernameForPasswordReset();
        }
    }


    public void loginUser(User user) throws InvalidUserCredentials {
        var existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (!user.getPassword().equals(existingUser.get().getPassword())) {
                throw new InvalidUserCredentials();
            }
        } else {
            throw new InvalidUserCredentials();
        }
    }
}