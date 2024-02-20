package com.freechazz.game.eventManager;

import com.freechazz.game.pieces.Piece;
import com.freechazz.network.DTO.game.server.PieceDTO;
import com.freechazz.network.DTO.game.server.log.TurnLogDTO;

import java.util.ArrayList;
import java.util.List;

public class HistoryState {
    private DrawEvent drawEvent;
    private List<PieceDTO> pieceDTOs = new ArrayList<>();
    private TurnLogDTO turnLogDTO = new TurnLogDTO();


    public HistoryState() {
        this.drawEvent = new DrawEvent();
    }

    public void setPieceDTOs(List<Piece> pieceDTOs) {
        for (Piece piece : pieceDTOs) {
            this.pieceDTOs.add(new PieceDTO(piece));
        }
    }

    public void setPiecesByDTOS(List<PieceDTO> pieceDTOs) {
        for (PieceDTO pieceDTO : pieceDTOs) {
            this.pieceDTOs.add(pieceDTO);
        }
    }

    public List<PieceDTO> getPieceDTOs() {
        return pieceDTOs;
    }

    public TurnLogDTO getTurnLogDTO() {
        return turnLogDTO;
    }

    public void addLog(String message) {
        turnLogDTO.addLogMessage(message);
    }

    public DrawEvent getDrawEvent() {
        return drawEvent;
    }

    //add event to drawEvent
    public void addEvent(Event event) {
        drawEvent.addEvent(event);
    }

    public String toString() {
        return "{" +
                "drawEvent=" + drawEvent +
                ", pieceDTOs=" + pieceDTOs.stream().map(PieceDTO::toString).reduce("", (a, b) -> a + b) +
                '}';
    }
}
