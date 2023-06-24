package com.freechazz.bots.impl;

import com.freechazz.bots.Bot;
import com.freechazz.game.Game;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.client.DrawDataDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


/**
 * vs: 2
 * split own and enemy draw and messure it differently
 */
@Slf4j
public class BetterBotClean extends Bot {

    private static final int NOTHING = 0;
    private static final int BEAT = 30;
    private static final int LOSE = 15;
    private static final int KING = 1885;
    private static final int MOVE_TO_ENEMY = 10;
    private int MAX_DEPTH = 1;
    private static final int MIN_DRAWS_CHECKING =7;
    private static final int MIN_DRAWS_CHECKING_HISTORY =4;
    private static final int MIN_DRAWS_CHECKING_DEEP = 5;
    private static final int MIN_DRAWS_CHECKING_HISTORY_DEEP = 4;

    private static final double TOLERANCE = 0.2;




    //Statistics


    public BetterBotClean(EPlayer player, int maxDepth, long seed) {
        super(player,seed);
        this.MAX_DEPTH = maxDepth;
    }

    public BetterBotClean(EPlayer player, int maxDepth) {
        super(player);
        this.MAX_DEPTH = maxDepth;
    }



    @Override
    public void doDraw(Game game){

        HashMap<DrawDataDTO,Double> evaluatedDraws = evaluateDraws(game);
        if(evaluatedDraws.isEmpty())
        {
            game.surrender();
            return;
        }
        DrawDataDTO bestDraw = randomDraw(getBestDrawsWithTolerance(evaluatedDraws,TOLERANCE));
        game.play( bestDraw.getFromPos(),bestDraw.getToPos());

    }


    private HashMap<DrawDataDTO,Double> evaluateDraws(Game game){
        Game gameCopy = game.copy();
        HashMap<DrawDataDTO,Double> drawValues = new HashMap<>();
        ArrayList<DrawDataDTO> draws = getDraws(game,game.getPlayersTurn());
        if(draws.isEmpty()){
            return drawValues;
        }

        double bestValue = -Double.MAX_VALUE/4;
        int drawCounter = 0;
        int lastGoodDraw = 0;
        for (DrawDataDTO draw : draws) {

            double value = emulateOwnDraw(gameCopy,draw, MAX_DEPTH);
            if(value>bestValue){
                bestValue=value;
                lastGoodDraw = drawCounter;
            }
            if(value<=-Double.MAX_VALUE/4)continue;//enemy can beat so do not use
            drawValues.put(draw,value);
            if(value==Double.MAX_VALUE)return drawValues; //here you can beat him
            if(drawCounter>MIN_DRAWS_CHECKING && drawCounter-lastGoodDraw>MIN_DRAWS_CHECKING_HISTORY){
                break;
            }
            drawCounter++;
        }

        if(drawValues.isEmpty()){
            if(draws.isEmpty()){
                return drawValues;
            }
            drawValues.put(randomDraw(draws),0.0);
            return drawValues;
        }
        return drawValues;
    }







    private double emulateOwnDraw(Game game, DrawDataDTO drawData, int depth){
        ArrayList<Piece> graveYardBefore = new ArrayList<>(game.getState().getGraveyard());

        game.play(drawData.getFromPos(),drawData.getToPos());
        newDraw();

        ArrayList<Piece> graveYardAfter = game.getState().getGraveyard();


        double sum = weightOf(evaluateDraw(game,drawData,graveYardBefore,graveYardAfter),depth);
        if(depth==MAX_DEPTH && sum >=weightOf(KING,MAX_DEPTH)/2){// if can hit king
            game.undo();
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
        double bestValue = -1000000;
        int drawCounter = 1;
        int lastGoodDraw = 0;
        double tempSum = 0;
        for(DrawDataDTO d: opponentDraws){

            double value = emulateEnemyDraw(game,d,depth-1);
            tempSum+=value;
            if(value>bestValue){
                bestValue=value;
                lastGoodDraw = drawCounter;
            }
            drawCounter++;
            if(drawCounter>MIN_DRAWS_CHECKING_DEEP && drawCounter-lastGoodDraw>MIN_DRAWS_CHECKING_HISTORY_DEEP){
                break;
            }


        }
        sum+=tempSum/drawCounter;;
        game.undo();
        return sum;
    }



    private double emulateEnemyDraw(Game game, DrawDataDTO drawData, int depth){
        ArrayList<Piece> graveYardBefore = new ArrayList<>(game.getState().getGraveyard());
        game.play(drawData.getFromPos(),drawData.getToPos());
        newDraw();
        ArrayList<Piece> graveYardAfter = game.getState().getGraveyard();

        double sum = weightOf(evaluateDraw(game,drawData,graveYardBefore,graveYardAfter),depth);

        if(depth==MAX_DEPTH && sum >=weightOf(KING,MAX_DEPTH)/2){// if can hit king
            game.undo();
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

        double bestValue = 1000000;
        int drawCounter = 1;
        int lastGoodDraw = 0;
        double tempSum = 0;
        for(DrawDataDTO d: opponentDraws){
            double value = emulateOwnDraw(game,d,depth-1);
            tempSum+=value;
            if(value<bestValue){
                bestValue=value;
                lastGoodDraw = drawCounter;
            }
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
        game.computePossibleMoves();
        ArrayList<DrawDataDTO> draws = new ArrayList<>();
        ArrayList<Piece> pieces = game.getState().getAllPiecesFrom(player);

        for(Piece p:pieces) {
            for (Pos pos : p.getPossibleMoves()) {
                DrawDataDTO draw = new DrawDataDTO(p.getPos(), pos);
                draws.add(draw);
            }
        }
        sortDrawData(draws,game);
        return draws;
    }



    private void sortDrawData(ArrayList<DrawDataDTO> draws, Game game){

        if(draws.isEmpty())return;

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

        for (Piece removedPiece:diff(graveYardAfter,graveYardBefore)) {
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




    public String getDephSpace(int deph){
        String space = "";
        for(int i=0;i<MAX_DEPTH-deph;i++){
            space+="     ";
        }
        return space;
    }
}