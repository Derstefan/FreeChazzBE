package com.freechazz.game.actions.connector;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameOperator;
import com.freechazz.generators.action.PosSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connector {

    private Act previousAct;
    private Map<PosSet,Act> nextActs = new HashMap<>();
    public Connector(Act previousAct) {
        this.previousAct = previousAct;
    }

    public Connector() {
    }

    //perform function for binary acts
    public void perform(GameOperator board,Pos fromPos, Pos toPos){
        for(PosSet posSet : getNextActs().keySet()){
        switch (posSet){
            case fromPos:
                ((PosAct)getNextActs().get(posSet)).performWithoutChain(board,fromPos);
                break;
            case toPos:
                ((PosAct)getNextActs().get(posSet)).performWithoutChain(board,toPos);
                break;
            case fromPosAround:
                for (Pos pAround: fromPos.getPosAround()) {
                    ((PosAct)getNextActs().get(posSet)).performWithoutChain(board,pAround);
                }
                break;
            case toPosAround:
                for (Pos pAround: toPos.getPosAround()) {
                    ((PosAct)getNextActs().get(posSet)).performWithoutChain(board,pAround);
                }
                break;

        }
        }
    }

    //perform function for unitary acts
    public void perform(GameOperator board,Pos pos){
        for(PosSet posSet : getNextActs().keySet()){
            switch (posSet){
                case pos:
                    ((PosAct)getNextActs().get(posSet)).performWithoutChain(board,pos);
                    break;
                case posAround:
                    for (Pos pAround: pos.getPosAround()) {
                        ((PosAct)getNextActs().get(posSet)).performWithoutChain(board,pAround);
                    }
                    break;
                default:
                    break;
            }
        }
    }



    public void addPosSets(List<PosSet> posSets, Act act){
        posSets.forEach(posSet -> getNextActs().put(posSet,act));
    }

    public void addChain(PosSet posSet, Act act){
       getNextActs().put(posSet,act);
    }

    public void addActOverall(Act act){
        getNextActs().keySet().forEach(posSet -> getNextActs().put(posSet,act));
    }


    public Act getPreviousAct() {
        return previousAct;
    }

    public void setPreviousAct(Act previousAct) {
        this.previousAct = previousAct;
    }

    public Map<PosSet, Act> getNextActs() {
        return nextActs;
    }

    public String toString(){
        return ": " + getPosSetSize() + " -> " + nextActs.values().toArray()[0].toString();
    }

    private int getPosSetSize(){
        int sum =0;
        for (PosSet posSet: nextActs.keySet()) {
            sum += posSet.getFieldCount();
        }
        return sum;
    }
}
