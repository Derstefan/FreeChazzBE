package com.freechazz.archiv;

import com.freechazz.game.Game;
import com.freechazz.bots.Bot;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class StupidBot extends Bot {


    public StupidBot(EPlayer player) {
        super(player,123);
    }

    public static void doRandomDraw(Game game){

        ArrayList<Piece> pieces = game.getState().getAllPiecesFrom(EPlayer.P2);
        if(pieces.size()==0)return;
        int index = (int) (Math.random()*pieces.size());
        Piece p = pieces.get(index);

        ArrayList<Pos> posList = p.getPossibleMoves();
        if(posList.size()==0)return;
        int index2 = (int) (Math.random()*posList.size());
        Pos pos = posList.get(index2);

        game.play(p.getPosition(),pos);
        game.getPlayer2().setLastActionTime();
    }

    /**
     * prefers attacks
     * @param game
     */
    @Override
    public void doDraw(Game game){

        ArrayList<Piece> pieces = game.getState().getAllPiecesFrom(EPlayer.P2);

        HashMap<Piece,ArrayList<Pos>> posMap = new HashMap<>();

        for(Piece p:pieces){
            ArrayList<Pos> posList = p.getPossibleMoves();
            ArrayList<Pos> posListAttack = new ArrayList<>();
            for(Pos pos:posList){
                Piece piece = game.getState().pieceAt(pos);
                if(piece!=null){
                    if(!EPlayer.P2.equals(piece.getOwner())){
                        if(piece.isKing()){
                            game.play(p.getPosition(),pos);
                            game.getPlayer2().setLastActionTime();
                        }
                        posListAttack.add(pos);
                    }
                }
            }
            if(posListAttack.size()!=0){
                posMap.put(p,posListAttack);
            }
        }
        if(posMap.keySet().isEmpty()){ // if no attacks available
            doRandomDraw(game);
            return;
        }

        int index = (int) (Math.random()*posMap.values().size());
        Piece p = (Piece)posMap.keySet().toArray()[index];

        int index2 = (int) (Math.random()*posMap.get(p).size());
        Pos pos = posMap.get(p).get(index2);

        game.play(p.getPosition(),pos);
        game.getPlayer2().setLastActionTime();
    }



}
