package com.freechazz.game.eventManager.events;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class MoveAndDestroyEvent extends Event {

    @JsonSerializeField
    private Pos fromPos;
    @JsonSerializeField
    private Piece piece;
    @JsonSerializeField
    private Pos toPos;

    @JsonSerializeField
    private Piece targetPiece;

    public MoveAndDestroyEvent(Pos fromPos, Piece piece, Pos toPos, Piece targetPiece) {
        super(EventType.MOVEANDDESTROY);
        this.fromPos = fromPos;
        this.piece = piece;
        this.toPos = toPos;
        this.targetPiece = targetPiece;
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public Piece getPiece() {
        return piece;
    }

    public Pos getToPos() {
        return toPos;
    }

    public Piece getTargetPiece() {
        return targetPiece;
    }

    @Override
    public void perform(GameOperator state) {
        Piece targetPiece = state.pieceAt(toPos);
        Piece piece = state.pieceAt(fromPos);
        if (targetPiece.isKing()) {
            state.log("winner is " + piece.getOwner().toString());
            state.setWinner(targetPiece.getOwner().getOpponent());
        }
        state.removePiece(toPos);
        state.getGraveyard().add(targetPiece);

        state.removePiece(fromPos);
        state.putPiece(piece, toPos);
        state.log("[p," + piece.getPieceId() + "] at " + fromPos + " has moved to " + toPos + " and destroyed [p," + targetPiece.getPieceId() + "] at " + toPos);

    }

    @Override
    public void undo(GameOperator state) {
        state.setWinner(null);
        if (!state.isFree(fromPos)) {
            log.warn("Cant undo Move Event, because there is a Piece at fromPos...");
        }
        //undo Operation
        state.removePiece(toPos);
        state.putPiece(piece, fromPos);

        state.getGraveyard().remove(targetPiece);
        state.putPiece(targetPiece, toPos);
    }

    @Override
    public void reconnect(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            if (p.equals(piece)) {
                piece = p;
            } else if (p.equals(targetPiece)) {
                targetPiece = p;
            } else {
                return;
            }
        }
    }
}
