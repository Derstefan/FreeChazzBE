package com.freechazz.generators.piece;

import com.freechazz.game.pieces.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * A pool of piece types with different level
 */
public class PieceTypePool {


    Random rand;

    HashMap<Integer, ArrayList<PieceType>> piecePool = new HashMap<>();

    public PieceTypePool(long seed) {
        rand = new Random(seed);
    }

     public PieceType getRandomPieceType(int lvl){
        return piecePool.get(lvl).get(rand.nextInt(piecePool.get(lvl).size()));
     }

    public void put(int lvl, ArrayList<PieceType> pieceTypes){
        piecePool.put(lvl,pieceTypes);
    }

    public ArrayList<PieceType> get(int lvl){
        return piecePool.get(lvl);
    }
}
