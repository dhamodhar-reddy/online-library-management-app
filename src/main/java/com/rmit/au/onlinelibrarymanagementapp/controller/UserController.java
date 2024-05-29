package com.rmit.au.onlinelibrarymanagementapp.controller;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidJWTException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.model.User;
import com.rmit.au.onlinelibrarymanagementapp.service.JWTService;
import com.rmit.au.onlinelibrarymanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) throws InvalidUserCredentials, InvalidJWTException {
        return userService.loginUser(user);
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
