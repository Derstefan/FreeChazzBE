package com.freechazz.server.controller;


import com.freechazz.server.DTO.GameData;
import com.freechazz.server.DTO.ServerData;
import com.freechazz.server.security.JwtUtils;
import com.freechazz.server.MatchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com","http://localhost:3000"})
@RestController
@RequestMapping("api/")
public class ServerController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private MatchManager server;

    // get some data from server TODO: Maybe better metrics
    @GetMapping("serverdata")
    public ResponseEntity<ServerData> getServerData(){
        return ResponseEntity.ok(new ServerData(server));
    }

    @GetMapping("activegames")
    public ResponseEntity<ArrayList<GameData>> getActiveGames(){

        return ResponseEntity.ok(server.getGameDataList());
    }
}
