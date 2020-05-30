package com.userservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "stackTrace", "cause", "suppressed", "localizedMessage"})
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
