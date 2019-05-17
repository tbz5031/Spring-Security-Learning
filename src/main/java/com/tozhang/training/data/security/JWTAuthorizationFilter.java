package com.tozhang.training.data.security;

import com.tozhang.training.util.Constant;
import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.tozhang.training.data.security.SecurityConstants.*;

@Component
@Order(2)
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (NullPointerException e) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ServiceRuntimeException {
        String token = request.getHeader(HEADER_STRING);
        String user = null;
        if (token != null) {
            // parse the token.
            try {
                user = Jwts.parser().setSigningKey(GuestSECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();
            }
            catch (io.jsonwebtoken.SignatureException s){
                logger.error("Invalid access Token");
                throw new ServiceRuntimeException("Invalid Access token");
            }catch(ArrayIndexOutOfBoundsException ae){
                logger.error("Invalid access Token");
                throw new ServiceRuntimeException("Invalid Access token");
            }catch (MalformedJwtException me){
                logger.error("Invalid access Token");
                throw new ServiceRuntimeException("Invalid Access token");
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
