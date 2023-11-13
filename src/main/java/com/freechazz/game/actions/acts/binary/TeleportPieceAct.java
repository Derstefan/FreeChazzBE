package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeleportPieceAct extends PieceAct {
    @Override
    public void performWithoutChain(GameOperator board, Pos pos1, Pos pos2) {
        Acts.TELEPORT_INVERSE_ACT.performWithoutChain(board,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
