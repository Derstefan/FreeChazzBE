package com.freechazz.game.state;

import com.freechazz.game.pieces.Piece;

public class Field {

    private Piece piece;


    public Field(Piece piece) {
        this.piece = piece;
    }

    public Field() {
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
