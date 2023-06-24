package com.freechazz.network.controller;

import com.freechazz.network.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com","http://localhost:3000"})
@RestController
@RequestMapping("api/match/")
@Slf4j
public class MatchController {



    @Autowired
    JwtUtils jwtUtils;



    /*
    @Autowired
    private MatchManager matchManager;

    @PostMapping("play/{gameId}")
    public ResponseEntity<String> play(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId, @RequestBody DrawData draw){


        if(jwtUtils.validate(headers)) {
            Game
            //headers get userId
//            if(userId is player in gameId)

            matchManager.play(gameId,userId,draw);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(401).body(null);
    }

    //surrender
    @GetMapping("surrender/{gameId}")
    public ResponseEntity<String> surrender(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId){
        if(jwtUtils.validate(headers,gameId)) {
            //headers get userId
//            if(userId is player in gameId)

            matchManager.surrender(gameId,userId);
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(401).body(null);
    }



    //checkForUpdate (turn) -> get last events
    //TODO: returns a delta gamestate (DeltaGameState??)
    @GetMapping("check/{gameId}")
    public ResponseEntity<ArrayList<DrawEvents>> check(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId, @PathVariable int turn){
        if(jwtUtils.validate(headers)) {
            //headers get userId
//            if(userId is player in gameId)

            return ResponseEntity.ok(matchManager.getDrawEventsSince(gameId,turn));
        }
        return ResponseEntity.status(401).body(null);
    }



    //getFullGameData get full GameData
    //TODO: retiurns a full gamestate (GameState??)
    @GetMapping("get/{gameId}")
    public ResponseEntity<GameState>


    */

}
