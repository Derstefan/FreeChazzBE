package com.freechazz.game.actions.acts.unitary;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;

public class TeleportInverseAct extends PosAct {
    @Override
    public void performWithoutChain(GameOperator board, Pos pos) {
        Piece piece = board.pieceAt(pos);
        if(piece==null)return;
        int width = board.getWidth();
        int height = board.getHeight();
        Pos invPos = new Pos(width-1-pos.getX(),height-1-pos.getY());
        Acts.MOVE_OR_ATTACK.perform(board,pos,invPos);
    }
}
