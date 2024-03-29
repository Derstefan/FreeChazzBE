package com.freechazz.network.DTO.game.server;

import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.MoveSet;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceTypeId;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class PieceDTO {

    private UUID pieceId;


    private PieceTypeId pieceTypeId;

    private Pos pos;
    private String symbol;
    private MoveSet moveSet;
    private EPlayer owner;


    public PieceDTO(Piece p) {
        this.pieceId = p.getPieceId();
        this.pieceTypeId = p.getPieceType().getPieceTypeId();
        this.pos = p.getPos();
        this.symbol = p.getSymbol();
        this.moveSet = p.getMoveSet();
        this.owner = p.getOwner();
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

    public MoveSet getMoveSet() {
        return moveSet;
    }

    public EPlayer getOwner() {
        return owner;
    }

    public void setOwner(EPlayer owner) {
        this.owner = owner;
    }


    public PieceTypeId getPieceTypeId() {
        return pieceTypeId;
    }

    public UUID getPieceId() {
        return pieceId;
    }
}
