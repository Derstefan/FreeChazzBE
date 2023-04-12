package com.freechazz.game.actions.conditions.operations;

import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

public class TrivCondition extends Condition {

    @Override
    public  boolean check(GameState board, Pos pos1, Pos pos2) {
        return true;
    }

}
