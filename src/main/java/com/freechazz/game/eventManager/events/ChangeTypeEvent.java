package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.state.GameOperator;

import java.util.ArrayList;

public class ChangeTypeEvent extends Event {

    @JsonSerializeField
    private Piece piece;
    @JsonSerializeField
    private PieceType newType;
    @JsonSerializeField
    private PieceType oldType;

    public ChangeTypeEvent(Piece piece, PieceType oldType, PieceType newType) {
        super(EventType.CHANGE_TYPE);
        this.piece = piece;
        this.oldType = oldType;
        this.newType = newType;
    }

    public Piece getPiece() {
        return piece;
    }

    public PieceType getOldType() {
        return oldType;
    }


    public PieceType getNewType() {
        return newType;
    }

    @Override
    public void perform(GameOperator state) {


        piece.setPieceType(newType);
        state.log("type of [p," + piece.getPieceId() + "]  has changed");
    }

    @Override
    public void undo(GameOperator state) {
        piece.setPieceType(oldType);
    }

    @Override
    public void reconnect(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            if (p.equals(piece)) {
                piece = p;
                return;
            }
        }
    }
}
