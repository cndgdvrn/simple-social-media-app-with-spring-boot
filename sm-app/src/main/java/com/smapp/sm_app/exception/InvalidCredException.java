package com.smapp.sm_app.exception;

public class InvalidCredException extends RuntimeException{
    public InvalidCredException(String message) {
        super(message);
    }
}
