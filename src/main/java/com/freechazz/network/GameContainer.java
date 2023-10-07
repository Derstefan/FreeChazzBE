package com.freechazz.network;

import com.freechazz.bots.impl.BetterBot2;
import com.freechazz.game.Game;
import com.freechazz.game.GameBuilder;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.player.User;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class GameContainer {

    private UUID gameId = UUID.randomUUID();

    private GameType gameType;
    private User user1;
    private Formation formation1;
    private User user2;
    private Formation formation2;
    private Game game;

    private boolean started = false;

    public GameContainer(GameType gameType) {
        this.gameType = gameType;
    }

    public void joinUser(User user) {
        if (user1 == null) {
            user1 = user;
        } else if (user2 == null) {
            user2 = user;
        } else {
            log.warn("Game is full of Users");
         }
    }

    public void joinFormation(Formation formation) {
        if (formation1 == null) {
            formation1 = formation;
        } else if (formation2 == null) {
            formation2 = formation;
        } else {
            log.warn("Game is full of Formations");
        }
    }

    public void start() {
        GameBuilder gameBuilder = new GameBuilder(formation1, formation2);

        if(gameType == GameType.BVB){
            gameBuilder.botP1(new BetterBot2(EPlayer.P1,2,(long)(Math.random()*23312)));
            gameBuilder.botP2(new BetterBot2(EPlayer.P2,2,(long)(Math.random()*351241)));
        }
        else if(gameType == GameType.PVB){
            gameBuilder.botP2(new BetterBot2(EPlayer.P2,2,(long)(Math.random()*351241)));
        }
        else if(gameType == GameType.PVP){
            //do nothing
        }
        else{
            throw new RuntimeException("Unknown game type");
        }
        this.game = gameBuilder.randomStarter().build();
        started = true;
    }


    public UUID getGameId() {
        return gameId;
    }

    public Game getGame() {
        return game;
    }
}
