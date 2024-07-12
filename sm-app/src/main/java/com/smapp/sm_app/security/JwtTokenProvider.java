package com.smapp.sm_app.security;

import com.smapp.sm_app.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${SMAPP_SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${SMAPP_EXPIRES_IN}")
    private long EXPIRES_IN;

    private SecretKey secretKeyBytes ;

    @PostConstruct
    public void init(){
        this.secretKeyBytes = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String createJwtToken(User user) {
        Date expiryDate = new Date(System.currentTimeMillis() + EXPIRES_IN);
        String token = Jwts
                .builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(secretKeyBytes)
                .compact();
        return token;
    }

    public User verifyToken(String authorizationHeader){
        String token = authorizationHeader.split(" ")[1];
        try {
                SecretKey secretKeyBytes = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                Jws<Claims> claims = Jwts
                        .parser()
                        .verifyWith(secretKeyBytes)
                        .build()
                        .parseSignedClaims(token);

                String stringId = claims.getPayload().getSubject();
                User user = new User();
                user.setId(Long.valueOf(stringId));
                return user;
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                     IllegalArgumentException ex) {
                throw new JwtException(ex.getMessage());
            }
    }



//    public Long verifyToken(String authorizationHeader) {
//
//        if (authorizationHeader.contains(" ") && authorizationHeader.split(" ")[0].equals("Bearer")) {
//            String token = null;
//            token = authorizationHeader.split(" ")[1];
//            try {
//                SecretKey secretKeyBytes = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//                Jws<Claims> claims = Jwts
//                        .parser()
//                        .verifyWith(secretKeyBytes)
//                        .build()
//                        .parseSignedClaims(token);
//
//                String stringId = claims.getPayload().getSubject();
//                return Long.valueOf(stringId);
//            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
//                     IllegalArgumentException ex) {
//                throw new JwtException(ex.getMessage());
//            }
//        }else {
//            throw new JwtException("Invalid token");
//        }
//    }


}
