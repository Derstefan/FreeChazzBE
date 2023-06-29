package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.state.GameState;

public class SwapEvent extends Event {

    private Pos fromPos;
    private Pos toPos;

    public SwapEvent(Pos fromPos, Pos toPos){
        super(EventType.SWAP);
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public Pos getFromPos(){
        return fromPos;
    }

    public Pos getToPos(){
        return toPos;
    }

    @Override
    public void perform(GameState state) {

    }

    @Override
    public void undo(GameState state) {

    }
}
