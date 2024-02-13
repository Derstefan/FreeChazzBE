package com.freechazz.network.DTO.game.server;

import java.util.ArrayList;

public class MatchDataCollection {

    private ArrayList<MatchData> matchDatas;

    public MatchDataCollection(ArrayList<MatchData> matchDatas) {
        this.matchDatas = matchDatas;
    }

    public ArrayList<MatchData> getMatchDatas() {
        return matchDatas;
    }

}
