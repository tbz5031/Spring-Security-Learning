package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.data.service.SmsSender;
import com.tozhang.training.util.Output;
import com.tozhang.training.util.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import static org.springframework.hateoas.jaxrs.JaxRsLinkBuilder.linkTo;

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
        logger.info("Received POST request");
        HashMap<String,String> request = new HashMap<>(payload);
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        if (guest != null) {
            logger.info("user already exists");
            return new Output().Wrong(HttpStatus.BAD_REQUEST,"User already exist");
        }
        else{
            Guest newGuest = GuestService.create(request);
            SmsSender newSender = new SmsSender();
            newSender.sendSMSmessage(request.get("phoneNumber"));
//        newSender.sendSMSmessage("8123616045");
//        newSender.sendSMSmessage("9102003436");
//        newSender.sendSMSmessage("3019717600");
//        //newSender.sendSMSmessage("6678029463");
            guestRepository.save(newGuest);
            logger.info("guest successfully added");
            return new Output().Correct(HttpStatus.OK,newGuest,"successfully added");
            //return new ResponseEntity<>(newGuest, new HttpHeaders(), HttpStatus.OK);
        }
    }

    //get a single guest find by id
    @GetMapping("/guests/{id}")
    public ResponseEntity<Object> getGuestById(@PathVariable(value = "id") Long guestId) {
        //Guest guest= new Guest();
        //Guest guest = guestRepository.findOne(guestId);
        Guest guest = guestRepository.findOne(guestId);
        if (guest==null)
        {
            //throw new ServiceRuntimeException("error");
            return new Output().Wrong(HttpStatus.NOT_FOUND,"user not found");
        }
        else
            return new Output().Correct(HttpStatus.OK,guest,"successfully founded");
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
