package com.freechazz.game.eventManager.events;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.core.Pos;

public class SwapEvent extends Event {

    private Pos fromPos;
    private Pos toPos;

    public SwapEvent(Pos fromPos, Pos toPos){
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public Pos getFromPos(){
        return fromPos;
    }

    public Pos getToPos(){
        return toPos;
    }
}
