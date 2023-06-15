package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.Pos;

public class MoveEvent extends Event {

    private Pos fromPos;
    private Piece piece;
    private Pos toPos;

    public MoveEvent(Pos fromPos, Piece piece, Pos toPos){
        this.fromPos = fromPos;
        this.piece = piece;
        this.toPos = toPos;
    }

    public Pos getFromPos(){
        return fromPos;
    }

    public Piece getPiece(){
        return piece;
    }

    public Pos getToPos(){
        return toPos;
    }
}
