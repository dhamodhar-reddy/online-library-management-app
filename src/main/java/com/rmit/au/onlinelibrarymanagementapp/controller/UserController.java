package com.rmit.au.onlinelibrarymanagementapp.controller;

import java.util.HashMap;
import java.util.Map;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rmit.au.onlinelibrarymanagementapp.model.User;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Void> loginUser(@RequestBody User user) throws InvalidUserCredentials {
        userService.loginUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> registerUser(@RequestBody User user) throws DuplicateUserException {
        userService.registerUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseEntity<Void> resetPassword(@RequestBody User user) throws InvalidUsernameForPasswordReset {
        userService.resetPassword(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
