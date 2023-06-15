package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.core.Pos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieceTypeBuilder {

    private PieceType pieceType;

    public PieceTypeBuilder(int lvl){
        this.pieceType = PieceType.getInstance();
        this.pieceType.setLvl(lvl);
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
        return pieceType;
    }

    private void validate(){
        //blabla
        //exception?
    }

}
