package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.eventManager.events.DestroyEvent;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

/**
 * Attacks all pieces (enemy and friends) in a line
 */
@Slf4j
public class RushAct extends Act {
    @Override
    public void perform(GameOperator state, Pos fromPos, Pos toPos){

        Piece piece = state.pieceAt(fromPos);
        // check equal pos
        if(fromPos.equals(toPos)){
            throw new IllegalArgumentException();
        }
        // check diagonal

        int dy = (fromPos.getY()-toPos.getY());
        int dx = (fromPos.getX()-toPos.getX());
        int l = Math.max(Math.abs(dy),Math.abs(dx));

        int x = fromPos.getX();
        int y = fromPos.getY();

        for(int i=1;i<=l;i++){
            x-=dx/l;
            y-=dy/l; //HERE is the minus!!!
            Pos pos = new Pos(x,y);
            if(!state.isFree(pos)){
                Piece targetPiece = state.pieceAt(pos);
                state.performEvent(new DestroyEvent(targetPiece, pos));
                //state.destroy(new Pos(x,y));
            }
        }
        state.performEvent(new MoveEvent(fromPos, piece, toPos));
        //state.move(fromPos,toPos);
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
