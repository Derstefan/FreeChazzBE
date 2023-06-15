package com.freechazz.game.state;

import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;

public class BoardUtil {


    public static int distance(Piece p1, Piece p2){
        return Math.abs(p1.getPos().getX()-p2.getPos().getX()) + Math.abs(p1.getPos().getY()-p2.getPos().getY());
    }

    public static int distance(Pos pos1, Pos pos2){
        return Math.abs(pos1.getX()-pos2.getX()) + Math.abs(pos1.getY()-pos2.getY());
    }
}
