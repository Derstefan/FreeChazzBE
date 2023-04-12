package com.freechazz.game.player;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String name;

    public User(String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
