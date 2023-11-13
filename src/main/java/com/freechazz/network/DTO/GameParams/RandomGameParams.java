package com.freechazz.network.DTO.GameParams;

import com.freechazz.game.core.ESize;

public class RandomGameParams extends GameParams {

    private ESize size;
    private boolean withSeed;

    private Long seed;
    private boolean samePieces;
    private boolean isBotEnemy;
    private boolean isAutomatic;


    private boolean isNetworkGame;
    private boolean isPublic;


    public RandomGameParams() {
    }


    public ESize getSize() {
        return size;
    }

    public Long getSeed() {
        return seed;
    }


    public boolean getIsSamePieces() {
        return samePieces;
    }


    public boolean getIsPublic() {
        return isPublic;
    }

    public boolean getIsBotEnemy() {
        return isBotEnemy;
    }

    public boolean getIsNetworkGame() {
        return isNetworkGame;
    }

    public boolean isWithSeed() {
        return withSeed;
    }

    public boolean getIsAutomatic() {
        return isAutomatic;
    }


    public String toString() {
        return "RandomGameParams{" +
                "size=" + size +
                ", withSeed=" + withSeed +
                ", seed=" + seed +
                ", samePieces=" + samePieces +
                ", isBotEnemy=" + isBotEnemy +
                ", isNetworkGame=" + isNetworkGame +
                ", isPublic=" + isPublic +
                '}';
    }
}

