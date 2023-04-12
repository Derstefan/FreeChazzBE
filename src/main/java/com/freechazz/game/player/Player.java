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

    private long lastActionTime;

    public Player(String name, EPlayer playerType) {
        this.name = name;
        playerId = UUID.randomUUID(); //
        this.playerType = playerType;
        lastActionTime=System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
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
        return bot.get();
    }

    public boolean isAI(){
        return bot.isPresent();
    }

    public void setBot(Bot bot) {
        this.bot = Optional.of(bot);
    }

    private Player(Player anotherPlayer){
        name = anotherPlayer.getName();
        playerId = anotherPlayer.getPlayerId();
        playerType = anotherPlayer.getPlayerType();
        lastActionTime=anotherPlayer.getLastActionTime();
    }
    public Player copy(){
        return new Player(this);
    }
}
