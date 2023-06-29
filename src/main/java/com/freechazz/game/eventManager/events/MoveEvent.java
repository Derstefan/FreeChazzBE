package com.freechazz.game.eventManager.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameState;

public class MoveEvent extends Event {

    private Pos fromPos;
    private Piece piece;
    private Pos toPos;

    public MoveEvent(Pos fromPos, Piece piece, Pos toPos){
        super(EventType.MOVE);
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

    @Override
    public void perform(GameState state) {
        Piece targetPiece = state.pieceAt(toPos);
        Piece piece = state.pieceAt(fromPos);
        if (targetPiece == null) {
            state.removePiece(fromPos);
            state.putPiece(piece, toPos);
        }
    }

    @Override
    public void undo(GameState state) {

    }
}
