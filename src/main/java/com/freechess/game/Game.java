package com.freechess.game;

import com.freechess.game.board.Board;
import com.freechess.game.draw.Draw;
import com.freechess.game.pieces.Piece;
import com.freechess.game.board.Position;
import com.freechess.game.player.EPlayer;
import com.freechess.game.player.Player;
import com.freechess.generators.board.impl.SymmetricBoardGenerator;
import com.freechess.server.DTO.GameData;
import com.freechess.server.DTO.GameParams;
import com.freechess.server.repository.GameDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Game {


    @Autowired
    private GameDataRepository gameDataRepository;
    private final UUID gameId;

    private long lastAction;

    private boolean isSingleplayer;
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
        isSingleplayer=true;


    }

    public Game(GameParams params){
        gameId = UUID.randomUUID();
        seed = params.getSeed()!=null?params.getSeed():(long) (Math.random() * Long.MAX_VALUE);
        SymmetricBoardGenerator gen = new SymmetricBoardGenerator(seed);
        board = params.getESize()!=null?gen.generate(params.getESize()):gen.generate();
        playersTurn = diceStartPlayer();
        lastAction = System.currentTimeMillis();

        checkGameType(params.getType());
    }

    private void checkGameType(String type){
        if("mp".equals(type)){
            isSingleplayer=false;
        } else {
            isSingleplayer=true;
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
            gameDataRepository.save(new GameData(this));
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
        return isSingleplayer;
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
}
