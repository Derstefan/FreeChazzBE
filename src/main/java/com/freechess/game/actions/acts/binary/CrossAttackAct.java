package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.actions.acts.Acts;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.exception.MovementException;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrossAttackAct extends Act {

    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
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
                    Acts.DESTROY_PIECE_ACT.perform(board,p.getPosition());
                }
            }
        }

    Acts.MOVE_ACT.perform(board,pos1,pos2);

    }
}
