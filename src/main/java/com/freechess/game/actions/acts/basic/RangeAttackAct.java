package com.freechess.game.actions.acts.basic;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.actions.acts.Acts;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RangeAttackAct extends Act {
    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
        Acts.DESTROY_PIECE_ACT.perform(board,pos2);
    }

}
