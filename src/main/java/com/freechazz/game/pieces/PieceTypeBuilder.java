package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.core.Pos;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class PieceTypeBuilder {

    private PieceType pieceType;

    public PieceTypeBuilder(int lvl, long seed, String generatorVersion){
        this.pieceType = PieceType.getInstance(lvl,seed,generatorVersion);
    }

    public PieceTypeBuilder symbol(final String symbol){
        pieceType.setSymbol(symbol);
        return this;
    }

    public PieceTypeBuilder action(final Pos pos, final Action action){
        pieceType.getActionMap().put(pos,action);
        return this;
    }

    public PieceTypeBuilder action(final int x,final int y,final Action action){
        action(new Pos(x,y),action);
        return this;
    }

    public PieceTypeBuilder actions(final ActionMap actionMap){
        pieceType.setActionMap(actionMap);

        return this;
    }

    public PieceType build(){
        validate();
        pieceType.getPieceTypeId().setPieceTypeId(pieceType.getActionMap().generateUUID());
        return pieceType;
    }

    private void validate(){
        //blabla
        //exception?
    }

}
