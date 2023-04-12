package com.freechazz.server.DTO;

import com.freechazz.server.MatchManager;

public class ServerData {
    private int gameNumber;

    public ServerData(MatchManager server) {
        gameNumber = server.getGameNumbers();
    }

    public int getGameNumber() {
        return gameNumber;
    }
}
