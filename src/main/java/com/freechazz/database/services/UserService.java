package com.freechazz.database.services;

import com.freechazz.database.entities.UserEntity;
import com.freechazz.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createMockUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(UUID.randomUUID()); // Assuming UUID is generated
        userEntity.setUsername("mockUser");
        userEntity.setMoney(1000); // Set some initial money amount

        userRepository.save(userEntity);
        return userEntity;
    }

    public Optional<UserEntity> getUserById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID uuid) {
        userRepository.deleteById(uuid);
    }
}

