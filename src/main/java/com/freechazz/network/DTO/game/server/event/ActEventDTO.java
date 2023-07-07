package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.ActEvent;
import com.freechazz.game.state.GameOperator;

/**
 *  this is an event, that tracks the act, that was performed
 *  this is for logging and for animation in frontend (e.g. ranged attack, in FE there should be a projectile flying from one unit to another instead of only destroying the unit)
 */
public class ActEventDTO extends EventDTO {
    private String actName;
    private Pos fromPos;
    private Pos toPos;

    public ActEventDTO(ActEvent actEvent){
        super(actEvent);
        this.actName = actEvent.getActName();
        this.fromPos = actEvent.getFromPos();
        this.toPos = actEvent.getToPos();
    }

    public String getActName() {
        return actName;
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public Pos getToPos() {
        return toPos;
    }
}
