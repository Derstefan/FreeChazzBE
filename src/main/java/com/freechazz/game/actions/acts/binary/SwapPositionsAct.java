package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.eventManager.events.SwapEvent;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapPositionsAct extends PieceAct {
    @Override
    public void performWithoutChain(GameOperator state, Pos pos1, Pos pos2) {
        Piece piece = state.pieceAt(pos1);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = state.pieceAt(pos2);
        if (targetPiece != null && piece!=null) {
            if (targetPiece.getOwner().equals(owner)) {
                state.performEvent(new SwapEvent(pos1,pos2));
            }
        }
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
