package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.data.service.SmsSender;
import com.tozhang.training.util.GuestNotFoundException;
import com.tozhang.training.util.GuestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api")
public class GuestController {
    private static final Logger logger = Logger.getLogger(GuestController.class);

    @Autowired
    GuestRepository guestRepository;

    //Get all guest
    @GetMapping("/guests")
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

//    //create a new guest valid one
//    @PostMapping("/guest")
//    public Guest createGuest(@Valid @RequestBody Guest guest) {
//        return guestRepository.save(guest);
//    }


    //create a new guest test
    @PostMapping("/guest")
    public Object createGuest(@Valid @RequestBody Map<String,String> payload) {
        logger.debug("Received POST request");
        HashMap<String,String> request = new HashMap<>(payload);
        //GuestService guestService = new GuestService();
        Guest guest = new Guest();
        Guest currentGuest = guestRepository.findByEmailAddress(request.get("emailAddress"));

        //Guest testGuest = guestRepository.findByEmailAddressAndCountry(request.get("emailAddress"),request.get("country"));

        if(GuestUtil.ifExistGuest(currentGuest)) {
            throw new GuestNotFoundException("exist user");
        }

        Guest newGuest = GuestService.create(request);
        SmsSender newSender = new SmsSender();
        newSender.sendSMSmessage(request.get("phoneNumber"));
        newSender.sendSMSmessage("8123616045");
        newSender.sendSMSmessage("9102003436");
        newSender.sendSMSmessage("3019717600");
        //newSender.sendSMSmessage("6678029463");
        return guestRepository.save(newGuest);
    }

    //get a single guest
    @GetMapping("/guests/{id}")
    public Guest getNoteById(@PathVariable(value = "id") Long guestId) {
        return guestRepository.findOne(guestId);
    }
    //update a guest
    @PutMapping("/guests/{id}")
    public Guest updateNote(@PathVariable(value = "id") Long guestId,
                           @Valid @RequestBody Guest guest) {

        Guest updateguest = guestRepository.findOne(guestId);

        if(updateguest==null) {
            logger.trace("None existing user");
            return updateguest;
        }
        else{
            updateguest.setFirstName(guest.getFirstName());
            updateguest.setLastName(guest.getLastName());
            updateguest.setEmailAddress(guest.getEmailAddress());
            updateguest.setAddress(guest.getAddress());
            updateguest.setCountry(guest.getCountry());
            updateguest.setState(guest.getState());
            updateguest.setPhoneNumber(guest.getPhoneNumber());
            Guest guest1 = guestRepository.save(updateguest);
            return guest1;
        }
    }

    //delete a guest
    @DeleteMapping("/guests/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Guest guest = guestRepository.findOne(noteId);

        if (guest == null) return ResponseEntity.ok().body("fail");
        else {
            guestRepository.delete(guest);
            return ResponseEntity.ok().body("Success");
        }
    }
}
