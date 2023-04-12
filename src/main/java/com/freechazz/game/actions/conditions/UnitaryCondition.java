package com.freechazz.game.actions.conditions;

import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

public abstract class UnitaryCondition extends Condition {
    protected abstract boolean check(GameState board, Pos pos);

    @Override
    public boolean check(GameState board, Pos pos1, Pos pos2){
        return check(board,pos2);
    }
}
