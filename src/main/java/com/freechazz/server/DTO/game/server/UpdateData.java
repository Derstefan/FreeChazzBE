package com.freechazz.server.DTO.game.server;

import com.freechazz.game.Game;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class UpdateData {
    private int turn;
    private Player player1;
    private Player player2;
    private EPlayer nextTurn;
    private EPlayer winner; //null default
    private List<PieceDTO> pieceDTOs; // null if no update
    private String lastAction;

    public UpdateData(Game game, int turn) {

        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();

        if(turn>game.getTurns()){
            //TODO: the requested game state doesn't exist yet
        }
        pieceDTOs = new ArrayList<>();

        this.turn = turn;
        this.winner = game.getState().getWinner().get();
        this.lastAction = ((System.currentTimeMillis() - game.getLastAction())/1000) + "s";

        if(turn==game.getTurns()){// get last state
            for (Piece p:game.getState().getBoard().getPieces()) {
                pieceDTOs.add(new PieceDTO(p));
            }
        }else {// request an older gamestate
            Game gameCopy = game.copy();
            int diff = game.getTurns()-turn;
            for (int i = 0; i < diff; i++) {
                gameCopy.undo();
            }
            for (Piece p:gameCopy.getState().getBoard().getPieces()) {
                pieceDTOs.add(new PieceDTO(p));
            }
        }
    }


    public int getTurns() {
        return turn;
    }

    public void setTurns(int turns) {
        this.turn = turns;
    }

    public EPlayer getWinner() {
        return winner;
    }

    public void setWinner(EPlayer winner) {
        this.winner = winner;
    }

    public List<PieceDTO> getPieceDTOs() {
        return pieceDTOs;
    }

    public void setPieceDTOs(List<PieceDTO> pieceDTOs) {
        this.pieceDTOs = pieceDTOs;
    }

    public String getLastAction() {
        return lastAction;
    }
}