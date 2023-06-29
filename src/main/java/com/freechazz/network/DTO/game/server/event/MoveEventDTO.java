package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.pieces.Piece;

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
