package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExplodeAct extends Act {

    @Override
    public void perform(GameOperator board, Pos pos1, Pos pos2) {
        Acts.MOVE_ACT.perform(board,pos1,pos2);
        Acts.EXPLOSION_ACT.perform(board,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
