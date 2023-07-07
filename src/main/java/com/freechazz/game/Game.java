package com.freechazz.game;

import com.freechazz.game.eventManager.DrawEvent;
import com.freechazz.game.formation.Formation;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.Pos;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.player.Player;
import com.freechazz.bots.Bot;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.state.GameOperatorBuilder;
import com.freechazz.network.DTO.game.client.DrawDataDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Game {



    private final UUID gameId;
    private Player player1;
    private Player player2;
    private Formation formation1;
    private Formation formation2;


    private long lastAction;
    private int turns = 0;
    private EPlayer playersTurn;
    private final GameOperator state;

    private boolean isCopy = false;







    public Game(Formation formation1, Formation formation2) {
        gameId = UUID.randomUUID();
        this.formation1 = formation1;
        this.formation2 = formation2;
        GameOperatorBuilder boardbuilder = new GameOperatorBuilder(formation1.getSize().getWidth(),formation1.getSize().getHeight());
        boardbuilder.putKing(EPlayer.P1, formation1.getKing(), formation1.getKingPos());
        for(Pos pos: formation1.getPieceTypes().keySet()){
         //   log.info("pos: {}",pos);
            if(pos.equals(formation1.getKingPos())){
                continue;
            }
            PieceType pieceType = formation1.getPieceTypes().get(pos);
            boardbuilder.putPieceMirrored(new Piece(EPlayer.P1,pieceType),pos);
        }
       // log.info("------------------");
        boardbuilder.putKing(EPlayer.P2, formation2.getKing(), formation2.getKingPos());
        for(Pos pos: formation2.getPieceTypes().keySet()){
          //  log.info("pos: {}",pos);
            if(pos.equals(formation2.getKingPos())){
                continue;
            }
            PieceType pieceType = formation2.getPieceTypes().get(pos);
            boardbuilder.putPiece(new Piece(EPlayer.P2,pieceType),pos);
        }
        state = boardbuilder.build();
        player1 = new Player(formation1.getOwner().getName(),EPlayer.P1);
        player2 = new Player(formation2.getOwner().getName(),EPlayer.P2);


    }


// --------------------- Player/Bot ---------------------

    public boolean play(DrawDataDTO draw){
        return play(draw.getFromPos(),draw.getToPos());
    }

    public boolean play(Pos fromPos, Pos toPos){
        if(!validateDrawLogic(fromPos,toPos)){
            return false;
        }
        lastAction = System.currentTimeMillis();
        state.performDraw(fromPos,toPos);
        endTurn();
        return true;
    }




    public void surrender(){
        if(playersTurn.equals(EPlayer.P1)){
            state.setWinner(EPlayer.P2);
        } else {
            state.setWinner(EPlayer.P1);
        }
        //log.info("Player " + playersTurn + " surrendered after " + turns + " turns!");
    }


    public void computePossibleMoves(){
        //log.info("computePossibleMoves");
        state.computePossibleMoves();
    }


    public void botAction(){
        if(getPlayer(playersTurn).getBot()!=null){
            getPlayer(playersTurn).getBot().doDrawOn(this);
        }
    }

//----------------------- ControllerGetter -----------------------

    public ArrayList<DrawEvent> getDrawsSince(int turn){
        ArrayList<DrawEvent> draws = new ArrayList<>();
        for(int i = turn; i < turns; i++){
            draws.add(state.getHistory().getDraw(i));
        }
        return draws;
    }

    public DrawEvent getLastDrawEvent(){
        return state.getHistory().getLastDraw();
    }




    private void endTurn(){
        //Check Win/Lose
        if(state.getWinner().isPresent()){
           // log.info("Game is over! Winner is " + state.getWinner().get());
            return;
        }
        changeTurn();

        if(!isCopy){//gamecopy is for bot draw computation, otherwise we can compute the possible moves
            computePossibleMoves();
        }
       //TODO: if(getPlayer(playersTurn).getBot()!=null){
        //    botAction();
        //}
    }







    private void changeTurn(){
        if(playersTurn.equals(EPlayer.P1)){
            playersTurn=EPlayer.P2;
            player1.setLastActionTime();
        } else {
            playersTurn=EPlayer.P1;
            player2.setLastActionTime();
        }
        turns++;
    }


    private boolean validateDrawLogic(Pos fromPos, Pos toPos){

        if(state.getWinner().isPresent()){
            log.warn("Game is already over! Winner is " + state.getWinner().get());
            return false;
        }
        Piece piece = state.pieceAt(fromPos);

        if(piece==null) {
            log.warn("No Piece at this Position " + fromPos);
            return false;
        }
        // is it this players turn?
        if(!piece.getOwner().equals(playersTurn)) {
            log.warn("it was not your turn. Piece: " + piece.getOwner() + " but the turn is on " + playersTurn + ". piece : " + piece.getId());
            return false;
        }
        // is it possible move??
        if(!canMoveTo(fromPos,toPos)){
            log.warn("This is not a possible move! " + fromPos + " to " + toPos);
            return false;
        }
        return true;
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

    public Player getPlayer(EPlayer player){
        if(player.equals(EPlayer.P1)){
            return player1;
        }
        if(player.equals(EPlayer.P2)){
            return player2;
        }
        return null;
    }

    public GameOperator getState() {
        return state;
    }

    public int getTurns() {
        return turns;
    }

    public long getLastAction() {
        return lastAction;
    }



    public Bot getBotOfP1(){
        return getPlayer1().getBot();
    }
    public void setP1Bot(Bot bot) {
        getPlayer1().setBot(bot);
    }
    public Bot getBotOfP2(){
        return getPlayer2().getBot();
    }
    public void setP2Bot(Bot bot) {
        getPlayer2().setBot(bot);
    }

    public EPlayer getPlayersTurn() {
        return playersTurn;
    }



    public void setPlayersTurn(EPlayer playersTurn) {
        this.playersTurn = playersTurn;
    }


    public Optional<Player> getWinner(){

        Optional<EPlayer> winner = state.getWinner();
        if(!winner.isPresent()){
            return Optional.empty();
        }
        if(winner.get()==EPlayer.P1){
            return Optional.of(player1);
        } else {
            return Optional.of(player2);
        }

    }

    private boolean canMoveTo(Pos fromPos, Pos toPos){
        Piece piece = state.pieceAt(fromPos);
        return piece.getPieceType().isPossibleMove(state,fromPos,toPos);
    }






    //constructor for copy game ------------------------------------------------------------------------------------
    private Game(Game anotherGame){
        gameId = anotherGame.getGameId();
        playersTurn = anotherGame.getPlayersTurn();
        lastAction = anotherGame.getLastAction();

        player1 = anotherGame.getPlayer1()!=null?anotherGame.getPlayer1().copy():null;
        player2 = anotherGame.getPlayer2()!=null?anotherGame.getPlayer2().copy():null;

        state = anotherGame.getState().copy();
        isCopy = true;
    }
    public Game copy(){
        return new Game(this);
    }



    public void undo(){
        state.undoDraw();
        playersTurn = playersTurn.getOpponent();
    }


}
