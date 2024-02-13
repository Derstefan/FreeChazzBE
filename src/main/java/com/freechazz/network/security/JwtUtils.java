package com.freechazz.network.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


//import io.jsonwebtoken.*;


@Component
public class JwtUtils {


    @Value("${freechess.app.jwtSecret}")
    private String jwtSecret;

    private long jwtExpirationMs = 86400000;

    public JwtUtils(String jwtSecret, long jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public JwtUtils() {

    }

    public String generateJwtToken(String username, UUID userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public boolean validate(HttpHeaders headers) {
        return validateJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    public UUID getUserId(HttpHeaders headers) {
        return getUserIdFromJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    public String getUsername(HttpHeaders headers) {
        return getUsernameFromJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    private String parseJwtToken(String value) {
        return value.substring(8, value.length() - 1);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false; // Token has expired
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public UUID getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.get("userId", String.class));
    }

    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

/*
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


    public boolean validate(HttpHeaders headers) {
        return validateJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    public String parseJwtToken(String value) {
        return value.substring(8, value.length() - 1);
    }*/
}