package com.tozhang.training.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GuestNotFoundException extends RuntimeException{
    private Date timestamp;
    private String message;
    private String details;

    public GuestNotFoundException() {
        super();
    }

    public GuestNotFoundException(String exception,String message, String details) {
        super(exception);
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public GuestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestNotFoundException(String message) {
        super(message);
    }
    public GuestNotFoundException(Throwable cause) {
        super(cause);
    }



}
