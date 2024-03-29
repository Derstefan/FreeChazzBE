package com.freechazz.network.DTO;

import com.freechazz.game.core.EPlayer;

import java.util.UUID;

public class JwtResponse {

    private UUID gameId;
    private UUID playerId;
    private long seed;
    private String version;
    private String accessToken;
    private EPlayer player;

    public JwtResponse(UUID gameId,long seed, UUID playerId, String accessToken,EPlayer player) {
        this.gameId = gameId;
        this.seed = seed;
        this.version = "0.5.0";
        this.playerId = playerId;
        this.accessToken = accessToken;
        this.player = player;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public EPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EPlayer player) {
        this.player = player;
    }

    public long getSeed() {
        return seed;
    }
    public String getVersion() {
        return version;
    }
}
