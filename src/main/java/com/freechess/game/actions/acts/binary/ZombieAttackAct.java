package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;

public class ZombieAttackAct extends Act {

    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = board.pieceAt(pos2);
        if (targetPiece != null && piece!=null) {
            if (!targetPiece.getOwner().equals(owner)) {
                board.takePiece(pos2);
                Piece zombie = new Piece(piece.getOwner(),piece.getPieceType());
                board.addPiece(zombie,pos2);
            }
        }
    }
}
