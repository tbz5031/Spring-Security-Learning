package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.data.service.SmsSender;
import com.tozhang.training.util.Output;
import com.tozhang.training.util.RestResponseEntityExceptionHandler;
import com.tozhang.training.util.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.WebRequest;

import static java.util.Collections.emptyList;


@RestController
@RequestMapping("/guest")
public class GuestController {
    private static final Logger logger = Logger.getLogger(GuestController.class);

    @Autowired
    GuestRepository guestRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //private WebRequest ex;


    public GuestController(GuestRepository guestRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.guestRepository = guestRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //Get all guest
    @GetMapping("/guests")
    public ResponseEntity<Object> getAllGuests() {
        List<Guest> ls_guests= guestRepository.findAll();
        return new Output().Correct(HttpStatus.OK,ls_guests,"Successful");
    }

//    //create a new guest valid one
//    @PostMapping("/guest")
//    public Guest createGuest(@Valid @RequestBody Guest guest) {
//        return guestRepository.save(guest);
//    }


    //create a new guest test
    @PostMapping("/signUp")
    public Object createGuest(@Valid @RequestBody Map<String,String> payload) {
        logger.info("Received POST request");
        HashMap<String,String> request = new HashMap<>(payload);
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        Guest newGuest = new Guest();
        try{
            newGuest = GuestService.create(request,guest);
            newGuest.setPassword(bCryptPasswordEncoder.encode(newGuest.getPassword()));
            guestRepository.save(newGuest);
            }
        catch (ServiceRuntimeException e){
            logger.error(e.getMessage(),e.fillInStackTrace());
            return new Output().Wrong(HttpStatus.BAD_REQUEST,"User already exist");
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex.fillInStackTrace());
        }
        return new Output().Correct(HttpStatus.OK,newGuest,"successfully added");
    }

    /*@PostMapping("/signIn")
    public Object guestLogin(@Valid @RequestBody Map<String,String> payload){
        logger.info("Received POST request");
        HashMap<String,String> request = new HashMap<>(payload);
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        if (guest == null) {
            throw new UsernameNotFoundException(request.get("emailAddress"));
        }

        return new User(guest.getEmailAddress(), guest.getPassword(), emptyList());
    }*/

    //get a single guest find by id
    @GetMapping("/guests/{id}")
    public ResponseEntity<Object> getGuestById(@PathVariable(value = "id") Long guestId) {
        //Guest guest= new Guest();
        //Guest guest = guestRepository.findOne(guestId);
        Guest guest = guestRepository.findOne(guestId);
        if (guest==null)
        {
            throw new ServiceRuntimeException("error");
            //return new Output().Wrong(HttpStatus.NOT_FOUND,"user not found");
        }
        else
            return new Output().Correct(HttpStatus.OK,guest,"successfully founded");
    }
    //update a guest
    @PutMapping("/guests/{id}")
    public ResponseEntity updateGuest(@PathVariable(value = "id") Long guestId,
                           @Valid @RequestBody Guest guest) {

        Guest updateguest = guestRepository.findOne(guestId);

        if(updateguest==null) {
            logger.info("User not exist");
            return new Output().Wrong(HttpStatus.NOT_FOUND,"user not exist");
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
            return new Output().Correct(HttpStatus.OK,updateguest,"successfully");
        }
    }

    //delete a guest
    @DeleteMapping("/guests/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable(value = "id") Long noteId) {
        Guest guest = guestRepository.findOne(noteId);

        if (guest == null) return new Output().Wrong(HttpStatus.BAD_REQUEST,"fail");
        else {
            guestRepository.delete(guest);
            return new Output().Correct(HttpStatus.OK,"successful");
        }
    }

}
