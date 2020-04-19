package com.userservice.exception;

public class ForbiddenRequest extends RuntimeException {

    public ForbiddenRequest(String message) {
        super(message);
    }
}
