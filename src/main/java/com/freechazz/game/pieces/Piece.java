package com.freechazz.game.pieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.core.Pos;
import com.freechazz.game.core.EPlayer;

import java.util.ArrayList;

public class Piece {

    private int id; //for piece


    private PieceType pieceType;

    private Pos position;

    private boolean king;

    private EPlayer owner;


    //- temporary saved data, not computet by getting!----------------
    private ArrayList<Pos> possibleMoves = new ArrayList<>();
    private int distanceToEnemy;
    private int distanceToEnemyKing;
    private int distanceToOwnKing;
    //-----------------------------------------------------------


    public Piece(EPlayer owner, PieceType pieceType){
        this.owner = owner;
        this.pieceType = pieceType;
        this.king = false;
    }

    public void addPossibleMove(Pos move){
        possibleMoves.add(move);
    }

    public void clearPossibleMoves(){
        possibleMoves.clear();;
    }

    public EPlayer getOwner(){
        return owner;
    }

    public void setOwner(EPlayer owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLvl(){return pieceType.getLvl();}

    public String getSymbol() {
        return pieceType.getSymbol();
    }

    public String getSeed(){
        return  String.valueOf(pieceType.getSeed());
    }

    public String getSerial() {
        return pieceType.getSerial();
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
            this.king = king;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    @JsonIgnore
    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public ArrayList<Pos> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(ArrayList<Pos> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public int getDistanceToEnemy() {
        return distanceToEnemy;
    }

    public void setDistanceToEnemy(int distanceToEnemy) {
        this.distanceToEnemy = distanceToEnemy;
    }

    public boolean isPossibleMove(Pos posTo ){
        for (int i = 0; i < possibleMoves.size(); i++) {
            if(possibleMoves.get(i).getX()==posTo.getX() && possibleMoves.get(i).getY()==posTo.getY()){
                return true;
            }
        }
        return false;
    }


    public String printPossibleMoves(){
        String s = "";
        for (int i = 0; i < possibleMoves.size(); i++) {
            s +="("+ possibleMoves.get(i).getX() + " " + possibleMoves.get(i).getY() + ") ";
        }
        return s;
    }

    public String printActions(){
        return pieceType.getActionMap().print();
    }






private Piece(Piece anotherPiece){
    owner = anotherPiece.getOwner();
    pieceType = anotherPiece.getPieceType();
    king = anotherPiece.isKing();
    position = anotherPiece.getPosition().copy();
    id = anotherPiece.getId();

    possibleMoves = new ArrayList<Pos>();
    for(Pos pos: anotherPiece.getPossibleMoves()){
        possibleMoves.add(pos.copy());
    }
}
    public Piece copy(){
        return new Piece(this);
    }


}
