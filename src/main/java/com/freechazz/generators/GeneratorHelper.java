package com.freechazz.generators;

import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.core.Pos;

import java.util.*;

public class GeneratorHelper {

    // array as list... input ?
    public static int dice(List<Double> wsks, Random rand) {
        double wsk = rand.nextDouble();
        double sum = 0;
        for(double d:wsks){
            sum+=d;
        }
        if(sum<1.0){
            throw new ArithmeticException(""+sum);
        }
        // System.out.println(wsk);
        for (int i = 0; i < wsks.size(); i++) {

            if (wsk <= wsks.get(i)) {
                return i;
            }
            wsk -= wsks.get(i);
        }
        return -1;
    }

    public static double sum(List<Double> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum;
    }

    public static void showMoves(char[][] moves) {
        for (int i = 0; i < moves.length; i++) {
            for (int j = 0; j < moves.length; j++) {
                System.out.print(" " + moves[j][moves.length - 1 - i] + " ");
            }
            System.out.println();
        }
    }


    public static ArrayList<Pos> getRandomPosOfArea(int minX, int maxX, int minY, int maxY, int number, Random rand) {
        ArrayList<Pos> allPositions = new ArrayList<>();
        ArrayList<Pos> positions = new ArrayList<>();

        for (int i = minX; i < maxX + 1; i++) {
            for (int j = minY; j < maxY + 1; j++) {
                allPositions.add(new Pos(i, j));
            }
        }
        if (allPositions.size() < number) {
            number = allPositions.size();
        }

        for (int i = 0; i < number; i++) {
            int k = (int) Math.round(rand.nextDouble() * (allPositions.size() - 1) - 0.5);
            positions.add(allPositions.get(k));
            allPositions.remove(k);
        }
        return positions;
    }

    public static PieceType getRandomEntryOf(ArrayList<PieceType> pieceTypes, Random rand) {

        int index = rand.nextInt(pieceTypes.size());
        Iterator<PieceType> iter = pieceTypes.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }

        return iter.next();
    }


    public static long seedOfADay(){
        Date date = new Date();
        return (long)(date.getTime()/1000)-date.getHours()*3600*60-date.getMinutes()*60 -date.getSeconds();
    }

}
