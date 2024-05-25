package com.rmit.au.onlinelibrarymanagementapp.controller;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Void> loginUser(@RequestBody User user) throws InvalidUserCredentials {
        var isAdmin = userService.loginUser(user);
        if (isAdmin) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> registerUser(@RequestBody User user) throws DuplicateUserException {
        userService.registerUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public ResponseEntity<Void> forgotPassword(@RequestBody User user) throws InvalidUsernameForPasswordReset {
        userService.forgotPassword(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
