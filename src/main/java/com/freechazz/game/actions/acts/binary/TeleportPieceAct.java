package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeleportPieceAct extends Act {
    @Override
    public void perform(GameState board, Pos pos1, Pos pos2) {
        Acts.TELEPORT_INVERSE_ACT.perform(board,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
