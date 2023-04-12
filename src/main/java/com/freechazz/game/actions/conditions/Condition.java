package com.freechazz.game.actions.conditions;

import com.freechazz.game.actions.conditions.operations.AndCondition;
import com.freechazz.game.actions.conditions.operations.NotCondition;
import com.freechazz.game.actions.conditions.operations.OrCondition;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

public abstract class Condition {

    public abstract boolean check(GameState board, Pos pos1, Pos pos2);

    public AndCondition AND(Condition cond2){
        return new AndCondition(this,cond2);
    }

    public OrCondition OR(Condition cond2){
        return new OrCondition(this,cond2);
    }

    public NotCondition NOT(){
        return new NotCondition(this);
    }
}
