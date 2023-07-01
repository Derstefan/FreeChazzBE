package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameOperator;

public class DestroyEvent extends Event {

    private Piece piece;
    private Pos pos;

    public DestroyEvent(Piece piece, Pos pos){
        super(EventType.DESTROY);
        this.piece = piece;
        this.pos = pos;
    }

    public Piece getPiece(){
        return piece;
    }

    public Pos getPos(){
        return pos;
    }

    @Override
    public void perform(GameOperator state) {
        Piece p = state.pieceAt(pos);
        if(p.equals(state.getKing1())){
            state.setWinner(state.getKing2().getOwner());
        } else if(p.equals(state.getKing2())){
            state.setWinner(state.getKing1().getOwner());
        }
        state.removePiece(pos);
        state.getGraveyard().add(p);
    }

    @Override
    public void undo(GameOperator state) {
        //undo Operation
        state.getGraveyard().remove(piece);
        state.putPiece(piece, pos);
    }
}
