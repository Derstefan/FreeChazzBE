package com.freechazz.generators.game;

import com.freechazz.game.Game;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.core.EPlayer;
import com.freechazz.bots.Bot;

public class GameBuilder {


    private Formation formation1;
    private Formation formation2;

    private Bot botP1;

    private Bot botP2;

    private EPlayer firstTurn;

    public GameBuilder(Formation formation1, Formation formation2) {
        this.formation1 = formation1;
        this.formation2 = formation2;
    }

    public GameBuilder randomStarter(){
        firstTurn = EPlayer.random();
        return this;
    }

    public GameBuilder randomStarter(long seed){
        firstTurn = EPlayer.random(seed);
        return this;
    }


    public GameBuilder botP1(Bot botP1){
        this.botP1 = botP1;
        return this;
    }

    public GameBuilder botP2(Bot botP2){
        this.botP2 = botP2;
        return this;
    }

    public GameBuilder firstTurnP1(){
        firstTurn = EPlayer.P1;
        return this;
    }

    public GameBuilder firstTurnP2(){
        firstTurn = EPlayer.P1;
        return this;
    }


    public Game build(){
        if(firstTurn==null){
            throw new IllegalArgumentException("First turn is not set");
        }
        Game game = new Game(formation1,formation2);
        if(botP1!=null){
            game.setP1Bot(botP1);
        }
        if(botP2!=null){
            game.setP2Bot(botP2);
        }

        game.setPlayersTurn(firstTurn);
        game.computePossibleMoves();
        return game;
    }


    public GameBuilder firstPlayer(EPlayer firstPlayer) {
        this.firstTurn = firstPlayer;
        return this;
    }
}
