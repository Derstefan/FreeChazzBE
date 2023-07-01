package com.freechazz.game.eventManager;

import com.freechazz.game.state.GameOperator;

public abstract class Event {

    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public abstract void perform(GameOperator state);
    public abstract void undo(GameOperator state);
}
