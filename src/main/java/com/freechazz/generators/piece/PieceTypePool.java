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
        piecePool.put(1, new ArrayList<>());
        piecePool.put(2, new ArrayList<>());
        piecePool.put(3, new ArrayList<>());
        piecePool.put(4, new ArrayList<>());
        piecePool.put(5, new ArrayList<>());

        rand = new Random(seed);
    }

     public PieceType getRandomPieceType(int lvl){
        return piecePool.get(lvl).get(rand.nextInt(piecePool.get(lvl).size()));
     }

    public void put(int lvl, ArrayList<PieceType> pieceTypes){
        piecePool.get(lvl).addAll(pieceTypes);
    }

    public ArrayList<PieceType> get(int lvl){
        return piecePool.get(lvl);
    }
}
