package com.freechazz.game.actions.conditions;

import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public abstract class UnitaryCondition extends Condition {
    protected abstract boolean check(GameOperator board, Pos pos);

    @Override
    public boolean check(GameOperator board, Pos pos1, Pos pos2){
        return check(board,pos2);
    }
}
