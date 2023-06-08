package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@Slf4j
public class LaserAct extends Act {

    @Override
    public void perform(GameState board, Pos pos1, Pos pos2) {
        //log.info(this.getClass().getSimpleName() + " performed.");
    }


    public HashSet<Pos> getLaserPositions(GameState board, Pos pos1, Pos pos2){
        if(pos1.equals(pos2)){
            return null;
        }

        int dy = (pos1.getY()-pos2.getY());
        int dx = (pos1.getX()-pos2.getX());
        double l = Math.sqrt(dy*dy+dx*dx);

        HashSet<Pos> laser = new HashSet<>();
        double x = pos2.getX()+0.5;
        double y = pos2.getY()+0.5;

        while(board.isOnboard(new Pos((int)(x+dx/l),(int)(y+dx/l)))){
            laser.add(new Pos((int)(x+dx/l),(int)(y+dx/l)));
            x+=dx;
            y+=dy;
        }


        return laser;
    }

}
