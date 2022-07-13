package com.freechess.server.DTO;

import com.freechess.game.board.ESize;

public class GameParams {

    private ESize size;
    private Long seed;

    private String type;

    public GameParams(ESize size, Long seed, String type) {
        this.size = size;
        this.seed = seed;
        this.type = type;
    }

    public GameParams(ESize size) {
        this.size = size;
    }

    public GameParams(Long seed) {
        this.seed = seed;
    }

    public ESize getESize() {
        return size;
    }

    public Long getSeed() {
        return seed;
    }

    public String getType() {
        return type;
    }
}
