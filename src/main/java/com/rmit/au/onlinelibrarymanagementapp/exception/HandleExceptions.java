package com.rmit.au.onlinelibrarymanagementapp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleExceptions {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateUserException.class)
    public Map<String, String> handleDuplicateUserException(DuplicateUserException duplicateUserException) {
        Map<String, String> error = new HashMap<>();
        error.put("key", duplicateUserException.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameForPasswordReset.class)
    public Map<String, String> handleInvalidUsernameForPasswordReset(InvalidUsernameForPasswordReset invalidUsernameForPasswordReset) {
        Map<String, String> error = new HashMap<>();
        error.put("key", invalidUsernameForPasswordReset.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidUserCredentials.class)
    public Map<String, String> handleInvalidUserCredentials(InvalidUserCredentials invalidUserCredentials) {
        Map<String, String> error = new HashMap<>();
        error.put("key", invalidUserCredentials.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidBookInformation.class)
    public Map<String, String> handleInvalidBookInformation(InvalidBookInformation invalidBookInformation) {
        Map<String, String> error = new HashMap<>();
        error.put("key", invalidBookInformation.getMessage());
        return error;
    }
    
}
