package com.freechazz.server.DTO.game.server;

import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;

import java.util.List;

public class PieceDTO {
    private String id;
    private PieceTypeDTO pieceType;
    private Pos pos;
    private String symbol;
    private List<Pos> possibleMoves;
    private EPlayer owner;


    public PieceDTO(Piece p) {
        //TODO: id!
        this.pieceType = new PieceTypeDTO(p.getPieceType());
        this.pos = p.getPos();
        this.symbol = p.getSymbol();
        this.possibleMoves = p.getPossibleMoves();
        this.owner = p.getOwner();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PieceTypeDTO getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceTypeDTO pieceType) {
        this.pieceType = pieceType;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Pos> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Pos> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public EPlayer getOwner() {
        return owner;
    }

    public void setOwner(EPlayer owner) {
        this.owner = owner;
    }
}
