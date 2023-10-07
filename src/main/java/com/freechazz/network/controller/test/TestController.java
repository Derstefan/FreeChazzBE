package com.freechazz.network.controller.test;


import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.ESize;
import com.freechazz.game.core.Pos;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import com.freechazz.generators.piece.PieceTypeGenerator;
import com.freechazz.network.DTO.game.server.PieceDTO;
import com.freechazz.network.DTO.game.server.PieceTypeDTO;
import com.freechazz.network.DTO.game.server.PieceTypeDTOCollection;
import com.freechazz.network.DTO.game.server.UpdateDataDTO;
import com.freechazz.network.MatchManager;
import com.freechazz.network.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
        Formation f1 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.big,user1).generate();
        Formation f2 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.big,user2).generate();

        game = new GameBuilder(f1, f2)
                //.botP1(new BetterBot2(EPlayer.P1,2,(long)(Math.random()*23312)))
               // .botP2(new BetterBot2(EPlayer.P2,2,(long)(Math.random()*351241)))
                .randomStarter()
                .build();
        log.info("new TESTGame: " + game.getGameId());
        UpdateDataDTO updateData = new UpdateDataDTO(game,0);


        return ResponseEntity.ok(updateData);
    }

    @GetMapping("newbotgame")
    public ResponseEntity<UpdateDataDTO> newBotGame(){
        User user1 = new User("bernd");
        User user2 = new User("tom");
        Formation f1 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.big,user1).generate();
        Formation f2 = new FormationGenerator((long)(Math.random()*Long.MAX_VALUE), ESize.big,user2).generate();

        game = new GameBuilder(f1, f2)
                //.botP1(new BetterBot2(EPlayer.P1,2,(long)(Math.random()*23312)))
                .botP2(new BetterBot2(EPlayer.P2,2,(long)(Math.random()*351241)))
                .randomStarter()
                .build();
        log.info("new TESTGame: " + game.getGameId());
        UpdateDataDTO updateData = new UpdateDataDTO(game,0);
        if(game.getPlayersTurn()==EPlayer.P2){
            game.botAction();
        }

        return ResponseEntity.ok(updateData);
    }


    @GetMapping("update/{turn}")
    public UpdateDataDTO checkUpdate(@PathVariable int turn){
        if(game==null){
            return null;
        }
        UpdateDataDTO updateData = new UpdateDataDTO(game,turn);

        return updateData;
    }

    @GetMapping("play/{x1}/{y1}/{x2}/{y2}")
    public UpdateDataDTO play(@PathVariable int x1, @PathVariable int y1, @PathVariable int x2, @PathVariable int y2){
        //log.info("play: " + x1 + " " + y1 + " " + x2 + " " + y2);
        game.play(new Pos(x1,y1),new Pos(x2,y2));
        //game.computePossibleMoves();

        //log.info(game.getState().toString());
        UpdateDataDTO updateData = new UpdateDataDTO(game,game.getTurns());
        if(updateData.getWinner()==""){
            game.botAction();
        }
        return updateData;
    }



    @GetMapping("loadAllPieceTypes/{turn}")
    public ResponseEntity<PieceTypeDTOCollection> loadAllPieceTypes(@PathVariable int turn){
        PieceTypeGenerator pieceTypeGenerator = new PieceTypeGenerator();
        ArrayList<PieceTypeDTO> pieceTypeDTOS = new ArrayList<>();
        for (PieceDTO pieceDTO:
        game.getState().getHistory().getHistoryState(turn).getPieceDTOs()) {
           // log.info("pieceTypeBy...: " + pieceDTO.getPieceTypeId().getSeed());
            pieceTypeDTOS.add(new PieceTypeDTO(pieceTypeGenerator.generate(pieceDTO.getPieceTypeId())));
        }
        return ResponseEntity.ok(new PieceTypeDTOCollection(pieceTypeDTOS));
    }

    @GetMapping("bot")
    public ResponseEntity<String> doBotAction(){
        game.botAction();
        return ResponseEntity.ok("GameId:" + game.getGameId());
    }
}
