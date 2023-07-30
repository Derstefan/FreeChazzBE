package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SacreficeAct extends PieceAct {


    @Override
    public void performWithoutChain(GameOperator board, Pos pos1, Pos pos2){
        //TODO: sacrefice and than happens something
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
