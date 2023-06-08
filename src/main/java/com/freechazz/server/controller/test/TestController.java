package com.freechazz.server.controller.test;


import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.ESize;
import com.freechazz.game.core.Pos;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.player.Player;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import com.freechazz.server.DTO.JwtResponse;
import com.freechazz.server.EPlayerType;
import com.freechazz.server.MatchManager;
import com.freechazz.server.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com","http://localhost:3000"})
@RestController
@RequestMapping("api/test")
@Slf4j
public class TestController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private MatchManager server;

    private Game game;

    @GetMapping("newgame")
    public ResponseEntity<String> newGame(){
        User user1 = new User("bernd");
        User user2 = new User("tom");
        Formation f1 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.medium,user1).generate();
        Formation f2 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.medium,user2).generate();

        Game game = new GameBuilder(f1, f2)
                .botP1(new BetterBot2(EPlayer.P1,2,(long)(Math.random()*23312)))
                .botP2(new BetterBot2(EPlayer.P2,2,(long)(Math.random()*351241)))
                .randomStarter()
                .build();
        log.info("new TESTGame: " + game.getGameId());
        return ResponseEntity.ok("GameId:" + game.getGameId());
    }


    @GetMapping("play/{x1}/{y1}/{x2}/{y2}")
    public ResponseEntity<String> newGame(@PathVariable int x1, @PathVariable int y1, @PathVariable int x2, @PathVariable int y2){
        game.play(new Pos(x1,y1),new Pos(x2,y2));
        return ResponseEntity.ok("GameId:" + game.getGameId());
    }

    @GetMapping("play")
    public ResponseEntity<String> doBotAction(){
        game.botAction();
        return ResponseEntity.ok("GameId:" + game.getGameId());
    }







}
