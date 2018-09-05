package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Map;

public class GuestService {

    public static Guest create(Map request) {
        Guest guest = new Guest();
        guest.setAddress(request.get("address").toString());
        guest.setPhoneNumber(request.get("phoneNumber").toString());
        guest.setState(request.get("state").toString());
        guest.setLastName(request.get("lastName").toString());
        guest.setFirstName(request.get("firstName").toString());
        guest.setEmailAddress(request.get("emailAddress").toString());
        guest.setCountry(request.get("country").toString());
        return guest;
    }
}
