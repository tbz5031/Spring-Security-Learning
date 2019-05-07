package com.tozhang.training.data.security;

import com.tozhang.training.data.webservice.GuestController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;

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
        logger.info("token: " + token);
        return token;
    }
    public static boolean jwtValidator(Map<String,String> header,Map<String,String> param){
        String token = header.get("authorization").split(" ")[1];
        System.out.println(token);
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        System.out.println(claims);
        if(claims.getId().equals(param.get("emailAddress"))&&claims.getSubject().equals(param.get("account")))
            return true;
        else
            return false;
    }
}
