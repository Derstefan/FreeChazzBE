package com.freechess.game.actions.acts.unitary;

import com.freechess.game.actions.acts.Acts;
import com.freechess.game.actions.acts.UnitaryAct;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;

public class ExplosionAct extends UnitaryAct {
    @Override
    public void perform(Board board, Position pos) {

        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(-1,-1));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(0,-1));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(1,-1));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(-1,0));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(0,0));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(1,0));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(-1,1));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(0,1));
        Acts.DESTROY_PIECE_ACT.perform(board,pos.add(1,1));

    }
}
