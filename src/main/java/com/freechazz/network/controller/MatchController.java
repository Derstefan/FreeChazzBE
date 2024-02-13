package com.freechazz.network.controller;

import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.database.entities.MatchEntity;
import com.freechazz.database.entities.UserEntity;
import com.freechazz.database.services.MatchService;
import com.freechazz.database.services.UserService;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.player.User;
import com.freechazz.generators.formation.FormationGenerator;
import com.freechazz.generators.piece.PieceTypeGenerator;
import com.freechazz.network.DTO.GameParams.RandomGameParams;
import com.freechazz.network.DTO.game.server.*;
import com.freechazz.network.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/match/")
@Slf4j
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;


    @PostMapping("/createrandomgame")
    public ResponseEntity<UUID> createGame(@RequestHeader HttpHeaders headers, @RequestBody RandomGameParams randomGameParams) {
        log.info("new Game: " + randomGameParams);
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }


        // hotseat, against bot or automatic ---------------------------------------------------------------------------------
        if (!randomGameParams.getIsNetworkGame()) {

            User user1;
            User user2;
            MatchEntity matchEntity;
            if (randomGameParams.getIsAutomatic()) { // automatic
                UserEntity bot = userService.getOrCreateBotUser();

                user1 = new User(bot.getUuid(), bot.getUsername());
                user2 = new User(bot.getUuid(), bot.getUsername());
                matchEntity = new MatchEntity(bot, bot);
                matchEntity.setWatchUser(user.get());
            } else if (randomGameParams.getIsBotEnemy()) { // against bot
                UserEntity bot = userService.getOrCreateBotUser();
                user1 = new User(user.get().getUuid(), user.get().getUsername());
                user2 = new User(bot.getUuid(), bot.getUsername());
                matchEntity = new MatchEntity(user.get(), bot);
            } else { // hot seat
                user1 = new User(user.get().getUuid(), user.get().getUsername());
                user2 = new User(user.get().getUuid(), user.get().getUsername());
                matchEntity = new MatchEntity(user.get(), user.get());
            }


            Formation f1 = new FormationGenerator((long) (Math.random() * Long.MAX_VALUE), randomGameParams.getSize(), user1).generate();// remove Formation
            Formation f2;// remove Formation
            if (randomGameParams.getIsSamePieces()) {
                f2 = f1.copy();
                f2.setOwner(user2);
            } else {
                f2 = new FormationGenerator((long) (Math.random() * Long.MAX_VALUE), randomGameParams.getSize(), user2).generate();

            }
            GameBuilder gameBuilder = new GameBuilder(f1, f2);
            if (randomGameParams.getIsBotEnemy()) {
                gameBuilder.botP2(new BetterBot2(EPlayer.P2, 2, (long) (Math.random() * 351241)));
            }
            if (randomGameParams.getIsAutomatic()) {
                gameBuilder.botP1(new BetterBot2(EPlayer.P1, 2, (long) (Math.random() * 35124231)));
            }
            Game game = gameBuilder.randomStarter()
                    .build();

            log.info("gameid: " + game.getGameId());

            if (game.getPlayersTurn() == EPlayer.P2 && randomGameParams.getIsBotEnemy()) {
                game.botAction();
                if (randomGameParams.getIsAutomatic()) {
                    log.info("automatic");
                    game.botAction();
                }
            }
            matchEntity.setId(game.getGameId());
            matchEntity.setGameData(game.toJson());
            matchService.createMatch(matchEntity);
            return ResponseEntity.ok(matchEntity.getId());
        }

        //create network game --------------------------------------------------------------------------------------------------------------
        User user1 = new User(user.get().getUuid(), user.get().getUsername());
        MatchEntity matchEntity = new MatchEntity(user.get(), null);

        Formation f1 = new FormationGenerator((long) (Math.random() * Long.MAX_VALUE), randomGameParams.getSize(), user1).generate();// remove Formation
        Formation f2;// remove Formation
        if (randomGameParams.getIsSamePieces()) {
            f2 = f1.copy();
            f2.setOwner(null);
        } else {
            f2 = new FormationGenerator((long) (Math.random() * Long.MAX_VALUE), randomGameParams.getSize(), null).generate();

        }
        Game game = new GameBuilder(f1, f2).randomStarter()
                .build();
        log.info("gameid: " + game.getGameId());
        matchEntity.setId(game.getGameId());
        matchEntity.setGameData(game.toJson());
        matchService.createMatch(matchEntity);
        return ResponseEntity.ok(matchEntity.getId());
    }


    @GetMapping("join/{gameId}")
    public ResponseEntity<UUID> joinGame(@RequestHeader HttpHeaders headers, @PathVariable String gameId) {
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        UUID id = matchService.joinGame(UUID.fromString(gameId), user.get());
        return ResponseEntity.ok(id);
    }


    @GetMapping("update/{gameId}/{turn}")
    public ResponseEntity<UpdateDataDTO> checkUpdate(@RequestHeader HttpHeaders headers, @PathVariable String gameId, @PathVariable int turn) {

        //log.info("checkUpdate input from controller: " + gameId + " " + turn);
        //checking
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        if (gameId == null)
            return ResponseEntity.status(404).body(null);

        log.info("gameId: " + gameId);
        MatchEntity matchEntity = matchService.getMatchByIdForUser(UUID.fromString(gameId), user.get().getUuid());
        if (matchEntity == null) {
            return ResponseEntity.status(404).body(null);
        }

        // update
        Game game = new Game(matchEntity.getGameData());
        UpdateDataDTO updateData = new UpdateDataDTO(game, turn);

        if (game.getTurns() > turn) { // the game is in the past
            return ResponseEntity.ok(updateData);
        }

        //botaction
        if (game.isBotTurn()) {
            game.botAction();
            matchService.updateMatch(matchEntity.getId(), game.toJson());
        }

        return ResponseEntity.ok(updateData);
    }

    @GetMapping("play/{gameId}/{x1}/{y1}/{x2}/{y2}")
    public ResponseEntity<UpdateDataDTO> play(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId, @PathVariable int x1, @PathVariable int y1, @PathVariable int x2, @PathVariable int y2) {
        //log.info("play input from controller: " + x1 + " " + y1 + " " + x2 + " " + y2);
        //checking
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(401).body(null);
        }
        MatchEntity matchEntity = matchService.getMatchByIdForUser(gameId, user.get().getUuid());
        if (matchEntity == null) {
            return ResponseEntity.status(401).body(null);
        }

        // play
        Game game = new Game(matchEntity.getGameData());
        boolean played = game.play(new Pos(x1, y1), new Pos(x2, y2));
        matchService.updateMatch(matchEntity.getId(), game.toJson());
        UpdateDataDTO updateData = new UpdateDataDTO(game, game.getTurns());

        //botaction
        if (game.isBotTurn() && played) {
            game.botAction();
            matchService.updateMatch(matchEntity.getId(), game.toJson());
        }

        return ResponseEntity.ok(updateData);
    }


    @GetMapping("loadAllPieceTypes/{gameId}/{turn}")
    public ResponseEntity<PieceTypeDTOCollection> loadAllPieceTypes(@RequestHeader HttpHeaders headers, @PathVariable UUID gameId, @PathVariable int turn) {
        log.info("loadAllPieceTypes input from controller: " + gameId + " " + turn);
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        MatchEntity matchEntity = matchService.getMatchByIdForUser(gameId, user.get().getUuid());
        if (matchEntity == null) {
            return ResponseEntity.status(404).body(null);
        }
        Game game = new Game(matchEntity.getGameData());

        PieceTypeGenerator pieceTypeGenerator = new PieceTypeGenerator();
        ArrayList<PieceTypeDTO> pieceTypeDTOS = new ArrayList<>();
        for (PieceDTO pieceDTO :
                game.getState().getHistory().getHistoryState(turn).getPieceDTOs()) {
            pieceTypeDTOS.add(new PieceTypeDTO(pieceTypeGenerator.generate(pieceDTO.getPieceTypeId())));
        }
        return ResponseEntity.ok(new PieceTypeDTOCollection(pieceTypeDTOS));
    }

    @GetMapping("matches")
    public ResponseEntity<MatchDataCollection> getAllMatches(@RequestHeader HttpHeaders headers) {
        log.info("getAllMatches");
        if (!jwtUtils.validate(headers)) {
            return ResponseEntity.status(401).body(null);
        }
        Optional<UserEntity> user = userService.getUserById(jwtUtils.getUserId(headers));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        ArrayList<MatchData> matchDatas = new ArrayList<>();
        for (MatchEntity matchEntity :
                matchService.getAllMatchesForUser(user.get().getUuid())) {
            Game game = new Game(matchEntity.getGameData());
            matchDatas.add(new MatchData(game));
        }
        MatchDataCollection matchDataCollection = new MatchDataCollection(matchDatas);
        return ResponseEntity.ok(matchDataCollection);
    }

}
