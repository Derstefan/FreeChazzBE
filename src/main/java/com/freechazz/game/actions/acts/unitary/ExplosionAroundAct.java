package com.freechazz.game.actions.acts.unitary;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public class ExplosionAroundAct extends PosAct {
    @Override
    public void performWithoutChain(GameOperator board, Pos pos) {

        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(-1,-1));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(0,-1));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(1,-1));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(-1,0));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(0,0));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(1,0));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(-1,1));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(0,1));
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos.add(1,1));

    }
}
