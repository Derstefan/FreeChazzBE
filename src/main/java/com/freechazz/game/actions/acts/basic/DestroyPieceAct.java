package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.UnitaryAct;
import com.freechazz.game.eventManager.events.DestroyEvent;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DestroyPieceAct extends UnitaryAct {
    @Override
    public void perform(GameOperator state, Pos pos) {
        Piece targetPiece = state.pieceAt(pos);
        if(targetPiece!=null){
            state.performEvent(new DestroyEvent(targetPiece, pos));
          //  log.info(this.getClass().getSimpleName() + " performed.");
        }

    }
}
