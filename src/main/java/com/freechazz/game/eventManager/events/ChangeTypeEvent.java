package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.state.GameState;

import java.util.UUID;

public class ChangeTypeEvent extends Event {

    private Piece piece;
    private PieceType newType;
    private PieceType oldType;

    public ChangeTypeEvent(Piece piece, PieceType oldType, PieceType newType){
        super(EventType.CHANGE_TYPE);
        this.piece = piece;
        this.oldType = oldType;
        this.newType = newType;
    }

    public Piece getPiece(){
        return piece;
    }

    public PieceType getOldType(){
        return oldType;
    }


    public PieceType getNewType(){
        return newType;
    }

    @Override
    public void perform(GameState state) {

    }

    @Override
    public void undo(GameState state) {

    }
}
