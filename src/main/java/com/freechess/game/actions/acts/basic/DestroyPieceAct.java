package com.freechess.game.actions.acts.basic;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.actions.acts.UnitaryAct;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;

public class DestroyPieceAct extends UnitaryAct {
    @Override
    public void perform(Board board, Position pos) {
        Piece targetPiece = board.pieceAt(pos);
        if(targetPiece!=null){
            board.takePiece(pos);
        }
    }
}
