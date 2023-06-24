package com.freechazz.network.controller.test;


import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.ESize;
import com.freechazz.game.core.Pos;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import com.freechazz.network.DTO.game.server.UpdateDataDTO;
import com.freechazz.network.MatchManager;
import com.freechazz.network.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UpdateDataDTO> newGame(){
        User user1 = new User("bernd");
        User user2 = new User("tom");
        Formation f1 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.tiny,user1).generate();
        Formation f2 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.tiny,user2).generate();

        game = new GameBuilder(f1, f2)
                //.botP1(new BetterBot2(EPlayer.P1,2,(long)(Math.random()*23312)))
                //.botP2(new BetterBot2(EPlayer.P2,2,(long)(Math.random()*351241)))
                .randomStarter()
                .build();
        log.info("new TESTGame: " + game.getGameId());
        UpdateDataDTO updateData = new UpdateDataDTO(game,0);
        return ResponseEntity.ok(updateData);
    }


    @GetMapping("update/{turn}")
    public ResponseEntity<UpdateDataDTO> checkUpdate(@PathVariable int turn){
        if(game==null){
            return ResponseEntity.ok(null);
        }
        UpdateDataDTO updateData = new UpdateDataDTO(game,turn);
        //System.out.println(game.getState().toString());
        return ResponseEntity.ok(updateData);
    }

    @GetMapping("play/{x1}/{y1}/{x2}/{y2}")
    public ResponseEntity<String> play(@PathVariable int x1, @PathVariable int y1, @PathVariable int x2, @PathVariable int y2){
        log.info("play: " + x1 + " " + y1 + " " + x2 + " " + y2);
        game.play(new Pos(x1,y1),new Pos(x2,y2));
        return ResponseEntity.ok("GameId:" + game.getGameId());
    }

    @GetMapping("bot")
    public ResponseEntity<String> doBotAction(){
        game.botAction();
        return ResponseEntity.ok("GameId:" + game.getGameId());
    }







}
