package com.tozhang.training.util;

import com.tozhang.training.data.entity.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Output {

    public ResponseEntity<Object> Wrong(HttpStatus status,String str){

        ApiResponse response =new ApiResponse.ApiResponseBuilder()
                .withStatus(status).withMessage(str)
                .withError_code(status.name()).build();

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
