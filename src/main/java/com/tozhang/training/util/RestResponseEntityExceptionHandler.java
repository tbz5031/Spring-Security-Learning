package com.tozhang.training.util;

import com.tozhang.training.data.errorHandling.IDMException;
import com.tozhang.training.data.webservice.GuestController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { IDMException.class})
    public ResponseEntity<Object> handleConflict(
            ServiceRuntimeException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        return new IDMResponse().Wrong(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
