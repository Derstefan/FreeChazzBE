package com.freechazz.game.pieces;

import java.util.UUID;

public class PieceTypeId {


    private UUID pieceTypeId;
    private long seed;
    private int lvl;
    private String generatorVersion;


    public PieceTypeId(long seed, int lvl, String generatorVersion) {
        this.seed = seed;
        this.lvl = lvl;
        this.generatorVersion = generatorVersion;
    }

    public UUID getPieceTypeId() {
        return pieceTypeId;
    }

    public long getSeed() {
        return seed;
    }

    public int getLvl() {
        return lvl;
    }

    public String getGeneratorVersion() {
        return generatorVersion;
    }

    public void setPieceTypeId(UUID pieceTypeId) {
        this.pieceTypeId = pieceTypeId;
    }
}
