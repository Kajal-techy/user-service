package com.userservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "stackTrace", "cause", "suppressed", "localizedMessage"})
public class UserExistsException extends RuntimeException {

    public UserExistsException(String message) {
        super(message);
    }
}
