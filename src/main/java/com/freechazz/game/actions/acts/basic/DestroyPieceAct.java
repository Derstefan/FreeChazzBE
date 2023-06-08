package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.UnitaryAct;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DestroyPieceAct extends UnitaryAct {
    @Override
    public void perform(GameState board, Pos pos) {
        Piece targetPiece = board.pieceAt(pos);
        if(targetPiece!=null){
            board.destroy(pos);
          //  log.info(this.getClass().getSimpleName() + " performed.");
        }

    }
}
