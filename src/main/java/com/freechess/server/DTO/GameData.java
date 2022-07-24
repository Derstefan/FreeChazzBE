package com.freechess.server.DTO;

import com.freechess.game.player.EPlayer;
import com.freechess.game.Game;
import com.freechess.game.player.Player;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "gamedata")
public class GameData {

    private long id;
    @Transient
    private UUID gameId;
    @Transient
    private Player player1;
    @Transient
    private Player player2;
    @Transient
    private EPlayer turn;

    private String lastAction;
    private int round;
    @Transient
    private Optional<Player> winner;

    public GameData(Game game) {
        this.gameId = game.getGameId();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.turn = game.getPlayersTurn();
        this.round = game.getRound();
        this.lastAction = ((System.currentTimeMillis() - game.getLastAction())/1000) + "s";
        this.setWinner(game.getWinner());
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    public UUID getGameId() {
        return gameId;
    }
    @Transient
    public Player getPlayer1() {
        return player1;
    }
    @Transient
    public Player getPlayer2() {
        return player2;
    }
    @Transient
    public EPlayer getTurn() {
        return turn;
    }

    @Column(name = "round", nullable = false)
    public int getRound() {
        return round;
    }
    @Transient
    public Optional<Player> getWinner() {
        return winner;
    }

    public void setWinner(Optional<Player> winner) {
        this.winner = winner;
    }

    @Column(name = "last", nullable = false)
    public String getLastAction() {
        return lastAction;
    }


    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setTurn(EPlayer turn) {
        this.turn = turn;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
