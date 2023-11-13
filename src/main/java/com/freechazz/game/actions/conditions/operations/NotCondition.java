package com.freechazz.game.actions.conditions.operations;

import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public class NotCondition extends Condition {

    private Condition cond;

    public NotCondition(Condition cond){
        this.cond = cond;

    }

    @Override
    public boolean check(GameOperator board, Pos pos1, Pos pos2) {

        return !cond.check(board,pos1,pos2);
    }

    public Condition getCond() {
        return cond;
    }
}
