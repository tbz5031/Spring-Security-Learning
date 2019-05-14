package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.security.SecurityConstants;
import com.tozhang.training.data.service.AdminService;
import com.tozhang.training.data.service.ConfigProperties;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.util.*;
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
        UtilTools utilTools = new UtilTools();
        HashMap<String,String> request = new HashMap<String, String>();
        Map<String, Object> result = GuestUtil.mappingHelper(payload);
        String token = null;
        AdminService adminService = new AdminService();
        if(utilTools.checkParameters(payload, Constant.RequiredParams.guestSignIn)){
            Admin admin = adminRepository.findByadminaccount(request.get(Constant.Param.account));
            token = jwtService.jwtIssuer(result, SecurityConstants.AdminSECRET);
            admin = adminService.updateLoginTimeAndStatus(admin);
            adminRepository.save(admin);
            result.put("LoginTs",admin.getLoginTs());
            result.put("access_token",token);
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "missing parameters");

        logger.info("Login Successfully");
        return new IDMResponse().Correct(HttpStatus.OK,result,"Login Successfully");


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

    }


    @PostMapping("/adminUpdate")
    public ResponseEntity<Object> adminUpdate(@Valid @RequestBody Map<String,String> payload) throws IOException {
        logger.info("Admin SignIn Process");
        AdminService adminService = new AdminService();
        HashMap<String, String> request = new HashMap<>(payload);
        UtilTools utilTools = new UtilTools();
        if (utilTools.checkParameters(payload, Constant.RequiredParams.adminUpdate)) {
            try{
                Admin res = adminService.updateAdmin(payload);
                if (res!=null) return new IDMResponse().Correct(HttpStatus.OK, res,"Admin user updated successfully");
                else throw new ServiceRuntimeException("Information does not match");
            }
            catch (ServiceRuntimeException se){
                return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "parameters wrong");
            }

        } else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "missing parameters");
    }




}
