package com.freechazz.security;

import com.freechazz.network.security.JwtUtils;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class JwtTest {

    private final String jwtSecret = "yourSecretKeyyourSecretKeyyourSecretKeyyourSecretKey"; // Replace with your actual secret key
    private final long jwtExpirationMs = System.currentTimeMillis() + 9405483;

    @Test
    public void testGenerateJwtToken() {
        JwtUtils jwtUtils = new JwtUtils(jwtSecret, jwtExpirationMs);

        String username = "testUser";
        UUID userId = UUID.randomUUID();

        String jwtToken = jwtUtils.generateJwtToken(username, userId);


        assertNotNull(jwtToken);
        assertTrue(jwtUtils.validateJwtToken(jwtToken));
        assertEquals(username, jwtUtils.getUsernameFromJwtToken(jwtToken));
        assertEquals(userId, jwtUtils.getUserIdFromJwtToken(jwtToken));


        //now header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        assertTrue(jwtUtils.validate(headers));
        assertEquals(username, jwtUtils.getUsername(headers));
        assertEquals(userId, jwtUtils.getUserId(headers));
    }

    /*
        @Test
        public void testValidateJwtToken() {
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setJwtSecret(jwtSecret);

            // Generate a valid token
            String validToken = generateValidToken();

            // Validate the valid token
            assertTrue(jwtUtils.validateJwtToken(validToken));

            // Generate an expired token
            String expiredToken = generateExpiredToken();

            // Validate the expired token
            assertFalse(jwtUtils.validateJwtToken(expiredToken));
        }

        @Test
        public void testValidate() {
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setJwtSecret(jwtSecret);

            // Generate a valid token
            String validToken = generateValidToken();

            // Create HttpHeaders with the valid token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + validToken);

            // Validate the token in HttpHeaders
            assertTrue(jwtUtils.validate(headers));
        }
    */
    private String generateValidToken() {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject("testUser")
                .claim("userId", UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private String generateExpiredToken() {
        Date expirationDate = new Date(System.currentTimeMillis() - 1000); // Set expiration in the past

        return Jwts.builder()
                .setSubject("testUser")
                .claim("userId", UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
