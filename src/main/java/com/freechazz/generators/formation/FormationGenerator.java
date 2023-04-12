package com.freechazz.generators.formation;

import com.freechazz.game.formation.Formation;
import com.freechazz.game.formation.FormationBuilder;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.player.User;
import com.freechazz.game.core.Pos;
import com.freechazz.generators.GeneratorHelper;
import com.freechazz.generators.game.ESize;

import java.util.ArrayList;
import java.util.Random;

/**
 * generates a random formation
 */
public class FormationGenerator {



    private static final int KING_LVL = 1;
    private static Pos KING_POS(ESize eSize){
        return new Pos(eSize.getWidth() / 2, 0);
    }


    private ESize eSize;
    private Random rand;
    private PieceTypePool pieceTypePool;

    private FormationBuilder formationBuilder;

    public FormationGenerator(long seed,ESize eSize, User user){
        rand = new Random(seed);
        this.eSize = eSize;
        pieceTypePool = new PiecePoolGenerator(rand.nextLong()).generate();
        formationBuilder = new FormationBuilder(eSize,user);
    }


    public Formation generate(){

        PieceType kingType = pieceTypePool.getRandomPieceType(KING_LVL);
        Pos kingPos = KING_POS(eSize);
        formationBuilder.putKing(kingType,kingPos);
        putPieces();


        return formationBuilder.build();
    }

    private void putPieces(){

        if(ESize.big.equals(eSize)){
            addPiecesToBoard(pieceTypePool.get(1),randomPositions(27,10));
            addPiecesToBoard(pieceTypePool.get(2),randomPositions(22,9));
            addPiecesToBoard(pieceTypePool.get(3),randomPositions(14,7));
            addPiecesToBoard(pieceTypePool.get(4),randomPositions(8,5));
            addPiecesToBoard(pieceTypePool.get(5),randomPositions(4,3));

        } else if(ESize.medium.equals(eSize)) {
            addPiecesToBoard(pieceTypePool.get(1),randomPositions(14,6));
            addPiecesToBoard(pieceTypePool.get(2),randomPositions(17,5));
            addPiecesToBoard(pieceTypePool.get(3),randomPositions(8,4));
            addPiecesToBoard(pieceTypePool.get(4),randomPositions(4,2));
        } else  if(ESize.small.equals(eSize)){
            addPiecesToBoard(pieceTypePool.get(1),randomPositions(8,3));
            addPiecesToBoard(pieceTypePool.get(2),randomPositions(10,2));
            addPiecesToBoard(pieceTypePool.get(3),randomPositions(4,1));
        } else{
            addPiecesToBoard(pieceTypePool.get(1),randomPositions(4,3));
            addPiecesToBoard(pieceTypePool.get(2),randomPositions(4,2));
            addPiecesToBoard(pieceTypePool.get(3),randomPositions(5,1));
        }
    }



    private ArrayList<Pos> randomPositions(int number, int line) {
        if ((line>=eSize.getHeight()/2) || number <= 0) {
            return new ArrayList<>();
        }
        return GeneratorHelper.getRandomPosOfArea(
                0,
                eSize.getWidth() - 1,
                1/2*eSize.getHeight() + line,
                1/2*eSize.getHeight() + 1 + line, number,rand);
    }


    private void addPiecesToBoard(ArrayList<PieceType> pieceTypes, ArrayList<Pos> positions ) {

        for (int i = 0; i < positions.size(); i++) {
            PieceType eType = GeneratorHelper.getRandomEntryOf(pieceTypes, rand);
            formationBuilder.putPiece(eType, positions.get(i));
        }
    }



}
