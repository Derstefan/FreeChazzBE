package com.freechazz.game.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.actions.conditions.operations.TrivCondition;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Action {


//Act weg und dafür nur Aktion mit optionaler condition ?

    private Condition condition;
    private PieceAct act;
    private String symbol;

    // TODO: oder so?: private ArrayList<Action> furtherActons;
    //private HashMap<String, Action> furtherActions;


    public Action(Condition condition,PieceAct act,String symbol){
        this.condition = condition;
        this.act= act;
        this.symbol=symbol;
    }

    public Action(PieceAct act){
        this.condition = new TrivCondition();
        this.act= act;
    }

    public boolean checkCondition(GameOperator board, Pos pos1, Pos pos2){
        return condition.check(board,pos1,pos2);
    }

    public void perform(GameOperator board, Pos pos1, Pos pos2){
        if(checkCondition(board, pos1, pos2)){
            act.perform(board, pos1, pos2);
        }
    }

    public void performWithoutChain(GameOperator board, Pos pos1, Pos pos2){
        if(checkCondition(board, pos1, pos2)){
            act.performWithoutChain(board, pos1, pos2);
        }
    }

    @JsonIgnore
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @JsonIgnore
    public PieceAct getAct() {
        return act;
    }

    public void setAct(PieceAct act) {
        this.act = act;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public Action copy(){
        Action action = new Action(condition,act.copy(),symbol);
        return action;
    }
}




