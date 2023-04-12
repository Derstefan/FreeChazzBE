package com.freechazz;

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

    private ArrayList<Piece> graveyard = new ArrayList<>();

    private DrawManager drawManager = DrawManager.getInstance();


    private ArrayList<Piece> pieces = new ArrayList<>();

    // double point of information, but it's easier to access
    private Piece[][] board;


    private GameState(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Piece[height][width];
    }


    public void perform(Pos fromPos, Pos toPos) {
        Piece piece = pieceAt(fromPos);
        drawManager.addDraw();
        piece.getPieceType().perform(this,fromPos,toPos);

    }

    // -------------------- GameController - Events only these are allowed to Actions --------------------
     public void move(Pos fromPos, Piece piece, Pos toPos){
         Piece targetPiece = pieceAt(toPos);
         if (targetPiece == null) {
             removePiece(fromPos);
             addPiece(piece, toPos);
         }

        drawManager.addEvent(new MoveEvent(fromPos, piece, toPos));
     }


    public void destroyPiece(Pos pos){
        Piece p = pieceAt(pos);
        if(p.equals(king1)){
            setWinner(king2.getOwner());
        } else if(p.equals(king2)){
            setWinner(king1.getOwner());
        }
        graveyard.add(p);
        removePiece(pos);
        pieces.remove(p);
        drawManager.addEvent(new DestroyEvent(p,pos));
    }

    public void swapPieces(Pos fromPos, Pos toPos){
        Piece p1 = pieceAt(fromPos);
        Piece p2 = pieceAt(toPos);
        addPiece(p1,toPos);
        addPiece(p2,fromPos);

        drawManager.addEvent(new SwapEvent(fromPos, toPos));
    }

    public void changeOwner(Piece piece){
        piece.setOwner(piece.getOwner().getOpponent());
        drawManager.addEvent(new ChangeOwnerEvent(piece));
    }

    public void changeType(Piece piece, PieceType newType){
        PieceType oldType = piece.getPieceType();
        piece.setPieceType(newType);
        drawManager.addEvent(new ChangeTypeEvent(piece,oldType, newType));
    }

// ------------------------


    private void removePiece(Pos pos){
        Piece p = pieceAt(pos);
        p.setPosition(new Pos(-1,-1));
        board[pos.getY()][pos.getX()] = null;
    }

    private void addPiece(Piece piece, Pos pos){
        piece.setPosition(pos);
        board[pos.getY()][pos.getX()] = piece;
    }

    public void addPieceToGame(Piece piece, Pos pos){
        pieces.add(piece);
        addPiece(piece,pos);
    }

    /**
     * Compute possible moves of all pieces.
     */
    public void computePossibleMoves(){
        for (Piece p : pieces) {
                Pos pos = p.getPosition();
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
            if(distance(pos,p.getPosition())<distance){
                distance = distance(pos,p.getPosition());
            }
        }
        return distance;
    }

    //computes minimal disctance to enemy piece
    public void computeEnemyDistances(){
        for (Piece p1 : getAllPiecesFrom(EPlayer.P1)) {
            int distance = 9000;
            for (Piece p2: getAllPiecesFrom(EPlayer.P2)) {
                if(distance(p1,p2)<distance){
                    distance = distance(p1,p2);
                }
            }
            p1.setDistanceToEnemy(distance);
        }
    }

    private int distance(Piece p1,Piece p2){
        return Math.abs(p1.getPosition().getX()-p2.getPosition().getX()) + Math.abs(p1.getPosition().getY()-p2.getPosition().getY());
    }

    private int distance(Pos pos1,Pos pos2){
        return Math.abs(pos1.getX()-pos2.getX()) + Math.abs(pos1.getY()-pos2.getY());
    }


// getter



    public Piece pieceAt(Pos p){
        if(p.getX()<0 || p.getX()>=width || p.getY()<0 || p.getY()>=height){
            return null;
        }
        return board[p.getY()][p.getX()];
    }

    public boolean isFree(Pos p){
        if(board[p.getY()][p.getX()]==null){
            return true;
        }
        return false;
    }

    public boolean isFree(int x,int y){
        if(!isOnboard(new Pos(x,y)))return true;
        if(board[y][x]==null){
            return true;
        }
        return false;
    }




    public String symbolAt(Pos p){
        if(pieceAt(p)!=null){
            Piece piece = pieceAt(p);
            String str = piece.isKing()? "K" :" ";

            return " " +piece.getSymbol() +":"+piece.getId()+":"+piece.getOwner() + " " + str + " ";
        }
        return "     -     ";
    }

    public boolean isOnboard(Pos pos){
        int x = pos.getX();
        int y = pos.getY();
        return x>=0 && x<width && y>=0 && y<height;
    }

    public boolean areEnemys(Piece p1,Piece p2){
        if(p1==null || p2==null){
            return false;
        }
        return p1.getOwner()!=p2.getOwner();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Piece[][] getBoard() {
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

    public int countPieces(){
        return pieces.size();
    }

    public ArrayList<Piece> getAllPiecesFrom(EPlayer ePlayer){
        ArrayList<Piece> list = new ArrayList<>();
        for (Piece p:pieces) {
            if(p.getOwner()==ePlayer   ) {
                list.add(p);
            }
        }
        return list;



        //return pieces.stream().filter(p -> p.getOwner() == ePlayer).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Piece> getAllPieces(){
        return pieces;
    }


    public String toString(){
        String str = "\n - ";
        for(int j=0;j<board[0].length;j++){
            str+="     "+j+"     ";
        }
        str+=" - ";
        str+="\n";
        for(int i=0;i<board.length;i++){
            str+=i+"|";
            for(int j=0;j<board[0].length;j++){
                str+=symbolAt(new Pos(j,i));
            }
            str+="|\n";
        }
        for(int j=0;j<board[0].length+2;j++){
            str+="     -     ";
        }
        return str;
    }

    public static GameState getInstance(int width, int height){
        return new GameState(width,height);
    }







    private GameState(GameState anotherBoard){
        width = anotherBoard.getWidth();
        height = anotherBoard.getHeight();

        king1 = anotherBoard.getKing1().copy();
        king2 = anotherBoard.getKing2().copy();

        winner = anotherBoard.getWinner().isPresent()?Optional.of(anotherBoard.getWinner().get()):Optional.empty();
        graveyard = new ArrayList<>();

        for(Piece p: anotherBoard.getGraveyard()){
            graveyard.add(p.copy());
        }
        board = new Piece[height][width];
        pieces = new ArrayList<Piece>();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                Piece p = anotherBoard.pieceAt(new Pos(i,j));
                if(p!=null){
                    board[j][i]=p.copy();
                    pieces.add(board[j][i]);
                }
            }
        }
    }
    public GameState copy(){
        return new GameState(this);
    }

    // alternative

    public void undoDraw(){

        DrawEvents draw = drawManager.getLastDraw();
        if(draw==null) throw new IllegalArgumentException("No Draw to undo");

        int count = draw.getEventCount();
        if(count==0)throw new IllegalArgumentException("No Events to undo");
        for (int i = 0; i < count; i++) {

            Event event = draw.getLastEvent();

        if(event instanceof MoveEvent){
            MoveEvent moveEvent = (MoveEvent) event;
            Pos fromPos = moveEvent.getFromPos();
            Piece piece = moveEvent.getPiece();
            Pos toPos = moveEvent.getToPos();
            if(!isFree(fromPos)){
                throw new IllegalArgumentException("Cant undo Move Event, because there is a Piece at fromPos...");
            }

            //undo Operation
            removePiece(toPos);
            addPiece(piece, fromPos);

//            log.info("undo MoveEvent" );

        } else if (event instanceof DestroyEvent){
            DestroyEvent destroyEvent = (DestroyEvent) event;
            Piece piece = destroyEvent.getPiece();
            Pos pos = destroyEvent.getPos();

            //undo Operation
            pieces.add(piece);
            graveyard.remove(piece);
            addPiece(piece, pos);

//            log.info("undo DestroyEvent" );

        } else if(event instanceof SwapEvent){
            SwapEvent swapEvent = (SwapEvent) event;
            Pos pos1 = swapEvent.getFromPos();
            Pos pos2 = swapEvent.getToPos();
            Piece p2 = pieceAt(pos1);
            Piece p1 = pieceAt(pos2);

            //undo Operation
            removePiece(pos1);
            removePiece(pos2);
            addPiece(p1,pos1);
            addPiece(p2,pos2);

//            log.info("undo SwapEvent" );
        } else if(event instanceof ChangeOwnerEvent){
            ChangeOwnerEvent changeOwnerEvent = (ChangeOwnerEvent) event;
            Piece piece = changeOwnerEvent.getPiece();

            //undo Operation
            piece.setOwner(piece.getOwner().getOpponent());

//            log.info("undo ChangeTypeEvent" );
        } else if(event instanceof ChangeTypeEvent){
            ChangeTypeEvent changeTypeEvent = (ChangeTypeEvent) event;
            Piece piece = changeTypeEvent.getPiece();
            PieceType oldType = changeTypeEvent.getOldType();
            PieceType newType = changeTypeEvent.getNewType();

            //undo Operation
            piece.setPieceType(oldType);

//            log.info("undo ChangeTypeEvent" );
        } else {
            throw new IllegalArgumentException("no such Event exists" );
        }
        drawManager.getLastDraw().removeLastEvent();
        }
 //       computePossibleMoves();
        drawManager.removeLastDraw();
    }



}
