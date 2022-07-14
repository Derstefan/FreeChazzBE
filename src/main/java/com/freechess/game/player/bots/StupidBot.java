package com.freechess.game.player.bots;

import com.freechess.game.Game;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class StupidBot {
    public static long DRAW_DELAY = 3200;

    public static void doRandomDraw(Game game){

        ArrayList<Piece> pieces = game.getBoard().getAllPiecefrom(EPlayer.P2);
        if(pieces.size()==0)return;
        int index = (int) (Math.random()*pieces.size());
        Piece p = pieces.get(index);

        ArrayList<Position> posList = p.getPossibleMoves();
        if(posList.size()==0)return;
        int index2 = (int) (Math.random()*posList.size());
        Position pos = posList.get(index2);

        game.play(p.getPosition(),pos);
        game.getPlayer2().setLastActionTime();
    }

    /**
     * prefers attacks
     * @param game
     */
    public static void doBetterRandomDraw(Game game){

        ArrayList<Piece> pieces = game.getBoard().getAllPiecefrom(EPlayer.P2);

        HashMap<Piece,ArrayList<Position>> posMap = new HashMap<>();

        for(Piece p:pieces){
            ArrayList<Position> posList = p.getPossibleMoves();
            ArrayList<Position> posListAttack = new ArrayList<>();
            for(Position pos:posList){
                Piece piece = game.getBoard().pieceAt(pos);
                if(piece!=null){
                    if(!EPlayer.P2.equals(piece.getOwner())){
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
        Position pos = posMap.get(p).get(index2);

        game.play(p.getPosition(),pos);
        game.getPlayer2().setLastActionTime();
    }

}
