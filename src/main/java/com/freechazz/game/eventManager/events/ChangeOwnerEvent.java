package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.pieces.Piece;

public class ChangeOwnerEvent extends Event {

    private Piece piece;

    public ChangeOwnerEvent(Piece piece){
        this.piece = piece;
    }

    public Piece getPiece(){
        return piece;
    }
}
