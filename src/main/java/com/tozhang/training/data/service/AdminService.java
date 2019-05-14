package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.webservice.AdminController;
import com.tozhang.training.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService extends Admin{

    private static final Logger logger = Logger.getLogger(AdminService.class);

    @Autowired
    private static AdminRepository adminRepository;

    @Autowired
    public void setAdminRepo(AdminRepository adminRepository ) {
        AdminService.adminRepository = adminRepository;
    }

    public boolean checkIfNewAdmin(Admin admin){
        if(admin.getFirst()==true) return true;
        else return false;
    }

    public boolean isPasscodeMatch(Admin admin, Map payload){
        try {
            if (admin.getPassword().equals(payload.get(Constant.Param.password))) return true;
            else return false;
        }catch (NullPointerException ne){
            return false;
        }
    }

    public boolean isPasswordMatch(Admin admin, Map payload){
        try {
            if (admin.getPassword().equals(payload.get(Constant.Param.password))) return true;
            else return false;
        }catch (NullPointerException ne){
            return false;
        }
    }

    public Admin updateAdmin(Map payload){
        Admin admin = adminRepository.findByadminaccount(payload.get(Constant.Param.account).toString());
        if(isPasscodeMatch(admin,payload)||isPasswordMatch(admin,payload)){
            logger.info("Authentication passed");
            if(checkIfNewAdmin(admin)){
                admin.setPassword(payload.get(Constant.Param.newPasscode).toString());
                admin.setAdminAccount(payload.get(Constant.Param.newAccount).toString());
                admin.setFirstName(payload.get(Constant.Param.firstName).toString());
                admin.setLastName(payload.get(Constant.Param.lastName).toString());
                admin.setPhoneNumber(payload.get(Constant.Param.phoneNumber).toString());
                admin.setEmailAddress(payload.get(Constant.Param.emailAddress).toString());
                admin.setFirst(false);
                adminRepository.save(admin);
                return admin;
            }
            else{
                admin.setPassword(payload.get(Constant.Param.newPasscode).toString());
                admin.setFirstName(payload.get(Constant.Param.firstName).toString());
                admin.setLastName(payload.get(Constant.Param.lastName).toString());
                admin.setPhoneNumber(payload.get(Constant.Param.phoneNumber).toString());
                adminRepository.save(admin);
                return admin;
            }
        }
        else{
            logger.info("Passcode or password does not match");
            return admin;
        }
    }

    public Admin updateLoginTimeAndStatus(Admin admin) {
        admin.setLoginTs(System.currentTimeMillis());
        admin.setStatus("active");
        return admin;
    }
}
