package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.pieces.PieceTypeId;

import java.util.UUID;


public class EventDTO {

    private EventType type;

    public EventDTO(Event event) {
        this.type = event.getType();
    }

    public EventDTO(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
