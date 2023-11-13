package com.freechazz.game.actions.acts.unitary.actChain;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.ForceType;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.actions.acts.ForceActionAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameOperator;

public class RangedAttackCrossAct extends ForceActionAct {


    public RangedAttackCrossAct() {
        super();
    }

    public RangedAttackCrossAct(ForceType forceType) {
        super(forceType);
    }

    @Override
    public void performWithoutChain(GameOperator board, Pos pos) {
        if(board.isOnboard(pos) && board.isFree(pos)){
            return;
        }
        performChainAct(board, Acts.RANGE_ATTACK_ACT.copy(),pos,pos.add(-2,0));
        performChainAct(board,Acts.RANGE_ATTACK_ACT.copy(),pos,pos.add(0,-2));
        performChainAct(board,Acts.RANGE_ATTACK_ACT.copy(),pos,pos.add(2,0));
        performChainAct(board,Acts.RANGE_ATTACK_ACT.copy(),pos,pos.add(0,2));
    }
}
