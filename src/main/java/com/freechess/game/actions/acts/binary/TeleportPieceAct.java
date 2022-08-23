package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.actions.acts.Acts;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

public class TeleportPieceAct extends Act {
    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
        Acts.TELEPORT_INVERSE_ACT.perform(board,pos2);
    }
}
