package com.freechazz.game.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.actions.conditions.operations.TrivCondition;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Action {


//Act weg und dafür nur Aktion mit optionaler condition ?

    private Condition condition;
    private Act act;
    private char symbol;

    // TODO: oder so?: private ArrayList<Action> furtherActons;
    //private HashMap<String, Action> furtherActions;


    public Action(Condition condition,Act act){
        this.condition = condition;
        this.act= act;
        symbol = 'x';
    }

    public Action(Condition condition,Act act,char symbol){
        this.condition = condition;
        this.act= act;
        this.symbol=symbol;
    }

    public Action(Act act){
        this.condition = new TrivCondition();
        this.act= act;
    }

    public boolean checkCondition(GameState board, Pos pos1, Pos pos2){
        return condition.check(board,pos1,pos2);
    }

    public void perform(GameState board, Pos pos1, Pos pos2){
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

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}




