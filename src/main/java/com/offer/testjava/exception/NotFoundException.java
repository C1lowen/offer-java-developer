package com.offer.testjava.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
