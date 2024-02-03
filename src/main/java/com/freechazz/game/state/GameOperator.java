package com.freechazz.game.state;

import com.freechazz.game.actions.Action;
import com.freechazz.game.core.EPlayer;
import com.freechazz.game.core.Pos;
import com.freechazz.game.eventManager.DrawEvent;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.MatchHistory;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class GameOperator {

    private final int width;
    private final int height;
    private EPlayer winner = null;


    private MatchHistory history;

    private EPlayer playersTurn;


    private Board board;

    private ArrayList<Piece> graveyard = new ArrayList<>();

    private boolean isBotCopy = false;


    public void performDraw(Pos fromPos, Pos toPos) {
        Piece piece = pieceAt(fromPos);
        history.addState();
        Action action = piece.getPieceType().perform(this, fromPos, toPos);
        computePossibleMoves();
        history.getLastState().setPieceDTOs(board.getPieces());
        if (!isBotCopy) {
            //  log.info("Player " + getPlayersTurn() + " played " + fromPos + " -> " + toPos + " with " + action.getAct().toString());
        }
    }


    public void performEvent(Event event) {
        history.addEvent(event);
        event.perform(this);
    }


    // ------------------------ For Bots ------------------------
    public void undoDraw() {
        DrawEvent draw = history.getLastDraw();
        if (draw == null) {
            log.warn("No Draw to undo");
        }

        int count = draw.getEventCount();
        if (count == 0) {
            log.warn("No Events to undo");
        }
        for (int i = 0; i < count; i++) {
            undoEvent(draw.getLastEvent());
        }
        history.removeLastState();
    }


    public void undoEvent(Event event) {
        event.undo(this);
        history.getLastDraw().removeLastEvent();
    }

    // ------------------------ GameController Action Getter ------------------------


    public Piece pieceAt(Pos p) {
        return board.pieceAt(p);
    }

    public boolean isFree(Pos p) {
        return board.isFree(p);
    }

    public boolean isOnboard(Pos pos) {
        return board.isOnboard(pos);
    }

    public boolean areEnemys(Piece p1, Piece p2) {
        return board.areEnemies(p1, p2);
    }


// ------------------------ internal Boardoperations ------------------------


    /**
     * Remove piece from Pos.
     *
     * @param pos
     */

    public void removePiece(Pos pos) {
        board.removePiece(pos);
    }


    /**
     * Put Piece to position on board.
     *
     * @param piece
     * @param pos
     */
    public void putPiece(Piece piece, Pos pos) {
        board.putPiece(piece, pos);
    }


    //-----------------------------------------------------------------------

    /**
     * Compute possible moves of all pieces.
     */
    public void computePossibleMoves() {
        for (Piece piece : board.getPieces()) {
            computePossibleMoves(piece);
        }
    }

    public void computePossibleMoves(Piece piece) {
        Pos pos = piece.getPos();
        PieceType type = piece.getPieceType();
        piece.setMoveSet(type.computePossibleMoves(this, pos));
    }

    public int distanceToEnemy(EPlayer enemy, Pos pos) {
        int distance = 9000;
        for (Piece p : getAllPiecesFrom(enemy)) {
            if (BoardUtil.distance(pos, p.getPos()) < distance) {
                distance = BoardUtil.distance(pos, p.getPos());
            }
        }
        return distance;
    }

    //computes minimal disctance to enemy piece
    public void computeEnemyDistances() {
        for (Piece p1 : getAllPiecesFrom(EPlayer.P1)) {
            int distance = 9000;
            for (Piece p2 : getAllPiecesFrom(EPlayer.P2)) {
                if (BoardUtil.distance(p1, p2) < distance) {
                    distance = BoardUtil.distance(p1, p2);
                }
            }
            p1.setDistanceToEnemy(distance);
        }
    }


    //-------- some more Methods --------


    public String toString() {
        return board.toString();
    }

    public static GameOperator getInstance(int width, int height) {
        return new GameOperator(width, height);
    }

    private GameOperator(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Board(width, height);
    }

    private GameOperator(GameOperator anotherState) {
        width = anotherState.getWidth();
        height = anotherState.getHeight();

        winner = anotherState.getWinner().isPresent() ? anotherState.getWinner().get() : null;
        graveyard = new ArrayList<>();

        for (Piece p : anotherState.getGraveyard()) {
            graveyard.add(p.copy());
        }
        board = anotherState.getBoard().copy();
        history = anotherState.getHistory().copy();
    }

    public GameOperator copy() {

        GameOperator copy = new GameOperator(this);
        copy.setBotCopy(true);
        return copy;
    }


    // ----------- GETTER/SETTER -----------------------


    protected void setHistory(MatchHistory history) {
        this.history = history;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field[][] getBoardArray() {
        return board.getBoardArray();
    }

    public Board getBoard() {
        return board;
    }

    public Optional<EPlayer> getWinner() {
        return winner != null ? Optional.of(winner) : Optional.empty();
    }

    public void setWinner(EPlayer winner) {
        // log.info("Winner is " + winner);
        this.winner = winner;
    }

    public Piece getKing1() {
        for (Piece p : board.getPieces()) {
            if (p.isKing() && p.getOwner() == EPlayer.P1) {
                return p;
            }
        }
        return null;
    }

    public void setKing1(Piece king1) {
        king1.setKing(true);
        //this.king1 = king1;
    }

    public Piece getKing2() {
        for (Piece p : board.getPieces()) {
            if (p.isKing() && p.getOwner() == EPlayer.P2) {
                return p;
            }
        }
        return null;
    }

    public void setKing2(Piece king2) {
        king2.setKing(true);
        //this.king2 = king2;
    }


    public ArrayList<Piece> getGraveyard() {
        return graveyard;
    }


    public ArrayList<Piece> getAllPieces() {
        return board.getPieces();
    }

    public ArrayList<Piece> getAllPiecesFrom(EPlayer ePlayer) {
        ArrayList<Piece> list = new ArrayList<>();
        for (Piece p : board.getPieces()) {
            if (p.getOwner() == ePlayer) {
                list.add(p);
            }
        }
        return list;
    }

    public String symbolAt(Pos p) {
        return board.symbolAt(p);
    }


    public MatchHistory getHistory() {
        return history;
    }

    public EPlayer getPlayersTurn() {
        return playersTurn;
    }

    public void setPlayerTurn(EPlayer playersTurn) {
        this.playersTurn = playersTurn;
    }

    public boolean isBotCopy() {
        return isBotCopy;
    }

    public void setBotCopy(boolean botCopy) {
        isBotCopy = botCopy;
    }


}
