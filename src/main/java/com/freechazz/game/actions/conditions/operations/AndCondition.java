package com.freechazz.game.actions.conditions.operations;

import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public class AndCondition extends Condition {

    private Condition cond1;
    private Condition cond2;

    public AndCondition(Condition cond1,Condition cond2){
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    @Override
    public boolean check(GameOperator board, Pos pos1, Pos pos2) {
        return cond1.check(board,pos1,pos2) && cond2.check(board,pos1,pos2);
    }

    public Condition getCond1() {
        return cond1;
    }

    public Condition getCond2() {
        return cond2;
    }
}
