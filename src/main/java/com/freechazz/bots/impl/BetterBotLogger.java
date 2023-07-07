package com.freechazz.bots.impl;

import com.freechazz.bots.Bot;
import com.freechazz.bots.BotUtil;
import com.freechazz.game.Game;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.network.DTO.game.client.DrawDataDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Slf4j
public class BetterBotLogger extends Bot {

    private static final int NOTHING = 0;
    private static final int BEAT = 20;
    private static final int LOSE = 10;
    private static final int KING = 100;
    private static final int MOVE_TO_ENEMY = 10;
    private int MAX_DEPTH = 1;
    private static final int MIN_DRAWS_CHECKING =7;
    private static final int MIN_DRAWS_CHECKING_HISTORY =4;
    private static final int MIN_DRAWS_CHECKING_DEEP = 4;
    private static final int MIN_DRAWS_CHECKING_HISTORY_DEEP = 3;




    //Statistics


    public BetterBotLogger(EPlayer player, int maxDepth, long seed) {
        super(player,seed);
        this.MAX_DEPTH = maxDepth;
    }

    @Override
    public void doDraw(Game game){

        HashMap<DrawDataDTO,Double> evaluatedDraws = evaluateDraws(game);
        DrawDataDTO bestDraw = randomDraw(BotUtil.getBestDraws(evaluatedDraws));
        log.info("final Draw: id:" + game.getState().pieceAt(bestDraw.getFromPos()).getId() + " : "+bestDraw.toString() + " " + (game.getState().pieceAt(bestDraw.getToPos())!=null?"attack id:"+ game.getState().pieceAt(bestDraw.getToPos()).getId():""));
        game.play( bestDraw.getFromPos(),bestDraw.getToPos());

    }


    private HashMap<DrawDataDTO,Double> evaluateDraws(Game game){
        Game gameCopy = game.copy();
        HashMap<DrawDataDTO,Double> drawValues = new HashMap<>();
        ArrayList<DrawDataDTO> draws = getDraws(game,game.getPlayersTurn());
        if(draws.isEmpty()){
            game.surrender();
            return null; //TODO: check if its really surrender
        }
        sortDrawData(draws,game);

        double bestValue = -Double.MAX_VALUE/4;
        int drawCounter = 0;
        int lastGoodDraw = 0;
       //log.info("draws count: "+draws.size());
        for (DrawDataDTO draw : draws) {

            double value = evaluateDrawWithoutCloning(gameCopy,draw, MAX_DEPTH);
            if(value>bestValue){
                bestValue=value;
                lastGoodDraw = drawCounter;
            }
            log.info("----Draw: " + drawCounter + " , "+draw.toString() + " , value: " + value + " , best: " + bestValue);
            if(value<=-Double.MAX_VALUE/4) { //enemy can beat so do not use
                continue;
            }
            drawValues.put(draw,value);
            if(value==Double.MAX_VALUE)return drawValues; //here you can beat him



            if(drawCounter>MIN_DRAWS_CHECKING && drawCounter-lastGoodDraw>MIN_DRAWS_CHECKING_HISTORY){
               // log.info("draws count: "+drawCounter);
                break;
            }
            drawCounter++;

        }

        if(drawValues.isEmpty()){
            if(draws.isEmpty()){
                game.surrender();
                return null; //TODO: check if its really surrender
            }
            drawValues.put(randomDraw(draws),0.0);
            return drawValues;
        }
        return drawValues;
    }







    private double evaluateDrawWithoutCloning(Game game, DrawDataDTO drawData, int depth){
        //log.info("depth: "+depth + "\n\n");
        ArrayList<Piece> graveYardBefore = new ArrayList<>(game.getState().getGraveyard());

        //log.info(" number of pieces: " + game.getState().getAllPieces().size());
        //log.info(game.getState().toString());
        game.play(drawData.getFromPos(),drawData.getToPos());
        newDraw();
        //log.info( " : " + depth + "draw: "+drawData.getFromPos()+" -> "+drawData.getToPos());

        ArrayList<Piece> graveYardAfter = game.getState().getGraveyard();


        double sum = weightOf(evaluateDraw(game,drawData,graveYardBefore,graveYardAfter),depth);
        if(depth==MAX_DEPTH && sum >=weightOf(KING,MAX_DEPTH)/2){// if can hit king
            game.undo();
            //log.info(game.getPlayersTurn() + " could hit king #################################################");
            return Double.MAX_VALUE;
        }
        if(depth==MAX_DEPTH-1 && sum <=-weightOf(KING,MAX_DEPTH-1)/2){
            game.undo();
            return -Double.MAX_VALUE/2;
        }

        if(depth==0){
            game.undo();
            return sum;
        }
        ArrayList<DrawDataDTO> opponentDraws = new ArrayList<>(getDraws(game,game.getPlayersTurn()));
        if(opponentDraws.isEmpty()){
            game.undo();
            return -Double.MAX_VALUE/4;
        }
        sortDrawData(opponentDraws,game);
        double bestValue = -1000000;
        int drawCounter = 1;
        int lastGoodDraw = 0;
        double tempSum = 0;
        for(DrawDataDTO d: opponentDraws){

            double value = evaluateDrawWithoutCloning(game,d,depth-1);
            tempSum+=value;
            if(value>bestValue){
                bestValue=value;
                lastGoodDraw = drawCounter;
            }
            //log.info( (depth<2?"    ":"") + ""+ depth+ " depth draw: "+drawCounter+"/"+opponentDraws.size()+" , "+d.toString() + "value " + value + " best: " + bestValue);
            drawCounter++;
            if(drawCounter>MIN_DRAWS_CHECKING_DEEP && drawCounter-lastGoodDraw>MIN_DRAWS_CHECKING_HISTORY_DEEP){
                break;
            }


        }
        sum+=tempSum/drawCounter;
        game.undo();
        return sum;
    }

    private ArrayList<DrawDataDTO> getDraws(Game game, EPlayer player){
        //game.computePossibleMoves();
        ArrayList<DrawDataDTO> draws = new ArrayList<>();
        ArrayList<Piece> pieces = game.getState().getAllPiecesFrom(player);

        for(Piece p:pieces) {
            for (Pos pos : p.getPossibleMoves()) {
                DrawDataDTO draw = new DrawDataDTO(p.getPos(), pos);
                draws.add(draw);
            }
        }

        return draws;
    }



    private void sortDrawData(ArrayList<DrawDataDTO> draws, Game game){

        if(draws.isEmpty())throw new IllegalArgumentException("no draws to sort");

        EPlayer enemy = game.getState().pieceAt(draws.get(0).getFromPos()).getOwner().getOpponent();
        draws.sort(new Comparator<DrawDataDTO>() {
            @Override
            public int compare(DrawDataDTO d1, DrawDataDTO d2) {
                return game.getState().distanceToEnemy(enemy,d1.getToPos())-game.getState().distanceToEnemy(enemy,d2.getToPos());
            }
        });
    }


    private double evaluateDraw(Game game, DrawDataDTO drawData, ArrayList<Piece> graveYardBefore, ArrayList<Piece> graveYardAfter){
        double sum = NOTHING;

        int moveValue = game.getState().distanceToEnemy(getPlayer().getOpponent(),drawData.getFromPos())-game.getState().distanceToEnemy(getPlayer().getOpponent(),drawData.getToPos());
        if(moveValue>0){
            sum+=MOVE_TO_ENEMY;
        } else if(moveValue<0){
            sum-=MOVE_TO_ENEMY;
        }

        for (Piece removedPiece:BotUtil.diff(graveYardAfter,graveYardBefore)) {
            sum += evaluateRemovedPiece(removedPiece);
        }

        return sum;
    }







    private double evaluateRemovedPiece(Piece p){
        if(p.isKing()){
            if(getPlayer().equals(p.getOwner())) {
                return -KING;
            }
            return  KING;
        } else {
            if(getPlayer().equals(p.getOwner())) {
                return -LOSE;
            }
            return  BEAT;
        }
    }





    private double weightOf(double value,int depth){
        return value/(MAX_DEPTH-depth+1)/(MAX_DEPTH-depth+1);
    }
}