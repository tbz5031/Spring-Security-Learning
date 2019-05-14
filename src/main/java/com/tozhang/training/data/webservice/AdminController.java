package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.security.SecurityConstants;
import com.tozhang.training.data.service.AdminService;
import com.tozhang.training.data.service.ConfigProperties;
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
        Map<String, Object> result = GuestUtil.mappingHelper(payload);
        String token = null;
        AdminService adminService = new AdminService();
        if(utilTools.checkParameters(payload, Constant.RequiredParams.adminSignIn)){
            Admin admin = adminRepository.findByadminaccount(payload.get(Constant.Param.account));
            admin = adminService.updateLoginTimeAndStatus(admin);
            result.put("LoginTs",admin.getLoginTs());
            token = jwtService.jwtIssuer(result, SecurityConstants.AdminSECRET);
            adminRepository.save(admin);
            result.put("access_token",token);
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "missing parameters");

        logger.info("Login Successfully");
        return new IDMResponse().Correct(HttpStatus.OK,result,"Login Successfully");
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

    @GetMapping("/admin/all")
    public ResponseEntity<Object> getAllAdmins(@RequestParam Map<String,String> allParams,
                                               @RequestHeader Map<String,String> header){
        logger.info("Start to process Get All Admins ");
        AdminService adminService = new AdminService();
        UtilTools utilTools = new UtilTools();
        jwtService = new JWTService();
        if(jwtService.jwtValidator(header,allParams,SecurityConstants.AdminSECRET)){
            if(utilTools.checkParameters(allParams,Constant.RequiredParams.adminGetAll)){
                List<Admin> res = adminRepository.findAll();
                return new IDMResponse().Correct(HttpStatus.OK,res,"Successfully found admins");
            }
            else {
                return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"missing parameters.");
            }
        }
        else{
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"invalid access token");
        }


    }

}
