package com.tozhang.training.data.mvc;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;

import com.auth0.jwt.JWT;
import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.security.TokenAuthentication;
import com.tozhang.training.data.security.WebSecurity;
import com.tozhang.training.data.service.AdminService;
import com.tozhang.training.data.webservice.GuestController;
import com.tozhang.training.util.IDMResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
@Controller
public class CallbackController {
    private static final Logger logger = LogManager.getLogger(CallbackController.class);

    @Autowired
    private AuthenticationController controller;
    @Autowired
    private GuestController guestController;
    @Autowired
    public void setGuestController(GuestController guestController ) {
        this.guestController = guestController;
    }

    private final String redirectOnFail;
    private final String redirectOnSuccess;
    private WebSecurity webSecurity;

    public CallbackController() {
        this.redirectOnFail = "/login";
        this.redirectOnSuccess = "/guest/signIn";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected ResponseEntity getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        logger.info("get call back");
        ResponseEntity result = handle(req, res);
        return result;
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        logger.info("post call back");
        handle(req, res);
    }

    private ResponseEntity<Object> handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Tokens tokens = controller.handle(req);
            TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);

            String redirectUri = req.getScheme() + "://" + req.getServerName();
            if ((req.getScheme().equals("http") && req.getServerPort() != 80) || (req.getScheme().equals("https") && req.getServerPort() != 443)) {
                redirectUri += ":" + req.getServerPort();
            }

            final String uri = "https://tozhang.auth0.com/userinfo";
            final HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+tokens.getAccessToken());
            RestTemplate restTemplate = new RestTemplate();
            //Create a new HttpEntity
            final HttpEntity<String> entity = new HttpEntity<String>(headers);

            //Execute the method writing your HttpEntity to the request
            ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
            return guestController.callBackLogin(response);
//            res.sendRedirect(redirectOnSuccess);
        } catch (AuthenticationException | IdentityVerificationException e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            res.sendRedirect(redirectOnFail);
        }
        return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"There is something wrong");
    }
}