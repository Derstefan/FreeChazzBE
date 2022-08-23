package com.freechess.game.actions.acts.basic;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.exception.MovementException;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;

public class MoveAct extends Act {

    @Override
    public void perform(Board board, Position pos1, Position pos2) {
        Piece piece = board.pieceAt(pos1);
        Piece targetPiece = board.pieceAt(pos2);

        if (targetPiece == null) {
            board.removePiece(pos1);
            board.addPiece(piece,pos2);
        }

    }
}
