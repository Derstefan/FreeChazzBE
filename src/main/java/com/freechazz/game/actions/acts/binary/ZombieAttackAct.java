package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.eventManager.events.ChangeOwnerEvent;
import com.freechazz.game.eventManager.events.ChangeTypeEvent;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZombieAttackAct extends Act {

    @Override
    public void perform(GameOperator state, Pos fromPos, Pos toPos) {
        Piece piece = state.pieceAt(fromPos);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = state.pieceAt(toPos);
        if (targetPiece != null && piece!=null) {
            if (!targetPiece.getOwner().equals(owner)) {
                Piece zombie = new Piece(piece.getOwner(),piece.getPieceType());
                state.performEvent(new ChangeOwnerEvent(zombie));
                state.performEvent(new ChangeTypeEvent(zombie,zombie.getPieceType(),piece.getPieceType()));
            }
        }
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
