package com.rmit.au.onlinelibrarymanagementapp.controller;

import java.util.HashMap;
import java.util.Map;

import com.rmit.au.onlinelibrarymanagementapp.exception.DuplicateUserException;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUserCredentials;
import com.rmit.au.onlinelibrarymanagementapp.exception.InvalidUsernameForPasswordReset;
import com.rmit.au.onlinelibrarymanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rmit.au.onlinelibrarymanagementapp.model.User;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> loginUser(@RequestBody User user) throws InvalidUserCredentials {
        Map<String, String> response = new HashMap<>();
        var result = userService.loginUser(user);
        response.put("key", result);
        return response;
    }

    @PostMapping("register")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> registerUser(@RequestBody User user) throws DuplicateUserException {
        Map<String, String> response = new HashMap<>();
        var result = userService.registerUser(user);
        response.put("key", result);
        return response;
    }

    @PostMapping("resetPassword")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> resetPassword(@RequestBody User user) throws InvalidUsernameForPasswordReset {
        Map<String, String> response = new HashMap<>();
        var result = userService.resetPassword(user);
        response.put("key", result);
        return response;
    }
}
