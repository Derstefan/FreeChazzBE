package com.freechazz.generators.piece;

import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.pieces.PieceTypeId;
import com.freechazz.generators.piece.defaultGenerator.DefaultPieceTypeGenerator;

public class PieceTypeGenerator {





    public PieceType generate(int lvl, long seed){

        return generate(lvl,seed,"V1");
    }
    public PieceType generate(int lvl, long seed, String generatorVersion){
        switch (generatorVersion) {
            case "V1":
                DefaultPieceTypeGenerator gen = new DefaultPieceTypeGenerator();
                return gen.generate(lvl, seed);
            case "V2":
                //TODO:
                return null;
            default:
                DefaultPieceTypeGenerator gen2 = new DefaultPieceTypeGenerator();
                return gen2.generate(lvl, seed);
            }
        }

    public PieceType generate(PieceTypeId pieceTypeId){
        return generate(pieceTypeId.getLvl(),pieceTypeId.getSeed(),pieceTypeId.getGeneratorVersion());
    }
    }

