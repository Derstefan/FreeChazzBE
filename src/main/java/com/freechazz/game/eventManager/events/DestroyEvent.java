package com.freechazz.game.eventManager.events;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class DestroyEvent extends Event {

    @JsonSerializeField
    private Piece piece;
    @JsonSerializeField
    private Pos pos;

    public DestroyEvent(Piece piece, Pos pos) {
        super(EventType.DESTROY);
        this.piece = piece;
        this.pos = pos;
    }

    public Piece getPiece() {
        return piece;
    }

    public Pos getPos() {
        return pos;
    }

    @Override
    public void perform(GameOperator state) {
        Piece p = state.pieceAt(pos);
        if (p.isKing()) {
            state.setWinner(p.getOwner().getOpponent());
        }
        state.removePiece(pos);
        state.getGraveyard().add(p);
    }

    @Override
    public void undo(GameOperator state) {
        //undo Operation
        state.setWinner(null);
        state.getGraveyard().remove(piece);
        state.putPiece(piece, pos);
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
