package com.userservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<UserExistsException> userExistsException(UserExistsException exception) {
        log.info("Entering UserExceptionHandler.userExistsException with parameter exception {}.", exception);
        return ResponseEntity.badRequest().body(exception);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<NotFoundException> notFoundException(NotFoundException exception) {
        log.info("Entering UserExceptionHandler.notFoundException with parameter exception {}.", exception);
        return ResponseEntity.badRequest().body(exception);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ExpiredJwtException> expiredToken(ExpiredJwtException exception) {
        log.info("Entering UserExceptionHandler.expiredToken with parameter exception {}.", exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
    }

    @ExceptionHandler(value = ForbiddenRequest.class)
    public ResponseEntity<ForbiddenRequest> forBiddenRequest(ForbiddenRequest exception) {
        log.info("Entering UserExceptionHandler.forBiddenRequest with parameter exception {}.", exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception);
    }
}
