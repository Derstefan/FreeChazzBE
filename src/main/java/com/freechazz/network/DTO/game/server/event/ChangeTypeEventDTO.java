package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.EventType;
import com.freechazz.game.eventManager.events.ChangeTypeEvent;
import com.freechazz.game.eventManager.events.MoveEvent;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.pieces.PieceTypeId;

import java.util.UUID;

public class ChangeTypeEventDTO extends EventDTO {

    private UUID pieceId;
    private PieceTypeId newPieceTypeId;
    private PieceTypeId oldPieceTypeId;

    public ChangeTypeEventDTO(ChangeTypeEvent event){
        super(event);
        this.pieceId = event.getPiece().getPieceId();
        this.newPieceTypeId = event.getNewType().getPieceTypeId();
        this.oldPieceTypeId = event.getOldType().getPieceTypeId();
    }

    public UUID getPieceId(){
        return pieceId;
    }

    public PieceTypeId getNewPieceTypeId(){
        return newPieceTypeId;
    }

    public PieceTypeId getOldPieceTypeId(){
        return oldPieceTypeId;
    }

}
