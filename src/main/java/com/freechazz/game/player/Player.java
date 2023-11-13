package com.freechazz.game.player;

import com.freechazz.game.core.EPlayer;
import com.freechazz.bots.Bot;

import java.util.Optional;
import java.util.UUID;

public class Player {

    private EPlayer playerType;

    private String name;
    private UUID playerId;

    private Optional<Bot> bot = Optional.empty();

    private boolean isRealPlayer;
    private long lastActionTime;

    public Player(String name, EPlayer playerType) {
        this.name = name;
        playerId = UUID.randomUUID(); //TODO: use real id
        this.playerType = playerType;
        lastActionTime=System.currentTimeMillis();
    }

    public Player(String name, EPlayer playerType, boolean isRealPlayer) {
        if(!isRealPlayer){
            this.name = "WolrdPlayer";
            playerId = UUID.fromString("d700bb95-ca35-4f40-835f-ab097c48388f");
            this.isRealPlayer = false;
        } else {
            this.name = name;
            playerId = UUID.randomUUID(); //TODO: use real id
            this.isRealPlayer = true;
        }
        this.playerType = playerType;
        lastActionTime=System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public UUID getPlayerId() {
        return playerId;
    }


    public EPlayer getPlayerType() {
        return playerType;
    }

    public long getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime() {
        this.lastActionTime = System.currentTimeMillis();
    }


    public Bot getBot() {
        if(bot.isPresent()){
            return bot.get();
        }
        return null;
    }

    public boolean isAI(){
        return bot.isPresent();
    }

    public void setBot(Bot bot) {
        this.bot = Optional.of(bot);
    }


    public boolean isRealPlayer() {
        return isRealPlayer;
    }

    private Player(Player anotherPlayer){
        name = anotherPlayer.getName();
        playerId = anotherPlayer.getPlayerId();
        playerType = anotherPlayer.getPlayerType();
        lastActionTime=anotherPlayer.getLastActionTime();
        isRealPlayer = anotherPlayer.isRealPlayer();
    }
    public Player copy(){
        return new Player(this);
    }
}
