package com.freechazz.game.pieces;

import com.freechazz.game.core.ActionPos;

import java.util.ArrayList;

public class MoveSet {
    private ArrayList<ActionPos> pm = new ArrayList<>();

    public MoveSet(ArrayList<ActionPos> possibleMoves) {
        this.pm = possibleMoves;
    }

    public MoveSet() {
    }

    public ArrayList<ActionPos> getPm() {
        return pm;
    }

    public int size() {
        return pm.size();
    }

    public ActionPos getPos(int i) {
        return pm.get(i);
    }

    public String getTag(int i) {
        return pm.get(i).getTag();
    }

    public void add(ActionPos pos, String tag) {
        pm.add(new ActionPos(pos.getX(), pos.getY(), tag));
    }

    public void add(ActionPos pos) {
        pm.add(pos);
    }


    @Override
    public String toString() {
        String s = "";
        for (ActionPos pos : pm) {
            s += "(" + pos.getTag() + " " + pos.getX() + " " + pos.getY() + ") ";
        }
        return s;
    }

}
