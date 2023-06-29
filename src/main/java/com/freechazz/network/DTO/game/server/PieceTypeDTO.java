package com.freechazz.network.DTO.game.server;

import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.pieces.PieceTypeId;

import java.util.ArrayList;
import java.util.List;


//only for direct loading of pieceType
public class PieceTypeDTO {
    private PieceTypeId pieceTypeID;

    private List<ActionDTO> actions;

    public PieceTypeDTO(PieceType pieceType) {
        this.pieceTypeID = pieceType.getPieceTypeId();
        this.actions = new ArrayList<>();
        pieceType.getActionMap().keySet().forEach(pos -> {
            this.actions.add(new ActionDTO(pos, ""+pieceType.getActionMap().get(pos).getSymbol()));
        });
    }


    public PieceTypeId getPieceTypeID() {
        return pieceTypeID;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }
}