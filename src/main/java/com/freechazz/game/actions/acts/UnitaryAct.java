package com.freechazz.game.actions.acts;

import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

public abstract class UnitaryAct extends Act {
    public abstract void perform(GameState board, Pos pos);

    public void perform(GameState board, Pos pos1, Pos pos2){
        perform(board,pos2);
    }
}
