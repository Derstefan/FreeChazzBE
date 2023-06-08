package com.freechazz.game.formation;

import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.player.User;
import com.freechazz.game.core.Pos;
import com.freechazz.game.core.ESize;

public class FormationBuilder {

    private Formation formation;

    private boolean kingSet = false;
    private int idCounter = 0;

    public FormationBuilder(ESize size, User owner) {

        formation = new Formation();
        formation.setSize(size);
        formation.setOwner(owner);
    }

    public FormationBuilder putKing(PieceType pieceType, Pos pos){
        formation.setKingPos(pos);
        formation.put(pieceType,pos);
        kingSet = true;
        return this;
    }

    public FormationBuilder putPiece(PieceType pieceType, Pos pos){
        formation.put(pieceType,pos);
        return this;
    }

    public Formation build() {
        if(!kingSet){
            throw new IllegalStateException("King not set");
        }
        return formation;
    }

}
