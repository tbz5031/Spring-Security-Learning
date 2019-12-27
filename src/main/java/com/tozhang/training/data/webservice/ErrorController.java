package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Room;
import com.tozhang.training.util.IDMResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ErrorController {

    @GetMapping(value = "")
    public ResponseEntity<Object> getError() {
        return new IDMResponse().Wrong(HttpStatus.OK,"Error end point, invalid token");
    }
    @PostMapping (value = "")
    public ResponseEntity<Object> postError() {
        return new IDMResponse().Wrong(HttpStatus.OK,"Error end point, invalid token");
    }
    @PutMapping(value = "")
    public ResponseEntity<Object> putError() {
        return new IDMResponse().Wrong(HttpStatus.OK,"Error end point, invalid token");
    }
    @DeleteMapping(value = "")
    public ResponseEntity<Object> deleteError() {
        return new IDMResponse().Wrong(HttpStatus.OK,"Error end point, invalid token");
    }
}
