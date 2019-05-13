package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.service.ConfigProperties;
import com.tozhang.training.util.IDMResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/system")
public class AdminController {

    @Autowired
    JWTService jwtService;

    @Autowired
    AdminRepository guestRepository;

    @Autowired
    ConfigProperties configProp;

    @GetMapping("/properties")
    public ResponseEntity<Object> getSystemProperties() {
        String portNumber = configProp.getConfigValue("server.port");
        String adminUsername = configProp.getConfigValue("admin.username");
        String adminPasscode = configProp.getConfigValue("admin.passcode");
        String systemVersion = configProp.getConfigValue("system.version");
        String dbVersion = configProp.getConfigValue("db.version");

        HashMap<String,String> map= new HashMap<String,String>();
        map.put("portNumber",portNumber);
        map.put("adminUsername",adminUsername);
        map.put("adminPasscode",adminPasscode);
        map.put("systemVersion",systemVersion);
        map.put("dbVersion",dbVersion);

        return new IDMResponse().Correct(HttpStatus.OK,map,"Successfully Processed");
    }
}
