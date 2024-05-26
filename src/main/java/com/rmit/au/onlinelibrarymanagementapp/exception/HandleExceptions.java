package com.rmit.au.onlinelibrarymanagementapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleExceptions {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Void> handleDuplicateUserException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameForPasswordReset.class)
    public ResponseEntity<Void> handleInvalidUsernameForPasswordReset() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserCredentials.class)
    public ResponseEntity<Void> handleInvalidUserCredentials() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidBookInformation.class)
    public ResponseEntity<Void> handleInvalidBookInformation() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}