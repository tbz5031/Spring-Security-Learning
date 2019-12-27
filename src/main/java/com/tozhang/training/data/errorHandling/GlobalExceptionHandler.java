package com.tozhang.training.data.errorHandling;

import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = IDMException.class)
    public ResponseEntity<Object> exception(IDMException exception) {
        logger.error("Globally caught exception: need to solve later");
        exception.printStackTrace();
        return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
}


