package com.tozhang.training.data.security;

import com.tozhang.training.data.webservice.GuestController;
import com.tozhang.training.util.ServiceRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.Map;

import static com.tozhang.training.data.security.SecurityConstants.EXPIRATION_TIME;
import static com.tozhang.training.data.security.SecurityConstants.SECRET;

public class JWTService {
    private static final Logger logger = Logger.getLogger(JWTService.class);
    public static String jwtIssuer(Map<String,String> payload){
        String token = null;
        token = Jwts.builder()
                .setId(payload.get("emailAddress"))
                .setSubject(payload.get("account"))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        logger.info("token is successfully issued" );
        return token;
    }
    public static boolean jwtValidator(Map<String,String> header,Map<String,String> param){
        String token = header.get("authorization").split(" ")[1];
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }
        catch (io.jsonwebtoken.SignatureException s) {
            logger.error("Invalid access Token");
            logger.error(s, s.fillInStackTrace());
            return false;
        }catch(ArrayIndexOutOfBoundsException ae){
            logger.error("Invalid access Token");
            logger.error(ae,ae.fillInStackTrace());
            return false;
        }catch (MalformedJwtException me){
            logger.error("Invalid access Token");
            logger.error(me,me.fillInStackTrace());
            return false;
        }catch (NullPointerException ne){
            logger.error("Please Provide correct token");
            logger.error(ne,ne.fillInStackTrace());
        }
        if(claims.getId().equals(param.get("emailAddress"))&&claims.getSubject().equals(param.get("account")))
            return true;
        else
            return false;
    }
}
