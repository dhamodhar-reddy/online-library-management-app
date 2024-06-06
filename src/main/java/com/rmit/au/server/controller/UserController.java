package com.rmit.au.server.controller;

import com.rmit.au.server.exception.DuplicateUserException;
import com.rmit.au.server.exception.InvalidJWTException;
import com.rmit.au.server.exception.InvalidUserCredentials;
import com.rmit.au.server.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.server.model.User;
import com.rmit.au.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) throws InvalidUserCredentials, InvalidJWTException {
        return userService.loginUser(user);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> registerUser(@RequestBody User user) throws DuplicateUserException {
        return userService.registerUser(user);
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public ResponseEntity<Void> forgotPassword(@RequestBody User user) throws InvalidUsernameForPasswordReset {
        return userService.forgotPassword(user);
    }
}
