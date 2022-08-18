package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.exception.MovementException;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RangeAttackAct extends Act {
    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = board.pieceAt(pos2);

        if (targetPiece != null) {
            if (targetPiece.getOwner().equals(owner)) {
                log.warn("Can not attack own piece.");
                throw new MovementException();
            }
                // attack enemy
                board.takePiece(pos2);

        }

    }

}
