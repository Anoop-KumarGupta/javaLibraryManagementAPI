package com.example.lms.errorHandling;

public class GlobalNotFoundException extends RuntimeException {

    public GlobalNotFoundException(String message){
        super(message);
    }
    public GlobalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalNotFoundException(Throwable cause) {
        super(cause);
    }
}
