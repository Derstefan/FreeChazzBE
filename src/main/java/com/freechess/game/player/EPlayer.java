package com.freechess.game.player;

public enum EPlayer {
    P1, //bottom upp
    P2;  //top down


    private EPlayer getOpponent(){
        if(EPlayer.P1.equals(this)){
            return EPlayer.P2;
        }
        return EPlayer.P1;
    }
}
