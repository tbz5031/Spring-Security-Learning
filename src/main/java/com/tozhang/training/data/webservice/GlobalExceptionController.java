package com.tozhang.training.data.webservice;

import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController extends Throwable {

    @ExceptionHandler(value = ServiceRuntimeException.class)
    public ResponseEntity<Object> exception(ServiceRuntimeException exception) {
        return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid Access Token");
    }
}


