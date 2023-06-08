package com.freechazz.game.actions;

import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;

public interface MatchActionInterface {
    //TODO: interface for actions
    // only these methods here should be used in the actions

    void move(Pos fromPos, Pos toPos);
    void destroy(Pos pos);
    void swap(Pos fromPos, Pos toPos);
    void changeOwner(Piece piece);
    void changeType(Piece piece, PieceType newType);


    Piece pieceAt(Pos p);
    Pos posOf(Piece p);
    boolean isFree(Pos p);
    boolean isOnboard(Pos pos);
    boolean areEnemys(Piece p1,Piece p2);


    default boolean isFree(int x, int y){
        return isFree(new Pos(x,y));
    }

    default boolean isOnboard(int x, int y){
        return isOnboard(new Pos(x,y));
    }




}
