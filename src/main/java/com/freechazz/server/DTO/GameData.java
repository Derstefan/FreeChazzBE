package com.freechazz.server.DTO;

import com.freechazz.game.core.EPlayer;
import com.freechazz.game.Game;
import com.freechazz.game.player.Player;

import java.util.Optional;
import java.util.UUID;

public class GameData {

    private UUID gameId;
    private Player player1;
    private Player player2;
    private EPlayer turn;

    private String lastAction;
    private int round;
    private Optional<Player> winner;

    public GameData(Game game) {
        this.gameId = game.getGameId();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.turn = game.getPlayersTurn();
        this.round = game.getTurns();
        this.lastAction = ((System.currentTimeMillis() - game.getLastAction())/1000) + "s";
        this.setWinner(game.getWinner());
    }


    public UUID getGameId() {
        return gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public EPlayer getTurn() {
        return turn;
    }

    public int getRound() {
        return round;
    }

    public Optional<Player> getWinner() {
        return winner;
    }

    public void setWinner(Optional<Player> winner) {
        this.winner = winner;
    }

    public String getLastAction() {
        return lastAction;
    }
}
