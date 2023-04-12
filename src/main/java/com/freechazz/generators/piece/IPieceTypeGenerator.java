package com.freechazz.generators.piece;

import com.freechazz.game.pieces.PieceType;

public interface IPieceTypeGenerator {



    public PieceType generate(int lvl, long seed);

/*
    public IPieceType generate(long pieceId);

    public IPieceType generate(int lvl, long pieceId);
*/

}
