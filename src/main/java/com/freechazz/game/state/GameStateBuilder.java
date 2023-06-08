package com.freechazz.game.state;


import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;

import java.util.HashMap;

public class GameStateBuilder {

    private GameState state;
    private int width;
    private int height;
    //for validatation
    private boolean king1Set = false;
    private boolean king2Set = false;
    private int idCounter = 0;



    public GameStateBuilder(int width, int height){
        //TODO:validate width and height
        this.width = width;
        this.height = height;
        this.state = GameState.getInstance(width,height);
    }

    public GameStateBuilder putPiece(Piece piece, Pos pos){
        piece.setId(idCounter);
        idCounter++;
        state.putPiece(piece,pos);
        return this;
    }

    public GameStateBuilder putPieceMirrored(Piece piece, Pos pos){
        putPiece(piece, mirrorPos(pos));
        return this;
    }

    private Pos mirrorPos(Pos pos){
        return new Pos(width-1-pos.getX(),height-1-pos.getY());
    }

    public void putPieces(HashMap<Pos, Piece> pieces){
        for(Pos pos:pieces.keySet()){
            putPiece(pieces.get(pos),pos);
        }
    }

    public GameStateBuilder putKing(EPlayer player, PieceType kingType, Pos pos){
        Piece king = new Piece(player, kingType);
        if(EPlayer.P1.equals(player)){
            putPieceMirrored(king, pos);
            state.setKing1(king);
            king1Set = true;
            return this;
        }
        putPiece(king, pos);
        state.setKing2(king);
        king2Set = true;
        return this;
    }

    // with null return or with exception?
    public GameState build() {
        if(validate()){
            state.computePossibleMoves();
            return state;
        }
        return null;
    }

    private boolean validate() {
        if(!king1Set || !king2Set){
            return false;
        }
        return true;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
