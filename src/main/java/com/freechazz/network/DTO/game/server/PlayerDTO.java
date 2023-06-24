package com.freechazz.network.DTO.game.server;

import com.freechazz.game.core.EPlayer;
import com.freechazz.game.player.Player;

import java.util.UUID;

public class PlayerDTO {
    private UUID playerId;
    private String name;
    private EPlayer playerType;
    private boolean isPlayedByBot;
    private boolean isRealPlayer;

    public PlayerDTO(Player player) {
        this.playerId = player.getPlayerId();
        this.name = player.getName();
        this.playerType = player.getPlayerType();
        this.isPlayedByBot = player.isAI();
        this.isRealPlayer = player.isRealPlayer();
    }


    public UUID getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public EPlayer getPlayerType() {
        return playerType;
    }

    public boolean isPlayedByBot() {
        return isPlayedByBot;
    }

    public boolean isRealPlayer() {
        return isRealPlayer;
    }
}
