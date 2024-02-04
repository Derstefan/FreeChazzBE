package com.freechazz.game.eventManager.events;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapEvent extends Event {

    @JsonSerializeField
    private Pos fromPos;

    @JsonSerializeField
    private Pos toPos;

    public SwapEvent(Pos fromPos, Pos toPos) {
        super(EventType.SWAP);
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public Pos getToPos() {
        return toPos;
    }

    @Override
    public void perform(GameOperator state) {
        Piece p1 = state.pieceAt(fromPos);
        Piece p2 = state.pieceAt(toPos);
        state.removePiece(fromPos);
        state.removePiece(toPos);
        state.putPiece(p1, toPos);
        state.putPiece(p2, fromPos);

    }

    @Override
    public void undo(GameOperator state) {
        Pos pos1 = getFromPos();
        Pos pos2 = getToPos();
        Piece p2 = state.pieceAt(pos1);
        Piece p1 = state.pieceAt(pos2);

        //undo Operation
        state.removePiece(pos1);
        state.removePiece(pos2);
        state.putPiece(p1, pos1);
        state.putPiece(p2, pos2);

    }
}
