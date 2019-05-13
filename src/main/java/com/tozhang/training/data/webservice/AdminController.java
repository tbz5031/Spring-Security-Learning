package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.service.AdminService;
import com.tozhang.training.data.service.ConfigProperties;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.util.Constant;
import com.tozhang.training.util.GuestUtil;
import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.UtilTools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    JWTService jwtService;

    @Autowired
    AdminRepository adminRepository;

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

    @PostMapping("/adminSignIn")
    public ResponseEntity<Object> adminLogin(@Valid @RequestBody Map<String,String> payload) throws IOException {
        logger.info("Admin SignIn Process");
        jwtService = new JWTService();
        HashMap<String,String> request = new HashMap<>(payload);
        String token = null;
        Admin admin = adminRepository.findByEmailAddress(request.get("emailAddress"));




        // transfer guest object to hashmap
//        Map<String, Object> result = GuestUtil.mappingHelper(guest);
//        if(guest!=null && guest.getPassword().equals(payload.get("password"))){
//            guest = GuestService.updateLoginTimeAndStatus(guest);
//            adminRepository.save(guest);
//            result.put("loginTs",guest.getLoginTs());
//            token = jwtService.jwtIssuer(result);
//            //todo Need to implement refreshtoken.
//            //refresh_token = JWTService.jwtIssuer()
//        }
//        else return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Not valid credential or username");
//
//        result.put("accessToken",token);
//        return new IDMResponse().Correct(HttpStatus.OK,result,"Login Successfully");
        return null;
    }


    @PostMapping("/adminUpdate")
    public ResponseEntity<Object> adminUpdate(@Valid @RequestBody Map<String,String> payload) throws IOException {
        logger.info("Admin SignIn Process");
        AdminService adminService = new AdminService();
        HashMap<String,String> request = new HashMap<>(payload);
        UtilTools utilTools = new UtilTools();
        if(utilTools.checkParameters(payload, Constant.RequiredParams.adminUpdate)){
            return new IDMResponse().Correct(HttpStatus.OK,"ok");
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"missing parameters");


//        if(payload.containsKey("adminAccount")&&payload.containsKey("passcode")){
//            Admin admin = adminRepository.findByAccount(payload.get("adminAccount"));
//            if(adminService.isPasswordMatch(admin,payload)){
//
//            }else{
//                logger.error("password does not match for account" + payload.get("account"));
//                return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"password does not match");
//            }
//        }else
//            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Missing Parameters");
    }



}
