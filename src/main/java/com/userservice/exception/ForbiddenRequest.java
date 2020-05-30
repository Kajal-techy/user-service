package com.userservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "stackTrace", "cause", "suppressed", "localizedMessage"})
public class ForbiddenRequest extends RuntimeException {

    public ForbiddenRequest(String message) {
        super(message);
    }
}
