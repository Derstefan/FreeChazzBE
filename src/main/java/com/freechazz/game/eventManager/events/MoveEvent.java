package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public void perform(GameOperator state) {
        Piece targetPiece = state.pieceAt(toPos);
        Piece piece = state.pieceAt(fromPos);
        if (targetPiece == null) {
            state.removePiece(fromPos);
            state.putPiece(piece, toPos);
        }
    }

    @Override
    public void undo(GameOperator state) {
        if(!state.isFree(fromPos)){
            log.warn("Cant undo Move Event, because there is a Piece at fromPos...");
        }
        //undo Operation
        state.removePiece(toPos);
        state.putPiece(piece, fromPos);
    }
}
