package com.freechazz.game.actions.acts;

import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

public abstract class Act {

    public abstract void perform(GameState board, Pos pos1, Pos pos2);

}
