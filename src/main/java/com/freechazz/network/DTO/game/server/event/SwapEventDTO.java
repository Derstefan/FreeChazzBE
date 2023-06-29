package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.eventManager.events.SwapEvent;

public class SwapEventDTO extends EventDTO {

    private Pos fromPos;
    private Pos toPos;

    public SwapEventDTO(SwapEvent event){
        super(event);
        this.fromPos = event.getFromPos();
        this.toPos = event.getToPos();
    }

    public Pos getFromPos(){
        return fromPos;
    }

    public Pos getToPos(){
        return toPos;
    }
}
