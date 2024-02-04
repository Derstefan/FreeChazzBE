package com.freechazz.game.eventManager.events;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.state.GameOperator;

/**
 * this is an event, that tracks the act, that was performed
 * this is for logging and for animation in frontend (e.g. ranged attack, in FE there should be a projectile flying from one unit to another instead of only destroying the unit)
 */
public class ActEvent extends Event {

    @JsonSerializeField
    private String actName;

    @JsonSerializeField

    private Pos fromPos;

    @JsonSerializeField

    private Pos toPos;

    public ActEvent(String actName, Pos fromPos, Pos toPos) {
        super(EventType.ACT);
        this.actName = actName;
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    @Override
    public void perform(GameOperator state) {
        //do nothing
    }

    @Override
    public void undo(GameOperator state) {
        //do nothing
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
