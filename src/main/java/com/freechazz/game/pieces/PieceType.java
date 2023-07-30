package com.freechazz.game.pieces;

import com.freechazz.game.actions.Action;
import com.freechazz.game.state.GameOperator;
import com.freechazz.game.core.Pos;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class PieceType {




    private PieceTypeId pieceTypeId;

    private String symbol = "X";// for pieceType

    private static final EPlayer TOPDOWN_PLAYER = EPlayer.P1;



    private ActionMap actions = new ActionMap();

    private PieceType(int lvl, long seed, String generatorVersion){
        this.pieceTypeId = new PieceTypeId(seed,lvl,generatorVersion);
    }

    public void perform(GameOperator state, Pos fromPos, Pos toPos){
        Piece piece = state.pieceAt(fromPos);
        boolean topDown = piece.getOwner()== TOPDOWN_PLAYER;
        Pos dPos = toPos.minus(fromPos);
        if(topDown) dPos.setY(-dPos.getY());
        actions.get(dPos).perform(state,fromPos,toPos);
    }

    //performs an action but without triggering a chain reaction
    public void performWithoutChain(GameOperator state, Pos fromPos, Pos toPos){
        Piece piece = state.pieceAt(fromPos);
        boolean topDown = piece.getOwner()== TOPDOWN_PLAYER;
        Pos dPos = toPos.minus(fromPos);
        if(topDown) dPos.setY(-dPos.getY());
        actions.get(dPos).performWithoutChain(state,fromPos,toPos);
    }



    /**
     * Computes the possible moves of the Piece in Position pos of the board and returns it.
     *
     */

    public MoveSet computePossibleMoves(GameOperator board, Pos pos) {

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
                possibleMoves.add(new Pos(toPos,action.getSymbol()));
            }
        }
        return new MoveSet(possibleMoves);
    }


    public boolean isPossibleMove(GameOperator state, Pos fromPos, Pos toPos){
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
            log.warn("Action is null");
            return false;
        }

        return state.isOnboard(toPos) && action.checkCondition(state,fromPos,toPos);

    }

    public PieceTypeId getPieceTypeId() {
        return pieceTypeId;
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





    public static PieceType getInstance(int lvl, long seed, String generatorVersion){
        return new PieceType(lvl,seed,generatorVersion);
    }

}
