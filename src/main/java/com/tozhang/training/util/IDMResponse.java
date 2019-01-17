package com.tozhang.training.util;

import com.tozhang.training.data.entity.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class IDMResponse {

    public ResponseEntity<Object> Wrong(HttpStatus status,String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status,Object body, String str){
        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str).withDetail(body)
                .withError_code(status.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status, String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status, List<Guest> ls, String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str).withDetail(ls)
                .withError_code(status.name()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

}
