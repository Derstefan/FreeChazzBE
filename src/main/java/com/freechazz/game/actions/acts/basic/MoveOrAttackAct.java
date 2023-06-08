package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveOrAttackAct extends Act {



    @Override
    public void perform(GameState board, Pos pos1, Pos pos2){
        Acts.DESTROY_PIECE_ACT.perform(board,pos2);
        Acts.MOVE_ACT.perform(board,pos1,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }

}
