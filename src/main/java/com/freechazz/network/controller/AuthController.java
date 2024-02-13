package com.freechazz.network.controller;

import com.freechazz.database.entities.UserEntity;
import com.freechazz.database.services.UserService;
import com.freechazz.network.security.JwtUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    public static final long expirationTime = System.currentTimeMillis() + 3600 * 1000;
    private final UserService userService;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegistrationRequest registrationRequest) {

        String username = registrationRequest.getUsername();
        String password = registrationRequest.getPassword();
        String email = registrationRequest.getEmail();
        log.info("registeredUser: " + username);

        UserEntity registeredUser = userService.registerUser(username, password, email);

        if (registeredUser != null) {
            String jwtToken = jwtUtils.generateJwtToken(username, registeredUser.getUuid());
            return new ResponseEntity<>(new UserResponse(registeredUser.getUuid(), registeredUser.getUsername(), registeredUser.getEmail(), jwtToken), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new UserResponse().withMessage("Username or email already taken"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        log.info("loggedInUser: " + username);
        Optional<UserEntity> loggedInUser = userService.loginUser(username, password);


        if (loggedInUser.isPresent()) {
            String jwtToken = jwtUtils.generateJwtToken(username, loggedInUser.get().getUuid());
            return new ResponseEntity<>(new UserResponse(loggedInUser.get().getUuid(), loggedInUser.get().getUsername(), loggedInUser.get().getEmail(), jwtToken), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserResponse().withMessage("Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
    }

    //get all users TEst : TODO: remove!!!
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/check")
    public ResponseEntity<?> checkToken(@RequestHeader HttpHeaders headers) {
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(user.get().getUsername());
    }

    @Data
    static class RegistrationRequest {
        private String username;
        private String password;
        private String email;
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    static class UserResponse {
        private UUID userId;
        private String username;
        private String email;
        private String token;
        private String message = "";


        public UserResponse(UUID userId, String username, String email, String token) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.token = token;
        }

        public UserResponse() {
        }

        public UserResponse withMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
