package com.freechazz.network.DTO.game.server;

import com.freechazz.game.Game;

import java.util.UUID;


public class MatchData {
    public UUID gameId;
    public int width;
    public int height;
    public int turns;
    public int maxTurns;
    public String playerTurn;
    public String winner; // null if not finished
    public boolean p1IsBot;
    public boolean p2IsBot;
    public UUID user1Id;
    public UUID user2Id;

    public MatchData(Game game) {
        this.gameId = game.getGameId();
        this.width = game.getState().getBoard().getWidth();
        this.height = game.getState().getBoard().getHeight();
        this.turns = game.getTurns();
        this.maxTurns = game.getTurns();
        this.playerTurn = game.getPlayersTurn().name();
        if (game.getState().getWinner().isPresent()) {
            this.winner = game.getState().getWinner().get().name();
        } else {
            this.winner = null;
        }
        this.p1IsBot = game.getPlayer1().isAI();
        this.p2IsBot = game.getPlayer2().isAI();
        this.user1Id = game.getPlayer1().getUserId();
        this.user2Id = game.getPlayer2().getUserId();
    }

    
}