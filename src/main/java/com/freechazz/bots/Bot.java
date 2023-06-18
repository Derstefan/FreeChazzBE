package com.freechazz.bots;

import com.freechazz.game.Game;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.pieces.Piece;
import com.freechazz.server.DTO.game.client.DrawData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Slf4j
public abstract  class Bot {

    public static long DRAW_DELAY = 0;

    private final EPlayer player;

    private boolean isReady = true;

    private Random rand;

    //Statistics
    private int drawsCounter = 0;


    public EPlayer getPlayer() {
        return player;
    }

    public Bot(EPlayer player,long seed)
    {this.rand = new Random(seed);
        this.player = player;
    }

    public Bot(EPlayer player)
    {
        this.rand = new Random((long)(Math.random()*1213987));
        this.player = player;
    }



    public void doDrawOn(Game game){
        if(isReady){
            long startTime = System.currentTimeMillis();
            drawsCounter =0;
            //isReady=false;
//            log.info("Bot started computing draws in game:" +game.toString() + " bot is " + player + ", game is " + game.getPlayersTurn());
            doDraw(game);
//            log.info("Bot computed " +drawsCounter+" draws: "
//                    + (System.currentTimeMillis()-startTime)
//                    + " ms in game:" +game.getGameId()
//                    + " piece count " + game.getState().getAllPieces().size()
//                    + "("+game.getState().getAllPiecesFrom(EPlayer.P1).size()+","
//                    +game.getState().getAllPiecesFrom(EPlayer.P2).size()+")");
//            log.info(game.getState().toString());
            isReady = true;
        }
    };

    protected abstract void doDraw(Game game);

    public int getDrawsCounter() {
    	return drawsCounter;
    }

    public void newDraw() {
    	drawsCounter++;
    }

    public Random getRand() {
        return rand;
    }


    public DrawData randomDraw(ArrayList<DrawData> draws){
        if(draws.isEmpty())throw new IllegalArgumentException("Draws list is empty");
        int index = (int) (rand.nextDouble()*draws.size());
        return draws.get(index);
    }




    public ArrayList<Piece> diff(ArrayList<Piece> list1, ArrayList<Piece> list2) {
        return BotUtil.diff(list1,list2);
    }


    public ArrayList<DrawData> getBestDraws(HashMap<DrawData,Double> draws){
        return BotUtil.getBestDraws(draws);
    }

    public ArrayList<DrawData> getBestDrawsWithTolerance(HashMap<DrawData,Double> draws, double tolerance){
        return BotUtil.getBestDrawsWithTolerance(draws,tolerance);
    }

}
