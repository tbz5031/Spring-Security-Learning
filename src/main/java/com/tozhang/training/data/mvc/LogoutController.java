package com.tozhang.training.data.mvc;

import com.tozhang.training.data.security.WebSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unused")
@Controller
public class LogoutController implements LogoutSuccessHandler {

    @Autowired
    private WebSecurity webSecurity;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
        logger.debug("Performing logout");
        invalidateSession(req);
        String returnTo = req.getScheme() + "://" + req.getServerName();
        if ((req.getScheme().equals("http") && req.getServerPort() != 80) || (req.getScheme().equals("https") && req.getServerPort() != 443)) {
            returnTo += ":" + req.getServerPort();
        }
        returnTo += "/login";
        String logoutUrl = String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                webSecurity.getDomain(),
                webSecurity.getClientId(),
                returnTo);
        try {
            res.sendRedirect(logoutUrl);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void invalidateSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

}