package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrossAttackAct extends Act {

    @Override
    public void perform(GameOperator board, Pos pos1, Pos pos2) {
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = board.pieceAt(pos2);

        if (targetPiece != null) {
            log.warn("first only move own piece.");
        }
        Piece[] ps = new Piece[4];
        ps[0] = board.pieceAt(pos2.add(1,1));
        ps[1] = board.pieceAt(pos2.add(-1,1));
        ps[2] = board.pieceAt(pos2.add(1,-1));
        ps[3] = board.pieceAt(pos2.add(-1,-1));

        for(Piece p:ps){
            if(p!=null){
                if(!owner.equals(p.getOwner())){
                    Acts.DESTROY_PIECE_ACT.perform(board,p.getPos());
                }
            }
        }

    Acts.MOVE_ACT.perform(board,pos1,pos2);
        //log.info(this.getClass().getSimpleName() + " performed.");

    }
}
