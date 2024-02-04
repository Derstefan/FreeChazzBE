package com.freechazz.game.state;

import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class Board {


    private final int width;
    private final int height;

    private ArrayList<Piece> pieces = new ArrayList<>();

    // double point of information, but it's easier to access
    private Field[][] board;


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Field[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = new Field();
            }
        }
    }


    public Piece pieceAt(Pos p) {
        if (p.getX() < 0 || p.getX() >= width || p.getY() < 0 || p.getY() >= height) {
            return null;
        }
        return board[p.getY()][p.getX()].getPiece();
    }

    public boolean isFree(Pos p) {
        if (board[p.getY()][p.getX()].getPiece() == null) {
            return true;
        }
        return false;
    }

    public boolean isFree(int x, int y) {
        if (!isOnboard(new Pos(x, y))) return true;
        if (board[y][x].getPiece() == null) {
            return true;
        }
        return false;
    }


    public String symbolAt(Pos p) {
        if (pieceAt(p) != null) {
            Piece piece = pieceAt(p);
            String str = piece.isKing() ? "K" : " ";

            return " " + piece.getSymbol() + ":" + piece.getId() + ":" + piece.getOwner() + " " + str + " ";
        }
        return "     -     ";
    }

    public boolean isOnboard(Pos pos) {
        int x = pos.getX();
        int y = pos.getY();
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean areEnemies(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.getOwner() != p2.getOwner();
    }


// ------------------------ internal Boardoperations ------------------------

    /**
     * put pieces to board
     *
     * @param pos
     */
    protected void removePiece(Pos pos) {
        Piece p = pieceAt(pos);
        pieces.remove(p);
        board[pos.getY()][pos.getX()].setPiece(null);
    }


    /**
     * Put Piece to position on board. (Piece is already in game)
     *
     * @param piece
     * @param pos
     */
    protected void putPiece(Piece piece, Pos pos) {
        piece.setPosition(pos);
        pieces.add(piece);
        board[pos.getY()][pos.getX()].setPiece(piece);
    }


    //------------------------ getter and setter ------------------------

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field[][] getBoardArray() {
        return board;
    }

    public String toString() {
        String str = "\n - ";
        for (int j = 0; j < board[0].length; j++) {
            str += "     " + j + "     ";
        }
        str += " - ";
        str += "\n";
        for (int i = 0; i < board.length; i++) {
            str += i + "|";
            for (int j = 0; j < board[0].length; j++) {
                str += symbolAt(new Pos(j, i));
            }
            str += "|\n";
        }
        for (int j = 0; j < board[0].length + 2; j++) {
            str += "     -     ";
        }
        return str;
    }

    public Board copy() {
        Board copy = new Board(width, height);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Piece p = pieceAt(new Pos(i, j));
                if (p != null) {
                    copy.getBoardArray()[j][i].setPiece(p.copy());
                    copy.getPieces().add(copy.getBoardArray()[j][i].getPiece());
                }
            }
        }
        return copy;
    }


}
