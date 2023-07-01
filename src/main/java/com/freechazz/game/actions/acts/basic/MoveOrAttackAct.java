package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveOrAttackAct extends Act {



    @Override
    public void perform(GameOperator state, Pos pos1, Pos pos2){
        Acts.DESTROY_PIECE_ACT.perform(state,pos2);
        Acts.MOVE_ACT.perform(state,pos1,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }

}
