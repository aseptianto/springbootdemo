package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class StudentException {

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class EmailExistsException extends IllegalStateException {
        public EmailExistsException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends IllegalStateException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}

