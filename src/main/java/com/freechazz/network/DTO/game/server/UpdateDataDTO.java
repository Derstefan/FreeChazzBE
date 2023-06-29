package com.freechazz.network.DTO.game.server;

import com.freechazz.game.Game;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.eventManager.DrawEvent;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class UpdateDataDTO {

    private UUID gameId;
    private PlayerDTO player1;
    private PlayerDTO player2;
    private int width;
    private int height;
    private int turn;
    private EPlayer nextTurn;
    private String lastActionTime; //for turn==0 last action ist the creation of the game
    private EPlayer winner = null; //null default
    private boolean draw = false;
    private List<PieceDTO> pieceDTOs; // null if no update

    //TODO
    private DrawEvent drawEvent;


    public UpdateDataDTO(Game game, int turn) {

        this.gameId = game.getGameId();
        this.player1 = new PlayerDTO(game.getPlayer1());
        this.player2 = new PlayerDTO(game.getPlayer2());
        this.width = game.getState().getBoard().getWidth();
        this.height = game.getState().getBoard().getHeight();
        this.turn = turn;
        this.nextTurn = game.getPlayersTurn();
        this.lastActionTime = ((System.currentTimeMillis() - game.getLastAction())/1000) + "s";
        this.drawEvent = game.getLastDrawEvent();

        if(turn>game.getTurns()){
            return;//the requested game state doesn't exist yet
        }
        pieceDTOs = new ArrayList<>();
        if(turn==game.getTurns()){// get last state
            for (Piece p:game.getState().getBoard().getPieces()) {
                PieceDTO pDTO = new PieceDTO(p);
                pieceDTOs.add(pDTO);
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
        this.winner = game.getState().getWinner().isPresent()?game.getState().getWinner().get():null;
        //TODO: this.draw = game.getState().
    }

    public UUID getGameId() {
        return gameId;
    }

    public PlayerDTO getPlayer1() {
        return player1;
    }

    public PlayerDTO getPlayer2() {
        return player2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTurn() {
        return turn;
    }

    public EPlayer getNextTurn() {
        return nextTurn;
    }

    public String getLastActionTime() {
        return lastActionTime;
    }

    public EPlayer getWinner() {
        return winner;
    }

    public List<PieceDTO> getPieceDTOs() {
        return pieceDTOs;
    }
}