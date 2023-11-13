package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RangeAttackAct extends PieceAct {
    @Override
    public void performWithoutChain(GameOperator board,Pos pos1, Pos pos2){
        Acts.DESTROY_PIECE_ACT.performWithoutChain(board,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }

}
