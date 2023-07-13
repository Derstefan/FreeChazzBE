package com.freechazz.network.DTO.game.server.event.archiv;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.events.MoveAndDestroyEvent;
import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.server.event.EventDTO;

import java.util.UUID;

public class MoveAndDestroyEventDTO extends EventDTO {
    private Pos fromPos;
    private UUID pieceId;
    private Pos toPos;
    private UUID targetPieceId;


    public MoveAndDestroyEventDTO(MoveAndDestroyEvent event){
        super(event);
        this.fromPos = event.getFromPos();
        this.pieceId = event.getPiece().getPieceId();
        this.toPos = event.getToPos();
        this.targetPieceId = event.getTargetPiece().getPieceId();
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public UUID getPieceId() {
        return pieceId;
    }

    public Pos getToPos() {
        return toPos;
    }

    public UUID getTargetPieceId() {
        return targetPieceId;
    }
}
