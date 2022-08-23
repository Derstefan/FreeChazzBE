package com.freechess.game.actions.acts.unitary;

import com.freechess.game.actions.acts.Acts;
import com.freechess.game.actions.acts.UnitaryAct;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;

public class TeleportInverseAct extends UnitaryAct {
    @Override
    public void perform(Board board, Position pos) {
        Piece piece = board.pieceAt(pos);
        if(piece==null)return;
        int width = board.getWidth();
        int height = board.getHeight();
        Position invPos = new Position(width-1-pos.getX(),height-1-pos.getY());
        Acts.MOVE_OR_ATTACK.perform(board,pos,invPos);
    }
}
