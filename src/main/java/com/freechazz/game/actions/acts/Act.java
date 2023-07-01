package com.freechazz.game.actions.acts;

import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public abstract class Act {

    public abstract void perform(GameOperator board, Pos pos1, Pos pos2);

}
