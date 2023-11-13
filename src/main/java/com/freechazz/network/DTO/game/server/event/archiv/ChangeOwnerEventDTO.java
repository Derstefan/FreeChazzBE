package com.freechazz.network.DTO.game.server.event.archiv;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.ChangeOwnerEvent;
import com.freechazz.game.eventManager.events.ChangeTypeEvent;
import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.server.event.EventDTO;

import java.util.UUID;

public class ChangeOwnerEventDTO extends EventDTO {


    private UUID pieceId;

    public ChangeOwnerEventDTO(ChangeOwnerEvent event){
        super(event);
        this.pieceId = event.getPiece().getPieceId();
    }

    public UUID getPieceId(){
        return pieceId;
    }


}
