package com.freechess.game.actions.acts.basic;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.actions.acts.Acts;
import com.freechess.game.board.Board;
import com.freechess.game.exception.MovementException;
import com.freechess.game.player.EPlayer;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveOrAttackAct extends Act {



    @Override
    public void perform(Board board, Position pos1,Position pos2){
        Acts.DESTROY_PIECE_ACT.perform(board,pos2);
        Acts.MOVE_ACT.perform(board,pos1,pos2);
    }

}
