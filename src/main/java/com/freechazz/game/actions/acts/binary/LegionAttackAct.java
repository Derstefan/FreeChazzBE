package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.Actions;
import com.freechazz.game.actions.acts.Act;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LegionAttackAct extends Act {
    @Override
    public void perform(GameState board, Pos pos1, Pos pos2) {
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();
        Pos diff = pos2.minus(pos1);

        for(Piece p: board.getAllPiecesFrom(owner)){
            if(p.getSymbol().equals(piece.getSymbol()) && board.isOnboard(p.getPosition().plus(diff))){
                Actions.MOVE_OR_ATTACK_ACTION.perform(board,p.getPosition(),p.getPosition().plus(diff));
            }
        }
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
