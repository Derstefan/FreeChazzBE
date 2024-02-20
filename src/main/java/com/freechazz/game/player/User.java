package com.freechazz.game.player;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String username;

    public User(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
