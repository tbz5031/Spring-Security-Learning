package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/guest")
public class GuestController {
    private static final Logger logger = Logger.getLogger(GuestController.class);

    @Autowired
    GuestRepository guestRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //private WebRequest ex;

    //Get all guest
    @GetMapping("/guests")
    public ResponseEntity<Object> getAllGuests() {
        List<Guest> ls_guests = guestRepository.findAll();
        return new IDMResponse().Correct(HttpStatus.OK, ls_guests, "Successful");
    }

    //create a new guest test
    @PostMapping("/signUp")
    public Object createGuest(@Valid @RequestBody Map<String,String> payload) {
        logger.info("SignUp Process");
        HashMap<String,String> request = new HashMap<>(payload);
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        Guest newGuest = new Guest();
        try{
            newGuest = GuestService.create(request,guest);
            //newGuest.setPassword(bCryptPasswordEncoder.encode(newGuest.getPassword()));
            guestRepository.save(newGuest);
            }
        catch (ServiceRuntimeException e){
            logger.error(e.getMessage(),e.fillInStackTrace());
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"User already exist");
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex.fillInStackTrace());
        }
        return new IDMResponse().Correct(HttpStatus.OK,newGuest,"successfully added");
    }

    @PostMapping("/signIn")
    public Object guestLogin(@Valid @RequestBody Map<String,String> payload) throws IOException {
        logger.info("SignIn Process");

        HashMap<String,String> request = new HashMap<>(payload);
        String token = null;
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        if(guest!=null && guest.getPassword().equals(payload.get("password"))){
             token = JWTService.jwtIssuer(request);
        }
        else return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Not valid credential or username");

        guest.setAccess_token(token);
        return new IDMResponse().Correct(HttpStatus.OK,guest,"Login Successfully");
    }

    //get a single guest find by id
    @GetMapping("")
    public Object getGuestById(@RequestParam Map<String,String> allParams,
                                               @RequestHeader Map<String,String> header) {
        logger.info("Get Guest Process");
        Guest guest = null;
        if(JWTService.jwtValidator(header,allParams)){
                guest = guestRepository.findByEmailAddress(allParams.get("emailAddress"));
            if (guest==null)
            {
                return new IDMResponse().Correct(HttpStatus.OK,null,"Successful");
            }
            else
                return new IDMResponse().Correct(HttpStatus.OK,guest,"successfully founded");
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid access token");
    }
    //update a guest
    @PutMapping("/update")
    public ResponseEntity updateGuest(@Valid @RequestBody Map<String,String> payload,
                                      @RequestHeader Map<String,String> header) {
        logger.info("Update User Process");

        HashMap<String,String> allParams = new HashMap<>(payload);

        if(JWTService.jwtValidator(header,allParams)) {
            Guest updateguest = guestRepository.findByEmailAddress(allParams.get("emailAddress"));
            if (updateguest == null) {
                logger.info("User not exist");
                return new IDMResponse().Wrong(HttpStatus.NOT_FOUND, "user not exist");
            } else {
                updateguest.setFirstName(allParams.get("firstName"));
                updateguest.setLastName(allParams.get("lastName"));
                updateguest.setEmailAddress(allParams.get("emailAddress"));
                updateguest.setAddress(allParams.get("address"));
                updateguest.setCountry(allParams.get("country"));
                updateguest.setState(allParams.get("state"));
                updateguest.setPhoneNumber(allParams.get("phoneNumber"));
                guestRepository.save(updateguest);
                return new IDMResponse().Correct(HttpStatus.OK, updateguest, "successfully");
            }
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid access token");
    }

    //delete a guest
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteGuest(@RequestParam Map<String,String> allParams,
                                         @RequestHeader Map<String,String> header) {
        logger.info("Delete User Process");

        if(JWTService.jwtValidator(header,allParams)) {
            Guest tempguest = guestRepository.findByEmailAddress(allParams.get("emailAddress"));
            if (tempguest == null) {
                logger.info("User does not exist");
                return new IDMResponse().Wrong(HttpStatus.NOT_FOUND, "user not exist");
            } else {
                guestRepository.delete(tempguest);
                return new IDMResponse().Correct(HttpStatus.OK, "successfully Delete");
            }
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid access token");
    }

}
