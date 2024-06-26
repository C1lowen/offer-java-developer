package com.offer.testjava.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Data
public class ApiException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
