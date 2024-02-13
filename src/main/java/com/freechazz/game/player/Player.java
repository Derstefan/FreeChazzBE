package com.freechazz.game.player;

import com.freechazz.bots.Bot;
import com.freechazz.game.core.EPlayer;

import java.util.UUID;

public class Player {

    private EPlayer playerType;

    private String name;
    private UUID playerId;

    private Bot bot = null;

    private long lastActionTime;

    public Player(UUID userId, String name, EPlayer playerType) {
        this.name = name;
        this.playerId = userId;
        this.playerType = playerType;
        lastActionTime = System.currentTimeMillis();
    }


    public String getName() {
        return name;
    }

    public UUID getUserId() {
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
        return bot;
    }

    public boolean isAI() {
        return bot != null;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }


    public boolean isRealPlayer() {
        return bot == null;
    }

    private Player(Player anotherPlayer) {
        name = anotherPlayer.getName();
        playerId = anotherPlayer.getUserId();
        playerType = anotherPlayer.getPlayerType();
        lastActionTime = anotherPlayer.getLastActionTime();
    }

    public Player copy() {
        return new Player(this);
    }
}
