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
    public ResponseEntity<String> exception(UserExistsException exception) {
        logger.info("UserExistsException occurs " + exception);
        return ResponseEntity.badRequest().body(exception.toString());
    }
}
