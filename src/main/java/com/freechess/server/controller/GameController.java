package com.freechess.server.controller;


import com.freechess.game.player.EPlayer;
import com.freechess.game.Game;
import com.freechess.game.player.Player;
import com.freechess.game.player.bots.BetterBot;
import com.freechess.game.player.bots.Bot;
import com.freechess.server.DTO.DrawData;
import com.freechess.server.DTO.GameData;
import com.freechess.game.board.Board;
import com.freechess.server.DTO.GameParams;
import com.freechess.server.DTO.JwtResponse;
import com.freechess.server.security.JwtUtils;
import com.freechess.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com","http://localhost:3000"})
@RestController
@RequestMapping("api/")
@Slf4j
public class GameController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private Server server;

    @GetMapping("newgame/{name}")
    public ResponseEntity<JwtResponse> newGame(@PathVariable String name){
        Game game = server.createGame();
        Player player1 = new Player(name, EPlayer.P1);
        game.join(player1);

        UUID playerId = player1.getPlayerId();
        UUID gameId = game.getGameId();

        //generate Security Token
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId, game.getSeed(), playerId,jwt,EPlayer.P1);
        log.info(name + " started a new Game: " + game.getGameId());
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("newgame/{name}")
    public ResponseEntity<JwtResponse> newGame(@PathVariable String name,@RequestBody GameParams params){
        Game game = server.createGame(params);
        Player player1 = new Player(name, EPlayer.P1);
        game.join(player1);
        if(game.isSingleplayer()){
            game.join(new Player("Bot1235",EPlayer.P2));
        }

        UUID playerId = player1.getPlayerId();
        UUID gameId = game.getGameId();

        //generate Security Token
        String jwt = jwtUtils.generateJwtToken(playerId,gameId);
        JwtResponse jwtResponse = new JwtResponse(gameId,game.getSeed(),playerId,jwt,EPlayer.P1);
        log.info(name + " started a new"+(game.isSingleplayer()?"singleplayer ":"multiplayer ")+" Game: " + game.getGameId());
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("joingame/{gameId}/{name}")
    public ResponseEntity<JwtResponse> joinGame(@PathVariable UUID gameId,@PathVariable String name){
        Game game = server.getGameById(gameId);
        if(game!=null){
            Player player2 = new Player(name, EPlayer.P2);
            boolean joined = game.join(player2);
            if(!joined){
                //already 2 player in this game
                return ResponseEntity.badRequest().body(null);
            }

            UUID playerId = player2.getPlayerId();
            String jwt = jwtUtils.generateJwtToken(playerId,gameId);
            JwtResponse jwtResponse = new JwtResponse(gameId,game.getSeed(),playerId,jwt,EPlayer.P2);
            log.info(name + " started a new"+(game.isSingleplayer()?" singleplayer ":" multiplayer ")+" Game: " + game.getGameId());
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.notFound().build();
    }

    // get some data for update check
    @GetMapping("gamedata/{gameId}")
    public ResponseEntity<GameData> getGameData(@PathVariable UUID gameId){
        Game game = server.getGameById(gameId);
        if(game!=null){
            if(game.isSingleplayer()){
                if(game.getPlayersTurn().equals(game.getPlayer2().getPlayerType())){//player2 is bot in singeplayer
                    if(System.currentTimeMillis()-game.getPlayer1().getLastActionTime()> Bot.DRAW_DELAY){
                        game.getBot().get().doDrawOn(game);
                    }
                }
            }

                return ResponseEntity.ok(new GameData(game));
        }
        return ResponseEntity.notFound().build();
    }

    // get full board
    @GetMapping("board/{gameId}")
    public ResponseEntity<Board> getBoard(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId){
        if(validate(headers)){
            Game game = server.getGameById(gameId);
            if(game!=null){
                Board board = game.getBoard();
                return ResponseEntity.ok(board);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(401).body(null);
    }

    // play gameid
    @PostMapping("play/{gameId}")
    public ResponseEntity<String> play(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId,@RequestBody DrawData draw){
        if(validate(headers)) {
            Game game= server.getGameById(gameId);
            game.play(draw.getFromPos(), draw.getToPos());
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(401).body(null);
    }


    private boolean validate(HttpHeaders headers){
        return jwtUtils.validateJwtToken(parseJwtToken(headers.get("Authorization").toString()));
    }

    private String parseJwtToken(String value){
        return value.substring(8,value.length()-1);
    }
}
