package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Slf4j
public class ActionMap {
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

    public int size(){
        return actions.size();
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




    public UUID generateUUID() { //needs some work
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(print().getBytes(StandardCharsets.UTF_8));

            long mostSignificantBits = 0;
            long leastSignificantBits = 0;

            for (int i = 0; i < 8; i++) {
                mostSignificantBits = (mostSignificantBits << 8) | (hashBytes[i] & 0xFF);
                leastSignificantBits = (leastSignificantBits << 8) | (hashBytes[i + 8] & 0xFF);
            }

            return new UUID(mostSignificantBits, leastSignificantBits);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
