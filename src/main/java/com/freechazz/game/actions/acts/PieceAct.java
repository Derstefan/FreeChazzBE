package com.freechazz.game.actions.acts;

import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameOperator;

public abstract class PieceAct extends Act {

    public abstract void performWithoutChain(GameOperator board, Pos pos1, Pos pos2);

    public void perform(GameOperator board, Pos pos1, Pos pos2){
        performWithoutChain(board,pos1,pos2);
        if(getConnector()!=null){
            getConnector().perform(board,pos1,pos2);
        }
    }


    public PieceAct copy(){
        PieceAct act = null;
        try {
            act = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return act;
    }

}
