package com.freechazz.game.core;

import java.util.Random;

public enum EPlayer {
    P1, //bottom up
    P2;  //top down


    public EPlayer getOpponent(){
        if(EPlayer.P1.equals(this)){
            return EPlayer.P2;
        }
        return EPlayer.P1;
    }

    public static EPlayer random(){
        return EPlayer.values()[(int) (Math.random()*EPlayer.values().length)];
    }

    public static EPlayer random(long seed){
        Random rand = new Random(seed);
        if(rand.nextDouble() > 0.5){
            return EPlayer.P1;
        }
        return EPlayer.P2;
    }
}
