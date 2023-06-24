package com.freechazz.network.controller;


import com.freechazz.network.security.JwtUtils;
import com.freechazz.network.MatchManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://free-chazz-fe.herokuapp.com","http://localhost:3000"})
@RestController
@RequestMapping("api/")
@Slf4j
public class GameController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private MatchManager server;



    //new Match bots,npc,realGame

    //join -accept a gamechallange TODO: gamechallage, new Class?







/*
=======
    @Autowired
    ItemRepository gameDataRepository;

>>>>>>> master:src/main/java/com/freechess/server/controller/GameController.java
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
            String botName = "Bot"+(int)(Math.random()*10000);
            game.join(new Player(botName,EPlayer.P2));
            gameDataRepository.save(new GameEntry(name,botName,game.getGameId(),game.getSeed(),true,game.getBoard().getWidth(),game.getBoard().getHeight()));
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
            gameDataRepository.save(new GameEntry(game.getPlayer1().getName(),name,game.getGameId(),game.getSeed(),false,game.getBoard().getWidth(),game.getBoard().getHeight()));

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
                return ResponseEntity.ok(new GameData(game));
        }
        return ResponseEntity.notFound().build();
    }

    // get full board
    @GetMapping("board/{gameId}")
    public ResponseEntity<GameState> getBoard(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId){
        if(validate(headers)){
            Game game = server.getGameById(gameId);
            if(game!=null){
                GameState board = game.getState();
                board.computePossibleMoves();
                return ResponseEntity.ok(board);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.status(401).body(null);
    }






 */
}
