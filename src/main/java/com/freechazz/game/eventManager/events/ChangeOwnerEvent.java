package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;

public class ChangeOwnerEvent extends Event {

    //@JsonSerializeField
    private Piece piece;

    public ChangeOwnerEvent(Piece piece) {
        super(EventType.CHANGE_OWNER);
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public void perform(GameOperator state) {
        piece.setOwner(piece.getOwner().getOpponent());
    }

    @Override
    public void undo(GameOperator state) {
        piece.setOwner(piece.getOwner().getOpponent());
    }
}
