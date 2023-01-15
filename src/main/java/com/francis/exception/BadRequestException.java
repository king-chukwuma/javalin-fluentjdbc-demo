package com.francis.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        String BASE_RESPONSE = "You sent a bad Request";
        throw new BadRequestException(BASE_RESPONSE);
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
