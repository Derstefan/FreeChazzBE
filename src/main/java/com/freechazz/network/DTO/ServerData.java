package com.freechazz.network.DTO;

import com.freechazz.network.MatchManager;

public class ServerData {
    private int gameNumber;

    public ServerData(MatchManager server) {
        gameNumber = server.getGameNumbers();
    }

    public int getGameNumber() {
        return gameNumber;
    }
}
