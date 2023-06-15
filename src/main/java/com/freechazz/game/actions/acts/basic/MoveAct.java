package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveAct extends Act {

    @Override
    public void perform(GameState state, Pos fromPos, Pos toPos) {
        Piece piece = state.pieceAt(fromPos);
        Piece targetPiece = state.pieceAt(toPos);


        if (targetPiece == null) {
            state.move(fromPos,toPos);
           // log.info(this.getClass().getSimpleName() + " performed.");
        }

    }
}
