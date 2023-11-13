package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveAct extends PieceAct {

    @Override
    public void performWithoutChain(GameOperator state, Pos fromPos, Pos toPos) {
        Piece piece = state.pieceAt(fromPos);
        Piece targetPiece = state.pieceAt(toPos);


        if (targetPiece == null) {
            state.performEvent(new MoveEvent(fromPos, piece, toPos));
        }

    }
}
