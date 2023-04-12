package com.freechazz.generators.formation;

import com.freechazz.game.pieces.PieceType;
import com.freechazz.generators.piece.impl.PieceTypeGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Generates a random pool of pieces with different levels
 */
@Slf4j
public class PiecePoolGenerator {

    private static final int MAX_LVL = 5;
    private static final int POOL_SIZE = 5;

    private int symbolCounter = 100; //ascii start

    private Random rand;

    PieceTypeGenerator gen = new PieceTypeGenerator();




    public PiecePoolGenerator(long seed) {
        rand = new Random(seed);
    }

    public PieceTypePool generate(){
        PieceTypePool pieceTypePool = new PieceTypePool(rand.nextLong());
        for(int j =1;j<=MAX_LVL;j++){
            for (int i = 0; i < POOL_SIZE; i++) {
                pieceTypePool.get(j).add(generate(j,rand.nextLong()));
            }
        }
        return pieceTypePool;
    }


    private PieceType generate(int lvl, long seed){


        PieceType pieceType = gen.generate(lvl,seed);

        //or other generators

        pieceType.setSeed(seed);

        //TODO: Georg/Björn impl
        pieceType.setSymbol(""+(char) symbolCounter);
        symbolCounter++;
        //TODO:to here
      //  log.info("Generated PieceType: {}", pieceType.getSymbol());

        return pieceType;
    }
}
