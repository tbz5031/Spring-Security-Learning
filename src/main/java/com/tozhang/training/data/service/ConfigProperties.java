package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Admin;
import com.tozhang.training.data.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
@Service
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Autowired
    private Environment env;

    @Autowired
    AdminRepository adminRepository;

    public String getConfigValue(String configKey){
        return env.getProperty(configKey);
    }


    // Set up first admin user
    @PostConstruct
    public void setUpAdmin(){
        Admin admin = new Admin();
        String adminUsername = env.getProperty("admin.username");
        String adminPasscode = env.getProperty("admin.passcode");
        HashMap<String,String> map = new HashMap<>();
        admin.setFirstName("FIRST_NAME");
        admin.setLastName("dsa");
        admin.setAccount(adminUsername);
        admin.setEmailAddress("tozhang@microstrategy.com");
        admin.setPhoneNumber("6666666666");
        admin.setPassword(adminPasscode);
        admin.setFirst(true);
        adminRepository.save(admin);
    }
}
