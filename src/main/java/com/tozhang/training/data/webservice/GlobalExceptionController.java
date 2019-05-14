package com.tozhang.training.data.webservice;

import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController extends Throwable {
    private static final Logger logger = Logger.getLogger(GlobalExceptionController.class);
    @ExceptionHandler(value = ServiceRuntimeException.class)
    public ResponseEntity<Object> exception(ServiceRuntimeException exception) {
        logger.error("Globally caught exception: need to solve later");
        exception.printStackTrace();
        return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"There is something wrong with our server");
    }
}


