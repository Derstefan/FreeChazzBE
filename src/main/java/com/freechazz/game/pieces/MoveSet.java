package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.core.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MoveSet {
    private ArrayList<Pos> possibleMoves = new ArrayList<>();

    public MoveSet(ArrayList<Pos> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public MoveSet() {
    }

    public ArrayList<Pos> getPossibleMoves() {
        return possibleMoves;
    }

    public int size() {
        return possibleMoves.size();
    }

    public Pos getPos(int i) {
        return possibleMoves.get(i);
    }
    public String getTag(int i) {
        return possibleMoves.get(i).getTag();
    }

    public void add(Pos pos, String tag) {
        possibleMoves.add(new Pos(pos.getX(),pos.getY(),tag));
    }

    public void add(Pos pos) {
        possibleMoves.add(pos);
    }

}
