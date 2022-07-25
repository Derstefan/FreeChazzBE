package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExplosionAct extends Act {



    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {


        Piece piece = board.pieceAt(pos1);
        if(board.isFree(pos2) || !board.isFree(pos1)){
            log.warn("something is wrong");
        }

        board.removePiece(pos1);
        board.addPiece(piece,pos2);


        Piece[] ps = new Piece[9];
        ps[0] = board.pieceAt(pos2.add(-1,-1));
        ps[1] = board.pieceAt(pos2.add(0,-1));
        ps[2] = board.pieceAt(pos2.add(1,-1));
        ps[3] = board.pieceAt(pos2.add(-1,0));
        ps[4] = board.pieceAt(pos2.add(0,0));
        ps[5] = board.pieceAt(pos2.add(1,0));
        ps[6] = board.pieceAt(pos2.add(-1,1));
        ps[7] = board.pieceAt(pos2.add(0,1));
        ps[8] = board.pieceAt(pos2.add(1,1));


        for(Piece p:ps){
            if(p!=null){
                board.takePiece(p.getPosition());
            }
        }


    }
}
