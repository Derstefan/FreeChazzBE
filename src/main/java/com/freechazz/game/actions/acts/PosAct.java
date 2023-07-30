package com.freechazz.game.actions.acts;

import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;

public abstract class PosAct extends Act {
    public abstract void performWithoutChain(GameOperator board, Pos pos);

    public void perform(GameOperator state, Pos pos){
        performWithoutChain(state,pos);
        if(getConnector()!=null){
            getConnector().perform(state,pos);
        }
    }


    public PosAct copy(){
        PosAct act = null;
        try {
            act = this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return act;
    }
}
