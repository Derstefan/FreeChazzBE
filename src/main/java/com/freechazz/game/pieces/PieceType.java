package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
public class PieceType {

    private String symbol = "X";// for pieceType

    private static final EPlayer TOPDOWN_PLAYER = EPlayer.P1;
    private long seed;

    private int lvl;

    private ActionMap actions = new ActionMap();

    private PieceType(){

    }

    public void perform(GameState board, Pos fromPos, Pos toPos){
        Piece piece = board.pieceAt(fromPos);
        boolean topDown = piece.getOwner()== TOPDOWN_PLAYER;
        Pos dPos = toPos.minus(fromPos);
        if(topDown) dPos.setY(-dPos.getY());
        actions.get(dPos).perform(board,fromPos,toPos);
    }



    /**
     * Computes the possible moves of the Piece in Position pos of the board and returns it.
     *
     */

    public ArrayList<Pos> computePossibleMoves(GameState board, Pos pos) {

        Piece piece1 = board.pieceAt(pos);
        boolean topDown = piece1.getOwner()== TOPDOWN_PLAYER;
        ArrayList<Pos> possibleMoves= new ArrayList<>();
        for (Pos p : actions.keySet()) {
            int dx = p.getX();
            int dy = p.getY();
            if(topDown)dy=-dy;//wenn spieler2 -> topdown

            Action action = actions.get(p);
            Pos toPos = new Pos(pos.getX() + dx, pos.getY() + dy);
            if (board.isOnboard(toPos) && action.checkCondition(board,pos,toPos)) {
                possibleMoves.add(toPos);
            }
        }
        return possibleMoves;
    }


    public boolean isPossibleMove(GameState state, Pos fromPos,Pos toPos){
        boolean topDown = state.pieceAt(fromPos).getOwner()== TOPDOWN_PLAYER;
        Action action;
        Pos dPos;
        if(topDown) {
            dPos = toPos.minus(fromPos).invertY();
            action = actions.get(dPos);
        }
        else {
            dPos = toPos.minus(fromPos);
            action = actions.get(dPos);
        }
        if(action==null) {
            throw new IllegalArgumentException("Action is null");
        }

        return state.isOnboard(toPos) && action.checkCondition(state,fromPos,toPos);

    }


    public ActionMap getActionMap() {
        return actions;
    }

    public void setActionMap(ActionMap actions) {
        this.actions = actions;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSerial(){
        String id = symbol+";";
        for(Pos p: actions.keySet()){
            id += actions.get(p).getSymbol() + "," + p.getX() + "," +p.getY() + ";";
        }
        return id;
    }


    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceType pieceType = (PieceType) o;
        for(Pos p: this.actions.keySet()){
            if(!p.isIn(pieceType.actions.keySet())){
                return false;
            }
        }
        for(Pos p: pieceType.actions.keySet()){
            if(!p.isIn(this.actions.keySet())){
                return false;
            }
        }
        return Objects.equals(symbol, pieceType.symbol);
    }

    public static PieceType getInstance(){
        return new PieceType();
    }

}
