package com.tozhang.training.data.security;

import com.tozhang.training.data.repository.AdminRepository;
import com.tozhang.training.data.repository.GuestRepository;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.tozhang.training.data.security.SecurityConstants.EXPIRATION_TIME;

@Service
public class JWTService {
    private static final Logger logger = Logger.getLogger(JWTService.class);
    @Autowired
    private static GuestRepository guestRepository;

    @Autowired
    private static AdminRepository adminRepository;

    @Autowired
    public void setUserRepo(GuestRepository guestRepository) {
        JWTService.guestRepository = guestRepository;
    }
    @Autowired void setAdminRepo(AdminRepository adminRepository){
        JWTService.adminRepository = adminRepository;
    }

    public String jwtIssuer(Map<String,Object> payload, String secret){
        String token = null;
        token = Jwts.builder()
                .setHeader(payload)
                .setId(payload.get("emailAddress").toString())
                .setSubject(payload.get("account").toString())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        logger.info("token is successfully issued" );
        return token;
    }
    public boolean jwtValidator(Map<String,String> reqheader,Map<String,String> param, String secret){
        Claims claims = null;
        JWTService jwtService = new JWTService();
        try {
            String token = reqheader.get("authorization").split(" ")[1];
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if(claims.getId().equals(param.get("emailAddress"))&&claims.getSubject().equals(param.get("account"))&&jwtService.decodeLoginTs(reqheader,param,secret))
                return true;
            else
                return false;
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
            return false;
        }

    }
    public boolean decodeLoginTs(Map<String,String> reqheader,Map<String,String> param,String secret){
        try {
            Object loginTsDb = adminRepository.findByEmailAddress(param.get("emailAddress")).getLoginTs();
            String token = reqheader.get("authorization").split(" ")[1];
            Object LoginTs = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getHeader().get("LoginTs");
            if (loginTsDb.equals(LoginTs)) return true;
            else return false;
        }
        catch (NullPointerException e){
            logger.error(e,e.fillInStackTrace());
            return false;
        }
    }
}