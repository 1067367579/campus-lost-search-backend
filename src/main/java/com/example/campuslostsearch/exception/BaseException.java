package com.example.campuslostsearch.exception;

public class BaseException extends RuntimeException {
    private final Integer code;

    public BaseException(String message) {
        super(message);
        this.code = 500;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }
} 