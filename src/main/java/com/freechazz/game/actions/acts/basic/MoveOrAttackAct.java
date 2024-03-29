package com.freechazz.game.actions.acts.basic;

import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.eventManager.events.MoveAndDestroyEvent;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveOrAttackAct extends PieceAct {



    @Override
    public void performWithoutChain(GameOperator state, Pos fromPos, Pos toPos) {

        if(!state.isOnboard(toPos)){
            return;
        }

        Piece piece = state.pieceAt(fromPos);
        Piece targetPiece = state.pieceAt(toPos);
        if(targetPiece!=null && piece!=null){
            state.performEvent(new MoveAndDestroyEvent(fromPos,piece,toPos,targetPiece));
        }

        if (targetPiece == null) {
            state.performEvent(new MoveEvent(fromPos, piece, toPos));
        }
    }



}
