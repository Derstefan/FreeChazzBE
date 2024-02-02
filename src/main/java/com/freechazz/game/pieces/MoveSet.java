package com.freechazz.game.pieces;

import com.freechazz.game.core.ActionPos;

import java.util.ArrayList;

public class MoveSet {
    private ArrayList<ActionPos> possibleMoves = new ArrayList<>();

    public MoveSet(ArrayList<ActionPos> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public MoveSet() {
    }

    public ArrayList<ActionPos> getPossibleMoves() {
        return possibleMoves;
    }

    public int size() {
        return possibleMoves.size();
    }

    public ActionPos getPos(int i) {
        return possibleMoves.get(i);
    }

    public String getTag(int i) {
        return possibleMoves.get(i).getTag();
    }

    public void add(ActionPos pos, String tag) {
        possibleMoves.add(new ActionPos(pos.getX(), pos.getY(), tag));
    }

    public void add(ActionPos pos) {
        possibleMoves.add(pos);
    }


    @Override
    public String toString() {
        String s = "";
        for (ActionPos pos : possibleMoves) {
            s += "(" + pos.getTag() + " " + pos.getX() + " " + pos.getY() + ") ";
        }
        return s;
    }

}
