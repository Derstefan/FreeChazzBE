package com.freechazz.game.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.actions.conditions.operations.TrivCondition;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Action {


//Act weg und dafür nur Aktion mit optionaler condition ?

    private Condition condition;
    private Act act;
    private String symbol;

    // TODO: oder so?: private ArrayList<Action> furtherActons;
    //private HashMap<String, Action> furtherActions;


    public Action(Condition condition,Act act,String symbol){
        this.condition = condition;
        this.act= act;
        this.symbol=symbol;
    }

    public Action(Act act){
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
    @JsonIgnore
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @JsonIgnore
    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}




