package com.freechess.game.player.bots;

import com.freechess.game.Game;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;
import com.freechess.server.DTO.DrawData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class BetterBot extends Bot {

    private static final int NOTHING = 0;
    private static final int BEAT = 1;
    private static final int KING = 9000;

    private int MAX_DEPTH = 1;

    public BetterBot(EPlayer player) {
        super(player);
    }

    public BetterBot(EPlayer player,int maxDepth) {
        super(player);
        this.MAX_DEPTH = maxDepth;
    }

    @Override
    public void doDraw(Game game){

        HashMap<DrawData,Double> evaluatedDraws = evaluateDraws(game);
        DrawData bestDraw = randomDraw(getBestDraws(evaluatedDraws));

        game.play(bestDraw.getFromPos(),bestDraw.getToPos());
    }


    private HashMap<DrawData,Double> evaluateDraws(Game game){
        HashMap<DrawData,Double> draws = new HashMap<>();
        ArrayList<Piece> pieces = game.getBoard().getAllPiecesFrom(this.getPlayer());


        for(Piece p:pieces) {

            for (Position pos : p.getPossibleMoves()) {
                DrawData draw = new DrawData(p.getPosition(), pos);
                double value = evaluateDraw(game,draw, MAX_DEPTH);
                //log.info(pos.toString() + " ->" +value);
                if(value<=-Double.MAX_VALUE/4) { //enemy can beat so do not use
                  //  log.info("not used");
                    continue;
                }
                draws.put(draw,value);
                if(value==Double.MAX_VALUE)return draws; //here you can beat him

            }
        }
        return draws;
    }

    private ArrayList<DrawData> getDraws(Game game, EPlayer player){
        ArrayList<DrawData> draws = new ArrayList<>();
        ArrayList<Piece> pieces = game.getBoard().getAllPiecesFrom(player);

        for(Piece p:pieces) {
            for (Position pos : p.getPossibleMoves()) {
                DrawData draw = new DrawData(p.getPosition(), pos);
                draws.add(draw);
            }
        }
        return draws;
    }


    private double evaluateDraw(Game game,DrawData drawData,int depth){
        Game gameCopy = game.copy();
 //       ArrayList<Piece> graveyardBefore = game.getBoard().getGraveyard();//TODO: game not gameCopy ?
        ArrayList<Piece> piecesBefore = game.getBoard().getAllPieces();

        gameCopy.play(drawData.getFromPos(),drawData.getToPos());
//        ArrayList<Piece> graveyardAfter = gameCopy.getBoard().getGraveyard();
        ArrayList<Piece> piecesAfter = gameCopy.getBoard().getAllPieces();

        double sum = weightOf(evaluateDraw(piecesBefore,piecesAfter),depth);//TODO: double weightof ??
        if(depth==MAX_DEPTH && sum >=weightOf(KING,MAX_DEPTH)/2){// if can hit king
            return Double.MAX_VALUE;
        }
        if(depth==MAX_DEPTH-1 && sum <=-weightOf(KING,MAX_DEPTH-1)/2){
            return -Double.MAX_VALUE/2;
        }

        if(depth==0){
            return sum;
        }
        ArrayList<DrawData> opponentDraws = getDraws(gameCopy,gameCopy.getPlayersTurn());
        for(DrawData d: opponentDraws){

            sum+=evaluateDraw(gameCopy,d,depth-1);
        }
        return sum;
    }

    private double evaluateDraw(ArrayList<Piece> piecesBefore,ArrayList<Piece> piecesAfter){
        double sum = NOTHING;

        //TODO:ArrayList<Piece> newPieces = new ArrayList<>();
        for(Piece p:piecesBefore){
            boolean stillonBoard = false;
            for(Piece pAfter:piecesAfter){
                if(p.getId()==pAfter.getId()){
                    stillonBoard=true;
                }
            }
            if(stillonBoard){
                sum+=evaluatePiece(p);
            }
        }
        return sum;
    }

    private double evaluatePiece(Piece p){
        double value;
        if(p.isKing()){
            value =  -KING;
        } else {
            value = -BEAT;
        }
        if(getPlayer().equals(p.getOwner())){
            value = -value;
        }
        return  value;
    }


    private ArrayList<DrawData> getBestDraws(HashMap<DrawData,Double> draws){
        double value = -Double.MAX_VALUE;
        for(DrawData d:draws.keySet()){
            if(draws.get(d)>value){
                value = draws.get(d);
            }
        }

        ArrayList<DrawData> bestDraws = new ArrayList<>();
        for(DrawData d:draws.keySet()){
            if(draws.get(d)==value){
                bestDraws.add(d);
            }
        }
        return bestDraws;
    }

    private DrawData randomDraw(ArrayList<DrawData> draws){
        int index = (int) (Math.random()*draws.size());
        return draws.get(index);
    }

    private double weightOf(double value,int depth){
        return value/(MAX_DEPTH-depth+1);
    }
}