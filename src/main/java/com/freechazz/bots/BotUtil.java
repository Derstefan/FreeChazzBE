package com.freechazz.bots;

import com.freechazz.game.pieces.Piece;
import com.freechazz.server.DTO.DrawData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class BotUtil {

    // in list1 and not list2
    public static ArrayList<Piece> diff(ArrayList<Piece> list1, ArrayList<Piece> list2) {
        ArrayList<Piece> diffList = new ArrayList<>();
        for (Piece element : list1) {
            if (!list2.contains(element)) {
                diffList.add(element);
            }
        }
        return diffList;
    }


    public static ArrayList<DrawData> getBestDraws(HashMap<DrawData,Double> draws){
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

    public static ArrayList<DrawData> getBestDrawsWithTolerance(HashMap<DrawData,Double> draws, double tolerance){
        if(tolerance>1 || tolerance<0){
            throw new IllegalArgumentException("Threshold must be between 0 and 1");
        }
        double bestvalue = -Double.MAX_VALUE;
        for(DrawData d:draws.keySet()){
            if(draws.get(d)>bestvalue){
                bestvalue = draws.get(d);
            }
        }
        //log.info("bestvalue: {} + tolerance*bestvalue: {}",bestvalue,bestvalue-(Math.abs(bestvalue*tolerance)) );

        ArrayList<DrawData> bestDraws = new ArrayList<>();
        for(DrawData d:draws.keySet()){
            if(draws.get(d)>=bestvalue-(Math.abs(bestvalue*tolerance))){
                bestDraws.add(d);
            }
        }
        return bestDraws;
    }






}
