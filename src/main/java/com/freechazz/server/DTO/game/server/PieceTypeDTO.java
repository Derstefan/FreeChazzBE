package com.freechazz.server.DTO.game.server;

import com.freechazz.game.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PieceTypeDTO {
    private String typeId;
    private List<ActionDTO> actions;

    public PieceTypeDTO(PieceType pieceType) {

        this.typeId = pieceType.getSerial();
        this.actions = new ArrayList<>();
        pieceType.getActionMap().keySet().forEach(pos -> {
            this.actions.add(new ActionDTO(pos, ""+pieceType.getActionMap().get(pos).getSymbol()));
        });
    }

}