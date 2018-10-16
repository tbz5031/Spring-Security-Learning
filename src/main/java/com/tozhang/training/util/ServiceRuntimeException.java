package com.tozhang.training.util;


import java.util.Date;
import java.util.Map;

public class ServiceRuntimeException extends RuntimeException{
    private Date timestamp;
    private String message;
    private String details;

    public ServiceRuntimeException() {
        super();
    }

    public ServiceRuntimeException(String exception,String message, String details) {
        super(exception);
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRuntimeException(String message) {
        super(message);
    }
    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
