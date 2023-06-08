package com.freechazz.game.actions.acts;

import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;

public abstract class UnitaryAct extends Act {
    public abstract void perform(GameState board, Pos pos);

    public void perform(GameState state, Pos pos1, Pos pos2){
        perform(state,pos2);
    }
}
