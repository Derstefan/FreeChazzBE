package com.freechazz.network.DTO.game.server.event.archiv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.server.event.EventDTO;

import java.io.Serializable;
import java.util.UUID;

public class MoveEventDTO extends EventDTO {

    private Pos fromPos;
    private UUID pieceId;
    private Pos toPos;

    public MoveEventDTO(MoveEvent event){
        super(event);
        this.fromPos = event.getFromPos();
        this.pieceId = event.getPiece().getPieceId();
        this.toPos = event.getToPos();
    }

    public Pos getFromPos(){
        return fromPos;
    }

    public UUID getPieceId(){
        return pieceId;
    }
    public Pos getToPos(){
        return toPos;
    }
}
