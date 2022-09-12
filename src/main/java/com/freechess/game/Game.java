package com.freechess.game;

import com.freechess.game.board.Board;
import com.freechess.game.board.ESize;
import com.freechess.game.draw.Draw;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import com.freechess.game.player.EPlayer;
import com.freechess.game.player.Player;
import com.freechess.game.player.bots.BetterBot;
import com.freechess.game.player.bots.Bot;
import com.freechess.game.player.bots.StupidBot;
import com.freechess.generators.board.impl.SymmetricBoardGenerator;
import com.freechess.server.DTO.DrawData;
import com.freechess.server.DTO.GameParams;

import javax.swing.text.html.Option;
import java.util.*;

public class Game {



    private final UUID gameId;
    private long lastAction;
    private Optional<Bot> bot = Optional.empty();
    private final long seed;
    private final Board board;
    private Player player1;
    private Player player2;



    private final ArrayList draws = new ArrayList<Draw>();
    private int round = 0;

    private EPlayer playersTurn;


    public Game(){
        gameId = UUID.randomUUID();
        seed = (long) (Math.random() * Long.MAX_VALUE);
        SymmetricBoardGenerator gen = new SymmetricBoardGenerator(seed);
        board = gen.generate();
        playersTurn = diceStartPlayer();
        lastAction = System.currentTimeMillis();
        setBot(Optional.of(new BetterBot(EPlayer.P2)));
    }

    public Game(GameParams params){
        gameId = UUID.randomUUID();
        seed = params.getSeed()!=null?params.getSeed():seedOfADay();
        SymmetricBoardGenerator gen = new SymmetricBoardGenerator(seed);
        board = params.getESize()!=null?gen.generate(params.getESize()):gen.generate();
        playersTurn = diceStartPlayer();
        lastAction = System.currentTimeMillis();

        checkGameType(params.getType());
    }

    private long seedOfADay(){
        Date date = new Date();
        return (long)(date.getTime()/1000)-date.getHours()*3600*60-date.getMinutes()*60 -date.getSeconds();
    }

    private void checkGameType(String type){
        if("mp".equals(type)){
            setBot(Optional.empty());
        } else {
            if(board.getHeight()>=ESize.big.getHeight()){
                setBot(Optional.of(new StupidBot(EPlayer.P2)));
            } else {
                setBot(Optional.of(new BetterBot(EPlayer.P2)));
            }

        }
    }


    public boolean join(Player player){
        if(player1==null) {
            player1 = player;
            return true;
        }
        if(player2==null){
            player2 = player;
            return true;
        }
        return false;
    }


    public void play(Position fromPos, Position toPos){
        lastAction = System.currentTimeMillis();

        if(board.getWinner().isPresent()){
            return;
        }

        Piece piece = board.pieceAt(fromPos);

        // is it this players turn?
        if(piece.getOwner().equals(playersTurn)){
            // is it possible move??
            if(piece.canMoveTo(toPos)){
                board.perform(fromPos,toPos);

                if(playersTurn.equals(EPlayer.P1)) {
                    player1.setLastActionTime();
                }else {
                    player2.setLastActionTime();
                }
            }
        }
        endTurn();
    }


    private void endTurn(){
        //Check Win/Lose
        if(board.getWinner().isPresent()){
            // board.getWinner() wins
        }

        if(playersTurn.equals(EPlayer.P1)){
            playersTurn=EPlayer.P2;
        } else {
            playersTurn=EPlayer.P1;
        }
        board.computePossibleMoves();
        round++;
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

    public Board getBoard() {
        return board;
    }

    public int getRound() {
        return round;
    }

    public long getSeed() {
        return seed;
    }

    public long getLastAction() {
        return lastAction;
    }

    public boolean isSingleplayer() {
        return bot.isPresent();
    }

    public Optional<Bot> getBot() {
        return bot;
    }

    public void setBot(Optional<Bot> bot) {
        this.bot = bot;
    }

    public EPlayer getPlayersTurn() {
        return playersTurn;
    }

    public Optional<Player> getWinner(){

        Optional<EPlayer> winner = board.getWinner();
        if(!winner.isPresent()){
            return Optional.empty();
        }
        if(winner.get()==EPlayer.P1){
            return Optional.of(player1);
        } else {
            return Optional.of(player2);
        }

    }

    private EPlayer diceStartPlayer(){
        if(Math.random()>=0.5){
            return EPlayer.P1;
        } else {
            return EPlayer.P2;
        }
    }


    //constructor for copy game
    private Game(Game anotherGame){
        gameId = anotherGame.getGameId();
        seed = anotherGame.getSeed();
        playersTurn = anotherGame.getPlayersTurn();
        lastAction = anotherGame.getLastAction();
        bot=anotherGame.getBot();

        player1 = anotherGame.getPlayer1()!=null?anotherGame.getPlayer1().copy():null;
        player2 = anotherGame.getPlayer2()!=null?anotherGame.getPlayer2().copy():null;

        board = anotherGame.getBoard().copy();
    }
    public Game copy(){
        return new Game(this);
    }
}
