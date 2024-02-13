package com.freechazz.game.eventManager;

import com.freechazz.game.eventManager.events.JsonSerializeField;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;

import java.util.ArrayList;

public abstract class Event {

    @JsonSerializeField
    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public abstract void perform(GameOperator state);

    public abstract void undo(GameOperator state);


    /**
     * reconnect the poieces of the event to the state pieces
     * after serialisation
     *
     * @param pieces
     */
    public abstract void reconnect(ArrayList<Piece> pieces);
}
