package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.actions.acts.Acts;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExplodeAct extends Act {

    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
        Acts.MOVE_ACT.perform(board,pos1,pos2);
        Acts.EXPLOSION_ACT.perform(board,pos2);
    }
}
