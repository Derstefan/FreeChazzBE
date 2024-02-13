package com.freechazz.database.services;

import com.freechazz.database.entities.UserEntity;
import com.freechazz.database.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String BOT_USERNAME = "Ferdinant";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserEntity> getUserById(UUID uuid) {
        //exclude password
        Optional<UserEntity> userEntity = userRepository.findById(uuid);
        if (userEntity.isPresent()) {
            // userEntity.get().setPassword(null);
        }
        return userEntity;
    }


    public List<UserEntity> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity userEntity : userEntities) {
            //userEntity.setPassword(null); //TODO add this
        }
        return userEntities;
    }

    public void deleteUser(UUID uuid) {
        userRepository.deleteById(uuid);
    }


    public UserEntity registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            log.warn("Username or email already taken");
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(UUID.randomUUID());
        userEntity.setUsername(username);
        userEntity.setEmail(email);

        String hashedPassword = passwordEncoder.encode(password);
        userEntity.setPassword(hashedPassword);

        userRepository.save(userEntity);

        //  userEntity.setPassword(null);

        return userEntity;
    }

    public Optional<UserEntity> loginUser(String username, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(password, optionalUser.get().getPassword())) {

                // optionalUser.get().setPassword(null);
                return optionalUser;
            }
        }
        return Optional.empty();
    }


    //get or create bot user
    public UserEntity getOrCreateBotUser() {
        Optional<UserEntity> userEntity = userRepository.findByUsername(BOT_USERNAME);
        if (userEntity.isEmpty()) {
            UserEntity newBotUser = new UserEntity(BOT_USERNAME);
            userRepository.save(newBotUser);
            return newBotUser;
        }
        return userEntity.get();
    }

}

