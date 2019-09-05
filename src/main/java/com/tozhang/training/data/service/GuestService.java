package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.util.ServiceRuntimeException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class GuestService {

    GuestService gs = new GuestService();

    public static Guest createnewguest(Map request,Guest cur) {
        Guest guest;
        if (cur!=null) throw new ServiceRuntimeException("User Already Exists");
        else {
            guest = GuestService.transfer(request);
//            SmsSender newSender = new SmsSender();
//            newSender.sendSMSmessage(request.get("phoneNumber").toString());
        }
        return guest;
    }
    protected static Guest transfer(Map request){

        Guest guest = new Guest.Builder(request.get("emailAddress").toString(),request.get("account").toString())
                .address(request.get("address").toString())
                .phoneNumber(request.get("phoneNumber").toString())
                .state(request.get("state").toString())
                .lastName(request.get("lastName").toString())
                .firstName(request.get("firstName").toString())
                .country(request.get("country").toString())
                .password(request.get("password").toString())
                .deviceId(request.get("deviceId").toString())
                .createdTs(System.currentTimeMillis())
                .build();


//        guest.setAddress(request.get("address").toString());
//        guest.setPhoneNumber(request.get("phoneNumber").toString());
//        guest.setState(request.get("state").toString());
//        guest.setLastName(request.get("lastName").toString());
//        guest.setFirstName(request.get("firstName").toString());
//        guest.setEmailAddress(request.get("emailAddress").toString());
//        guest.setCountry(request.get("country").toString());
//        guest.setPassword(request.get("password").toString());
//        guest.setDeviceId(request.get("deviceId").toString());
//        guest.setAccount(request.get("account").toString());
//        guest.setCreatedTs(System.currentTimeMillis());
        return guest;
    }

    public static Guest updateLoginTimeAndStatus(Guest guest){
        guest.setLoginTs(System.currentTimeMillis());
        guest.setStatus("active");
        return guest;
    }
}
