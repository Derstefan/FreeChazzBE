package com.freechazz.game.actions.acts;

import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public abstract class UnitaryAct extends Act {
    public abstract void perform(GameOperator board, Pos pos);

    public void perform(GameOperator state, Pos pos1, Pos pos2){
        perform(state,pos2);
    }
}
