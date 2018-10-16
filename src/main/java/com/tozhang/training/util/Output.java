package com.tozhang.training.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.json.JsonHttpContent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.DataOutput;

public class Output {

    public ResponseEntity<Object> Wrong(HttpStatus status,String str){

        ApiErrorResponse response =new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.BAD_REQUEST.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status,Object body, String str){

        // convert json to object
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectMapper mapper = new ObjectMapper();
//        Object value = mapper.readValue(jsonSource , javaObject);

        // convert object to json
//        String json = null;
//        ObjectMapper mapper = new ObjectMapper();
//        try{
//            json = mapper.writeValueAsString(body);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        ApiErrorResponse response =new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status).withMessage(str).withDetail(body)
                .withError_code(status.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status, String str){

        ApiErrorResponse response =new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

}
