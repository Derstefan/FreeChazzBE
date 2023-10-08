package com.freechazz.game.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pos implements Comparable<Pos> {

    private int x;
    private int y;


    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    public Pos add(int dx, int dy) {
        return new Pos(x + dx, y + dy);
    }

    public boolean memberOf(ArrayList<Pos> positions) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getX() == getX() && positions.get(i).getY() == getY()) {
                return true;
            }
        }
        return false;
    }


    @JsonIgnore
    public HashSet<Pos> getPosAround(){
        HashSet<Pos> posAround = new HashSet<>();
        posAround.add(new Pos(x-1,y-1));
        posAround.add(new Pos(x,y-1));
        posAround.add(new Pos(x+1,y-1));
        posAround.add(new Pos(x-1,y));
        posAround.add(new Pos(x+1,y));
        posAround.add(new Pos(x-1,y+1));
        posAround.add(new Pos(x,y+1));
        posAround.add(new Pos(x+1,y+1));
        return posAround;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos position = (Pos) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean isIn(Set<Pos> positions){
        for(Pos p: positions){
            if(this.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public Pos minus(Pos pos) {
        return new Pos(this.getX() - pos.getX(), this.getY() - pos.getY());
    }

    public Pos plus(Pos pos) {
        return new Pos(this.getX() + pos.getX(), this.getY() + pos.getY());
    }

    public Pos invertY() {
        return new Pos(this.getX(), - this.getY());
    }

    public String toString() {
        return "(x=" + x + ",y=" + y + ")";
    }

    public Pos copy(){
        return new Pos(getX(),getY());
    }

    @Override
    public int compareTo(Pos position) {
        if (getX() < position.getX()) {
            return -1;
        }
        if (getX() > position.getX()) {
            return 1;
        }
        // x_1=x_2
        if (getY() < position.getY()) {
            return -1;
        }
        if (getY() > position.getY()) {
            return 1;
        }
        return 0;
    }
}
