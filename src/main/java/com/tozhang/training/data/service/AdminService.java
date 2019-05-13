package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService extends Admin{

    public boolean checkIfNewAdmin(Admin admin){
        if(admin.getFirst()==true) return true;
        else return false;
    }

    public boolean isPasswordMatch(Admin admin, Map payload){
        if (admin.getPassword().equals(payload.get("password"))) return true;
        else return false;
    }
}
