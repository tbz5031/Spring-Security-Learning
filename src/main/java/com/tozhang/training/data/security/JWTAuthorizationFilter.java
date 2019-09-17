package com.tozhang.training.data.security;

import com.tozhang.training.util.ServiceRuntimeException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.tozhang.training.data.security.SecurityConstants.*;

@Component
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Autowired
    public JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        logger.info(req.getRequestURL().toString()+ " Before JWTauthorization filter");
        UsernamePasswordAuthenticationToken authentication = null;
        try{
            authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(authentication==null) throw new ServiceRuntimeException("Invalid Access token");

            logger.info(req.getRequestURL().toString()+ " after JWTauthorization filter");
        }catch (Exception e){
            //res.setStatus(HttpServletResponse.SC_UNAUTHORIZED, e.getLocalizedMessage());
            logger.error("Could not set user authentication in security context", e);
        }
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
                throw new ServiceRuntimeException("Invalid Access token",s.fillInStackTrace());
            }catch(ArrayIndexOutOfBoundsException ae){
                logger.error("Invalid access Token");
                throw new ServiceRuntimeException("Invalid Access token");
            }catch (MalformedJwtException me) {
                logger.error("Invalid access Token");
                throw new ServiceRuntimeException("Invalid Access token");
            }catch (io.jsonwebtoken.ExpiredJwtException ee){
                logger.error("Token expired");
                return null;
                //throw new ServiceRuntimeException("Invalid Access token");
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
