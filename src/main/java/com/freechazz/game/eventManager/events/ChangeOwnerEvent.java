package com.freechazz.game.eventManager.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameState;

import java.util.UUID;

public class ChangeOwnerEvent extends Event {

    private Piece piece;

    public ChangeOwnerEvent(Piece piece){
        super(EventType.CHANGE_OWNER);
        this.piece = piece;
    }

    public Piece getPiece(){
        return piece;
    }

    @Override
    public void perform(GameState state) {

    }

    @Override
    public void undo(GameState state) {

    }
}
