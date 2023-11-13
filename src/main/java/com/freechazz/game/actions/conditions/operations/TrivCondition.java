package com.freechazz.game.actions.conditions.operations;

import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public class TrivCondition extends Condition {

    @Override
    public  boolean check(GameOperator board, Pos pos1, Pos pos2) {
        return true;
    }

}
