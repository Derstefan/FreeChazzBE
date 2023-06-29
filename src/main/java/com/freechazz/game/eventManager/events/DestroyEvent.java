package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.Pos;
import com.freechazz.game.state.GameState;

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
    public void perform(GameState state) {

    }

    @Override
    public void undo(GameState state) {

    }
}
