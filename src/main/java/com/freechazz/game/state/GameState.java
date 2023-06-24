package com.freechazz.game.state;

import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.*;
import com.freechazz.game.eventManager.events.*;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class GameState {

    private int width;
    private int height;
    private Piece king1;
    private Piece king2;
    private Optional<EPlayer> winner = Optional.empty();



    private DrawManager drawManager = new DrawManager();


    private Board board;

    private ArrayList<Piece> graveyard = new ArrayList<>();



    public void perform(Pos fromPos, Pos toPos) {
        Piece piece = pieceAt(fromPos);
        drawManager.addDraw();
        piece.getPieceType().perform(this,fromPos,toPos);

    }

    // -------------------- GameController - Events only these are allowed to Actions --------------------

    /**
     * Moves Pieces from one position to a free Position
     * @param fromPos
     * @param toPos
     */
     public void move(Pos fromPos, Pos toPos){
         Piece targetPiece = pieceAt(toPos);
         Piece piece = pieceAt(fromPos);
         if (targetPiece == null) {
             removePiece(fromPos);
             putPiece(piece, toPos);
         }

        drawManager.addEvent(new MoveEvent(fromPos, piece, toPos));
     }

    /**
     * Destroys a Piece at a position and adds it to the graveyard
     * @param pos
     */
    public void destroy(Pos pos){
        Piece p = pieceAt(pos);
        if(p.equals(king1)){
            setWinner(king2.getOwner());
        } else if(p.equals(king2)){
            setWinner(king1.getOwner());
        }
        removePiece(pos);
        graveyard.add(p);
        drawManager.addEvent(new DestroyEvent(p,pos));
    }

    /**
     * Swaps two Pieces
     * @param fromPos
     * @param toPos
     */
    public void swap(Pos fromPos, Pos toPos){
        Piece p1 = pieceAt(fromPos);
        Piece p2 = pieceAt(toPos);
        removePiece(fromPos);
        removePiece(toPos);
        putPiece(p1,toPos);
        putPiece(p2,fromPos);

        drawManager.addEvent(new SwapEvent(fromPos, toPos));
    }

    /**
     * Changes the owner of Piece
     * @param piece
     */
    public void changeOwner(Piece piece){
        piece.setOwner(piece.getOwner().getOpponent());
        drawManager.addEvent(new ChangeOwnerEvent(piece));
    }

    /**
     * Changes the PieceType of Piece
     * @param piece
     * @param newType
     */
    public void changeType(Piece piece, PieceType newType){
        PieceType oldType = piece.getPieceType();
        piece.setPieceType(newType);
        drawManager.addEvent(new ChangeTypeEvent(piece,oldType, newType));
    }


    // ------------------------ GameController Action Getter ------------------------


    public Piece pieceAt(Pos p){
        return board.pieceAt(p);
    }

    public boolean isFree(Pos p){
        return board.isFree(p);
    }
    public boolean isOnboard(Pos pos){
        return board.isOnboard(pos);
    }

    public boolean areEnemys(Piece p1,Piece p2){
        return board.areEnemys(p1,p2);
    }


// ------------------------ internal Boardoperations ------------------------


    /**
     * Remove piece from Pos.
     * @param pos
     */

    private void removePiece(Pos pos){
        board.removePiece(pos);
    }


    /**
     * Put Piece to position on board. (Protected for builder)
     * @param piece
     * @param pos
     */
    protected void putPiece(Piece piece, Pos pos){
        board.putPiece(piece,pos);
    }


    //-----------------------------------------------------------------------

    /**
     * Compute possible moves of all pieces.
     */
    public void computePossibleMoves(){
        for (Piece p : board.getPieces()) {
                Pos pos = p.getPos();
                Piece piece = pieceAt(pos);
                if(piece!=null){
                    PieceType type = piece.getPieceType();
                    piece.setPossibleMoves(type.computePossibleMoves(this,pos));
                }
        }
    }

    public int distanceToEnemy(EPlayer enemy, Pos pos){
        int distance = 9000;
        for (Piece p: getAllPiecesFrom(enemy)) {
            if(BoardUtil.distance(pos,p.getPos())<distance){
                distance = BoardUtil.distance(pos,p.getPos());
            }
        }
        return distance;
    }

    //computes minimal disctance to enemy piece
    public void computeEnemyDistances(){
        for (Piece p1 : getAllPiecesFrom(EPlayer.P1)) {
            int distance = 9000;
            for (Piece p2: getAllPiecesFrom(EPlayer.P2)) {
                if(BoardUtil.distance(p1,p2)<distance){
                    distance = BoardUtil.distance(p1,p2);
                }
            }
            p1.setDistanceToEnemy(distance);
        }
    }










    //-------- UNDO SECTION only for bots to check and undo potential moves --------



    public void undoDraw(){
        DrawEvent draw = drawManager.getLastDraw();
        if(draw==null) throw new IllegalArgumentException("No Draw to undo");

        int count = draw.getEventCount();
        if(count==0)throw new IllegalArgumentException("No Events to undo");
        for (int i = 0; i < count; i++) {
            Event event = draw.getLastEvent();
            if(event instanceof MoveEvent){
                undoMove((MoveEvent) event);
            } else if (event instanceof DestroyEvent){
                undoDestroy((DestroyEvent) event);
            } else if(event instanceof SwapEvent){
                undoSwap((SwapEvent) event);
            } else if(event instanceof ChangeOwnerEvent){
                undoChangeOwner((ChangeOwnerEvent) event);
            } else if(event instanceof ChangeTypeEvent){
                undoChangeType((ChangeTypeEvent) event);
            } else {
                throw new IllegalArgumentException("no such Event exists" );
            }
            drawManager.getLastDraw().removeLastEvent();
        }
        drawManager.removeLastDraw();
    }




    private void undoMove(MoveEvent moveEvent){
        Pos fromPos = moveEvent.getFromPos();
        Piece piece = moveEvent.getPiece();
        Pos toPos = moveEvent.getToPos();
        if(!isFree(fromPos)){
            throw new IllegalArgumentException("Cant undo Move Event, because there is a Piece at fromPos...");
        }
        //undo Operation
        removePiece(toPos);
        putPiece(piece, fromPos);
    }

    private void undoDestroy(DestroyEvent destroyEvent){
        Piece piece = destroyEvent.getPiece();
        Pos pos = destroyEvent.getPos();

        //undo Operation
        graveyard.remove(piece);
        putPiece(piece, pos);
    }

    private void undoSwap(SwapEvent swapEvent){
        Pos pos1 = swapEvent.getFromPos();
        Pos pos2 = swapEvent.getToPos();
        Piece p2 = pieceAt(pos1);
        Piece p1 = pieceAt(pos2);

        //undo Operation
        removePiece(pos1);
        removePiece(pos2);
        putPiece(p1,pos1);
        putPiece(p2,pos2);
    }

    private void undoChangeOwner(  ChangeOwnerEvent changeOwnerEvent){
        Piece piece = changeOwnerEvent.getPiece();
        //undo Operation
        piece.setOwner(piece.getOwner().getOpponent());
    }

    private void undoChangeType(ChangeTypeEvent changeTypeEvent){
        Piece piece = changeTypeEvent.getPiece();
        PieceType oldType = changeTypeEvent.getOldType();
        //undo Operation
        piece.setPieceType(oldType);
    }





    //-------- some more Methods --------




    public String toString(){
        return board.toString();
    }

    public static GameState getInstance(int width, int height){
        return new GameState(width,height);
    }

    private GameState(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Board(width,height);
    }

    private GameState(GameState anotherState){
        width = anotherState.getWidth();
        height = anotherState.getHeight();

        king1 = anotherState.getKing1().copy();
        king2 = anotherState.getKing2().copy();

        winner = anotherState.getWinner().isPresent()?Optional.of(anotherState.getWinner().get()):Optional.empty();
        graveyard = new ArrayList<>();

        for(Piece p: anotherState.getGraveyard()){
            graveyard.add(p.copy());
        }
        board = anotherState.getBoard().copy();
    }
    public GameState copy(){
        return new GameState(this);
    }





    // ----------- GETTER/SETTER -----------------------

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Piece[][] getBoardArray() {
        return board.getBoardArray();
    }

    public Board getBoard() {
        return board;
    }

    public Optional<EPlayer> getWinner() {
        return winner;
    }

    public void setWinner(EPlayer winner) {
        this.winner = Optional.of(winner);
    }

    public Piece getKing1() {
        return king1;
    }

    public void setKing1(Piece king1) {
        king1.setKing(true);
        this.king1 = king1;
    }

    public Piece getKing2() {
        return king2;
    }

    public void setKing2(Piece king2) {
        king2.setKing(true);
        this.king2 = king2;
    }


    public ArrayList<Piece> getGraveyard() {
        return graveyard;
    }

    public ArrayList<Piece> getAllPiecesFrom(EPlayer ePlayer){
        ArrayList<Piece> list = new ArrayList<>();
        for (Piece p:board.getPieces()) {
            if(p.getOwner()==ePlayer   ) {
                list.add(p);
            }
        }
        return list;
    }

    public String symbolAt(Pos p){
        return board.symbolAt(p);
    }


    public DrawManager getDrawManager() {
        return drawManager;
    }

}
