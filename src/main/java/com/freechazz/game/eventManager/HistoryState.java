package com.freechazz.game.eventManager;

import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.server.PieceDTO;

import java.util.ArrayList;
import java.util.List;

public class HistoryState {
    private DrawEvent drawEvent;
    private List<PieceDTO> pieces = new ArrayList<>();


    public HistoryState() {
        this.drawEvent = new DrawEvent();
    }

    public void setPieces(List<Piece> pieces) {
        for (Piece piece:pieces) {
            this.pieces.add(new PieceDTO(piece));
        }
    }

    public void setPiecesByDTOS(List<PieceDTO> pieceDTOs) {
        for (PieceDTO pieceDTO:pieceDTOs) {
            this.pieces.add(pieceDTO);
        }
    }

    public List<PieceDTO> getPieces() {
        return pieces;
    }

    public DrawEvent getDrawEvent() {
        return drawEvent;
    }

    //add event to drawEvent
    public void addEvent(Event event) {
        drawEvent.addEvent(event);
    }
}
