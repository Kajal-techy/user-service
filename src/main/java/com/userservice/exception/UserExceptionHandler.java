package com.userservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(UserExistsException.class);

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<String> userExistsException(UserExistsException exception) {
        logger.debug("Entering UserExceptionHandler.userExistsException with parameter exception {}.", exception);
        return ResponseEntity.badRequest().body(exception.toString());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException exception) {
        logger.debug("Entering UserExceptionHandler.userNotFoundException with parameter exception {}.", exception);
        return ResponseEntity.badRequest().body(exception.toString());
    }
}
