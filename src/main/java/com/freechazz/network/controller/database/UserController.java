package com.freechazz.network.controller.database;

import com.freechazz.database.entities.UserEntity;
import com.freechazz.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/createMockUser")
    public UserEntity createMockUser() {
        return userService.createMockUser();
    }

    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    // Other controller methods
}
