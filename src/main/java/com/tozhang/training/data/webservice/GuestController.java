package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.repository.GuestRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.security.SecurityConstants;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.util.GuestUtil;
import com.tozhang.training.data.errorHandling.IDMException;
import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/guest")
public class GuestController {
    private static final Logger logger = LogManager.getLogger(GuestController.class);

    private GuestController guestController;
    @Autowired
    public void setGuestController(GuestController guestController ) {
        this.guestController = guestController;
    }
    @Autowired
    JWTService jwtService;

    @Autowired
    GuestRepository guestRepository;


    //Get all guest
    @GetMapping("/guests")
    public ResponseEntity<Object> getAllGuests(@RequestParam Map<String,String> allParams,
                                               @RequestHeader Map<String,String> header) {
        List<Guest> ls_guests = null;
        if(jwtService.jwtValidator(header,allParams,SecurityConstants.GuestSECRET)){
            ls_guests = guestRepository.findAll();
            if (ls_guests==null)
                return new IDMResponse().Correct(HttpStatus.OK,null,"Successful");
            else
                return new IDMResponse().Correct(HttpStatus.OK,ls_guests,"successfully founded");
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid access token");
    }

    //create a new guest test
    @PostMapping("/signUp")
    public Object createGuest(@Valid @RequestBody Map<String,String> payload) {
        logger.info("SignUp Process");
        HashMap<String,String> request = new HashMap<>(payload);
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        Guest newGuest;
        try{
            newGuest = GuestService.createnewguest(request,guest);
            guestRepository.save(newGuest);
            }
        catch (ServiceRuntimeException e){
            logger.error(e.getMessage(),e.fillInStackTrace());
            throw new IDMException("User Already Exists");
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex.fillInStackTrace());
            throw new IDMException("Other error");
        }
        return new IDMResponse().Correct(HttpStatus.OK, newGuest,"successfully added");
    }
    public Guest callBackSignUp(ResponseEntity response) throws IOException{
        logger.info("call back signUp process");
        ResponseEntity res = response;
        HashMap<String, Object> map = (HashMap<String, Object>) GuestUtil.mappingHelper(res.getBody());
        String account = map.get("email").toString().substring(0,map.get("email").toString().indexOf("@"));
        Guest newguest = new Guest.Builder(map.get("email").toString(),account).status("inactive")
                .password("default").createdTs(System.currentTimeMillis()).build();
//        newguest.setAccount(map.get("nickname").toString());
//        newguest.setEmailAddress(map.get("email").toString());
//        newguest.setStatus("inactive");
//        newguest.setPassword("Here it is");
//        newguest.setCreatedTs(System.currentTimeMillis());
        guestRepository.save(newguest);
        logger.info("Successfully added new user");
        return newguest;
    }

    @PostMapping("/signIn")
    public Object guestLogin(@Valid @RequestBody Map<String,String> payload) throws IOException {
        logger.info("SignIn Process");
        jwtService = new JWTService();
        HashMap<String,String> request = new HashMap<>(payload);
        String token = null;
        String refresh_token = null;
        Guest guest = guestRepository.findByEmailAddress(request.get("emailAddress"));
        // transfer guest object to hashmap
        Map<String, Object> result = null;
        if(guest!=null && guest.getPassword().equals(payload.get("password"))){
             guest = GuestService.updateLoginTimeAndStatus(guest);
             guestRepository.save(guest);
             result = GuestUtil.mappingHelper(guest);
             result.put("LoginTs",guest.getLoginTs());
             token = jwtService.jwtIssuer(result,SecurityConstants.GuestSECRET);
             result.put("accessToken",token);
             //todo Need to implement refreshtoken.
             //refresh_token = JWTService.jwtIssuer()
        }
        else {
            throw new IDMException("Password is wrong");
            //return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Not valid credential or username");
        }


        return new IDMResponse().Correct(HttpStatus.OK,result,"Login Successfully");
    }





    public ResponseEntity callBackLogin(ResponseEntity response) throws IOException {
        logger.info("SignIn Process");
        ResponseEntity res = response;
        HashMap<String, Object> map = (HashMap<String, Object>) GuestUtil.mappingHelper(res.getBody());
        Guest guest = guestRepository.findByEmailAddress(map.get("email").toString());
        Map<String, Object> result = null;
        String token = null;
        if(guest==null) guest = guestController.callBackSignUp(response);
        guest = GuestService.updateLoginTimeAndStatus(guest);
        guestRepository.save(guest);
        result = GuestUtil.mappingHelper(guest);
        result.put("LoginTs",guest.getLoginTs());
        token = jwtService.jwtIssuer(result,SecurityConstants.GuestSECRET);
        result.put("accessToken",token);
        //todo Need to implement refreshtoken.
        //refresh_token = JWTService.jwtIssuer()

        return new IDMResponse().Correct(HttpStatus.OK,result,"Successfully logged in");
    }

    //get a single guest find by id
    @GetMapping("")
    public Object getGuestById(@RequestParam Map<String,String> allParams,
                                               @RequestHeader Map<String,String> header) {
        logger.info("Get Guest Process");
        jwtService = new JWTService();
        Guest guest = null;
        logger.info("something wrong");
        if(jwtService.jwtValidator(header,allParams,SecurityConstants.GuestSECRET)){
            guest = guestRepository.findByEmailAddress(allParams.get("emailAddress"));
            if (guest==null)
                return new IDMResponse().Correct(HttpStatus.OK,null,"Successful");
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

        if(jwtService.jwtValidator(header,allParams, SecurityConstants.GuestSECRET)) {
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

        if(jwtService.jwtValidator(header,allParams,SecurityConstants.GuestSECRET)) {
            Guest tempguest = guestRepository.findByEmailAddress(allParams.get("emailAddress"));
            if (tempguest == null) {
                logger.info("User does not exist");
                return new IDMResponse().Wrong(HttpStatus.NOT_FOUND, "user not exist");
            } else {
                tempguest.setStatus("inactive");
                tempguest.setLogoutTs(System.currentTimeMillis());
                guestRepository.save(tempguest);
                return new IDMResponse().Correct(HttpStatus.OK, "successfully Delete");
            }
        }else
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid access token");
    }

}
