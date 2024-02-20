package com.freechazz.game.pieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.core.ActionPos;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.generators.piece.PieceTypeGenerator;
import com.freechazz.network.DTO.game.server.PieceDTO;

import java.util.UUID;

public class Piece {

    private int id; //remove it for piece

    private UUID pieceId;

    private PieceType pieceType;

    private Pos position;

    private boolean king;

    private EPlayer owner;


    //- temporary saved data, not computet by getting!----------------
    private MoveSet moveSet;
    private int distanceToEnemy;
    private int distanceToEnemyKing;
    private int distanceToOwnKing;
    //-----------------------------------------------------------


    public Piece(EPlayer owner, PieceType pieceType) {
        this.pieceId = UUID.randomUUID();//TODO: load the pieceId from the database
        this.owner = owner;
        this.pieceType = pieceType;
        this.moveSet = new MoveSet();
        this.king = false;
    }


    public EPlayer getOwner() {
        return owner;
    }

    public void setOwner(EPlayer owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public UUID getPieceId() {
        return pieceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLvl() {
        return pieceType.getPieceTypeId().getLvl();
    }

    public String getSymbol() {
        return pieceType.getSymbol();
    }

    public String getSeed() {
        return String.valueOf(pieceType.getPieceTypeId().getSeed());
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public Pos getPos() {
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

    public MoveSet getMoveSet() {
        return moveSet;
    }

    public void setMoveSet(MoveSet moveSet) {
        this.moveSet = moveSet;
    }

    public int getDistanceToEnemy() {
        return distanceToEnemy;
    }

    public void setDistanceToEnemy(int distanceToEnemy) {
        this.distanceToEnemy = distanceToEnemy;
    }

    public boolean isPossibleMove(Pos posTo) {

        for (Pos pos : moveSet.getPm()) {
            if (pos.equals(posTo)) {
                return true;
            }
        }
        return false;
    }


    public String printPossibleMoves() {
        String s = "";
        for (Pos pos : moveSet.getPm()) {
            s += "(" + pos.getX() + " " + pos.getY() + ") ";
        }
        return s;
    }

    public String printActions() {
        return pieceType.getActionMap().print();
    }


    // a full tostring method
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", pieceId=" + pieceId +
                ", pieceType=" + pieceType.toString() +
                ", position=" + position.toString() +
                ", king=" + king +
                ", owner=" + owner +
                ", moveSet=" + moveSet.toString() +
                ", distanceToEnemy=" + distanceToEnemy +
                ", distanceToEnemyKing=" + distanceToEnemyKing +
                ", distanceToOwnKing=" + distanceToOwnKing +
                '}';
    }


    private Piece(Piece anotherPiece) {
        owner = anotherPiece.getOwner();
        pieceType = anotherPiece.getPieceType();
        king = anotherPiece.isKing();
        position = anotherPiece.getPos().copy();
        id = anotherPiece.getId();

        moveSet = new MoveSet();
        for (ActionPos pos : anotherPiece.getMoveSet().getPm()) {
            moveSet.add(pos.copy());
        }
    }

    public Piece(PieceDTO pieceDTO) {

        this.pieceId = pieceDTO.getPieceId();
        this.owner = pieceDTO.getOwner();
        this.pieceType = PieceTypeGenerator.generate(pieceDTO.getPieceTypeId());
        this.moveSet = pieceDTO.getMoveSet();
        this.king = pieceDTO.isKing();
        this.position = pieceDTO.getPos();
    }

    public Piece copy() {
        return new Piece(this);
    }


}
