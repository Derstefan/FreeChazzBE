package com.freechazz.network.DTO.game.server;

import com.freechazz.game.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PieceTypeDTO {
    private String typeId;
    private List<ActionDTO> actions;

    public PieceTypeDTO(PieceType pieceType) {
        this.typeId = pieceType.getPieceTypeId().toString();
        this.actions = new ArrayList<>();
        pieceType.getActionMap().keySet().forEach(pos -> {
            this.actions.add(new ActionDTO(pos, ""+pieceType.getActionMap().get(pos).getSymbol()));
        });
    }


    public String getTypeId() {
        return typeId;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }
}