package com.freechazz.network.DTO.game.server;

import java.util.ArrayList;


//this is an update DTO for the pieceTypes to load all their actions
public class PieceTypeDTOCollection {

    private ArrayList<PieceTypeDTO> pieceTypeDTOs;

    public PieceTypeDTOCollection(ArrayList<PieceTypeDTO> pieceTypeDTOs) {
        this.pieceTypeDTOs = pieceTypeDTOs;
    }

    public ArrayList<PieceTypeDTO> getPieceTypeDTOs() {
        return pieceTypeDTOs;
    }
}
