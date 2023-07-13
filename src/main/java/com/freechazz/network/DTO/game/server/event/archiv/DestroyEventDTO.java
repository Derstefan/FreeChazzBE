package com.freechazz.network.DTO.game.server.event.archiv;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.DestroyEvent;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.server.event.EventDTO;

import java.util.UUID;

public class DestroyEventDTO extends EventDTO {

    private UUID pieceId;
    private Pos pos;

    public DestroyEventDTO(DestroyEvent event){
        super(event);
        this.pos = event.getPos();
        this.pieceId = event.getPiece().getPieceId();
    }

    public UUID getPieceId(){
        return pieceId;
    }
    public Pos getPos(){
        return pos;
    }
}
