package com.freechazz.game.formation;

import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.player.User;
import com.freechazz.game.core.Pos;
import com.freechazz.generators.game.ESize;

import java.util.HashMap;

public class Formation {


    private ESize size;
    private HashMap<Pos, PieceType> pieceTypes = new HashMap<>();
    private Pos kingPos;
    private User owner;


    public void put(PieceType pieceType, Pos pos){
        pieceTypes.put(pos, pieceType);
    }

    public ESize getSize() {
        return size;
    }

    public void setSize(ESize size) {
        this.size = size;
    }



    public Pos getKingPos() {
        return kingPos;
    }

    public void setKingPos(Pos kingPos) {
        this.kingPos = kingPos;
    }


    public HashMap<Pos, PieceType> getPieceTypes() {
        return pieceTypes;
    }

    public void setPieces(HashMap<Pos, PieceType> pieceTypes) {
        this.pieceTypes = pieceTypes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PieceType getKing() {
        return pieceTypes.get(kingPos);
    }

    protected Formation() {
    }

}
