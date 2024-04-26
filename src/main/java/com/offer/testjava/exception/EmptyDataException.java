package com.offer.testjava.exception;

import org.springframework.http.HttpStatus;

public class EmptyDataException extends ApiException{

    public EmptyDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
