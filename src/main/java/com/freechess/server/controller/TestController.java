package com.freechess.server.controller;

import java.util.List;
import com.freechess.server.DTO.GameData;
import com.freechess.server.repository.GameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @Autowired
    private GameDataRepository gameDataRepository;

    @GetMapping("/gameData")
    public List<GameData> getAllEmployees() {
        return gameDataRepository.findAll();
    }
}
