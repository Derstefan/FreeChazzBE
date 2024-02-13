package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;

import java.util.ArrayList;

public class ChangeOwnerEvent extends Event {

    @JsonSerializeField
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
