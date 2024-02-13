package com.freechazz.network.controller;


import com.freechazz.database.entities.MatchEntity;
import com.freechazz.database.services.MatchService;
import com.freechazz.network.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com", "http://localhost:3000"})
@RestController
@RequestMapping("api/")
public class ServerController {

    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    private MatchService matchService;


    @GetMapping
    public ResponseEntity<List<MatchEntity>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }


    @GetMapping("check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("Server is running");
    }
}
