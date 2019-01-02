package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.util.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;

import java.util.Map;

public class GuestService {

    GuestService gs = new GuestService();

    public static Guest create(Map request,Guest cur) {
        Guest guest = new Guest();
        if (cur!=null) throw new ServiceRuntimeException("User Already Exists");
        else {
            guest = GuestService.transfer(request);
            SmsSender newSender = new SmsSender();
            //newSender.sendSMSmessage(request.get("phoneNumber").toString());
        }
        return guest;
    }
    protected static Guest transfer(Map request){
        Guest guest = new Guest();
        guest.setAddress(request.get("address").toString());
        guest.setPhoneNumber(request.get("phoneNumber").toString());
        guest.setState(request.get("state").toString());
        guest.setLastName(request.get("lastName").toString());
        guest.setFirstName(request.get("firstName").toString());
        guest.setEmailAddress(request.get("emailAddress").toString());
        guest.setCountry(request.get("country").toString());
        guest.setPassword(request.get("password").toString());
        return guest;
    }
}
