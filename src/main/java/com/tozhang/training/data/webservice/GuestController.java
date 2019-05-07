package com.tozhang.training.data.webservice;
import com.google.gson.Gson;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import static com.tozhang.training.data.security.SecurityConstants.EXPIRATION_TIME;
import static com.tozhang.training.data.security.SecurityConstants.SECRET;
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
        List<Guest> ls_guests = guestRepository.findAll();
        return new IDMResponse().Correct(HttpStatus.OK, ls_guests, "Successful");
    }

    //create a new guest test
    @PostMapping("/signUp")
    public Object createGuest(@Valid @RequestBody Map<String,String> payload) {
        logger.info("Received POST request");
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
        logger.info("Received SignIn");

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
    public ResponseEntity<Object> getGuestById(@RequestParam Map<String,String> allParams,
                                               @RequestHeader Map<String,String> header) {
        logger.info("Start to process GET method");
        System.out.println(allParams);
        System.out.println(header);
        if(JWTService.jwtValidator(header,allParams)){
            Guest guest = guestRepository.findByEmailAddress(allParams.get("emailAddress"));
            if (guest==null)
            {
                throw new ServiceRuntimeException("Could not find User");
                //return new IDMResponse().Wrong(HttpStatus.NOT_FOUND,"user not found");
            }
            else
                return new IDMResponse().Correct(HttpStatus.OK,guest,"successfully founded");
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid access token");
    }
    //update a guest
    @PutMapping("/guests/{id}")
    public ResponseEntity updateGuest(@PathVariable(value = "id") Long guestId,
                           @Valid @RequestBody Guest guest) {

        Guest updateguest = guestRepository.findOne(guestId);

        if(updateguest==null) {
            logger.info("User not exist");
            return new IDMResponse().Wrong(HttpStatus.NOT_FOUND,"user not exist");
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
            return new IDMResponse().Correct(HttpStatus.OK,updateguest,"successfully");
        }
    }

    //delete a guest
    @DeleteMapping("/guests/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable(value = "id") Long noteId) {
        Guest guest = guestRepository.findOne(noteId);

        if (guest == null) return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"fail");
        else {
            guestRepository.delete(guest);
            return new IDMResponse().Correct(HttpStatus.OK,"successful");
        }
    }

}
