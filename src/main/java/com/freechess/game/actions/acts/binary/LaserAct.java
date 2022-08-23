package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;

import java.util.HashMap;
import java.util.HashSet;

public class LaserAct extends Act {

    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {

    }


    public HashSet<Position> getLaserPositions(Board board,Position pos1, Position pos2){
        if(pos1.equals(pos2)){
            return null;
        }

        int dy = (pos1.getY()-pos2.getY());
        int dx = (pos1.getX()-pos2.getX());
        double l = Math.sqrt(dy*dy+dx*dx);

        HashSet<Position> laser = new HashSet<>();
        double x = pos2.getX()+0.5;
        double y = pos2.getY()+0.5;

        while(board.isOnboard(new Position((int)(x+dx/l),(int)(y+dx/l)))){
            laser.add(new Position((int)(x+dx/l),(int)(y+dx/l)));
            x+=dx;
            y+=dy;
        }

        return laser;
    }

}
