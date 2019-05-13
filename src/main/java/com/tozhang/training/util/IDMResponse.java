package com.tozhang.training.util;

import com.tozhang.training.data.entity.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class IDMResponse extends ServiceRuntimeException{

    public IDMResponse(){
        super();
    }


    public IDMResponse(String invalid_access_token) {
        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST).withMessage(invalid_access_token)
                .withError_code(HttpStatus.BAD_REQUEST.value()).build();
    }

    public ResponseEntity<Object> Wrong(HttpStatus status, String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.value()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status,Object body, String str){
        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str).withDetail(body)
                .withError_code(status.value()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status, String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.value()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

    public ResponseEntity<Object> Correct(HttpStatus status, List<Guest> ls, String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str).withDetail(ls)
                .withError_code(status.value()).build();

        return new ResponseEntity<Object>(response,response.getStatus());
    }

}
