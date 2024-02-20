package com.freechazz.network.DTO.game.server;

import com.freechazz.game.Game;
import com.freechazz.game.GameHelper;
import com.freechazz.game.core.EPlayer;
import com.freechazz.network.DTO.game.server.event.DrawEventDTO;
import com.freechazz.network.DTO.game.server.log.TurnLogDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class UpdateDataDTO {

    private UUID gameId;
    private PlayerDTO player1;
    private PlayerDTO player2;
    //gibt die Breite an. Je breiter, desto
    private int width;
    private int height;
    private int turn;

    private int maxTurn;
    private EPlayer nextTurn;
    private String lastActionTime; //for turn==0 last action ist the creation of the game
    private String winner = "";
    private boolean draw = false;

    private DrawEventDTO drawEvent;
    private List<PieceDTO> pieceDTOs; // null if no update

    private TurnLogDTO turnLogDTO = null;

    //TODO


    public UpdateDataDTO(Game game, int turn, boolean withPieces) {
        log.info("try to create updategaemdto " + turn);
        this.gameId = game.getGameId();
        this.player1 = new PlayerDTO(game.getPlayer1());
        this.player2 = new PlayerDTO(game.getPlayer2());
        this.width = game.getState().getBoard().getWidth();
        this.height = game.getState().getBoard().getHeight();
        this.maxTurn = game.getTurns();
        this.turn = turn;
        this.nextTurn = game.getPlayersTurn();
        this.lastActionTime = ((System.currentTimeMillis() - game.getLastAction()) / 1000) + "s";
        this.drawEvent = game.getState().getHistory().getHistoryState(turn) != null ? new DrawEventDTO(game.getState().getHistory().getHistoryState(turn).getDrawEvent()) : null;
        if (turn > game.getTurns()) {
            //log.info("UpdateDataDTO: " + turn + " > " + game.getTurns());
            return;//the requested game state doesn't exist yet
        }
        if (withPieces) {
            pieceDTOs = GameHelper.getPieceDTOs(game, turn);
        }
        this.turnLogDTO = game.getState().getHistory().getHistoryState(turn).getTurnLogDTO();
        log.info(turnLogDTO.getLogMessages().size() + "");

        this.winner = game.getState().getWinner().isPresent() ? game.getState().getWinner().get().name() : "";
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
        // Hello Wörld!
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

    public String getWinner() {
        return winner;
    }

    public DrawEventDTO getDrawEvent() {
        return drawEvent;
    }

    public List<PieceDTO> getPieceDTOs() {
        return pieceDTOs;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public TurnLogDTO getTurnLogDTO() {
        return turnLogDTO;
    }
}