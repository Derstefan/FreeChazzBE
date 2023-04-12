package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.core.Pos;

import java.util.*;


public class ActionMap {
    public static final int width = 7;
    public static final int height = 7;


    private Map<Pos, Action> actions = new HashMap<>();

    public boolean put(Pos pos, Action action){
//        ArrayList<Pos> posList = new ArrayList<>();
//        for(Pos p: keySet()){
//            if(p.equals(pos)){
//                posList.add(p);
//            }
//
//        }
//        for(Pos p: posList){
//            actions.remove(p);
//        }
        actions.put(pos,action);
        return true;
    }

    public void putAll(HashMap<Pos, Action> actions){
        for(Pos p:actions.keySet()){
            put(p,actions.get(p));
        }
    }

    public Action get(Pos pos){
        Action action = null;
        for(Pos p: keySet()){
            if(p.equals(pos)){
                action= actions.get(p);
            }
        }
        return action;
    }

    public HashSet<Action> getActionSet(){
        HashSet<Action> actionSet = new HashSet<>();
        for(Pos p: keySet()){
            actionSet.add(actions.get(p));
        }
        return actionSet;
    }

    public int counter(Pos pos){
        int number =0;
        for(Pos p: keySet()){
            if(p.equals(pos)){
                number++;
            }
        }
        return number;
    }

    public Set<Pos> keySet(){
        return actions.keySet();
    }

    public void clear(){
        actions.clear();
    }

    public int size(){
        return actions.size();
    }


/*    public Map<Position, Action> getActions() {
        return actions;
    }*/

    //zur serialisierung gleich ein array
    //TODO: zur optimierung vll eine liste?
    public String[][] getActions(){

        String[][] moves = new String[2* width +1][2* height +1];
        for (int i = 0; i < moves.length; i++) {
        for (int j = 0; j < moves.length; j++) {
            moves[i][j] = "-";
            if (i == width && j == height) {
                moves[i][j] = "P";
            }
        }
    }
        for (Pos pos : actions.keySet()) {
        moves[pos.getX() + width][pos.getY() + height] = ""+actions.get(pos).getSymbol();
    }
        return moves;
    }


    public void setActions(Map<Pos, Action> actions) {
        this.actions = actions;
    }

    public String print() {
        Set<Pos> positions = actions.keySet();
        String s = "";
        for (Pos pos : positions) {
            s += " " + actions.get(pos).getSymbol() + ""+pos.toString();
        }
        return s;
    }
}
