package com.freechazz.game.actions.acts.unitary.randomMove;

import com.freechazz.game.actions.acts.ForceActionAct;
import com.freechazz.game.actions.acts.ForceType;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;

import java.util.ArrayList;

public class RandomActionPrefFleeAct extends ForceActionAct {


    public RandomActionPrefFleeAct() {
    }

    public RandomActionPrefFleeAct(ForceType forceType) {
        super(forceType);
    }

    @Override
    public void performWithoutChain(GameOperator operator, Pos pos) {
        if(!operator.isFree(pos)){
            return;
        }
        Piece piece = operator.pieceAt(pos);
        operator.computePossibleMoves(piece);
        Pos randomPos = getRandomMovePrefAttack(operator,piece);
        this.performChainAct(operator,piece.getPieceType().getActionMap().get(randomPos).getAct(),pos,randomPos);
    }

    private Pos getRandomMovePrefAttack(GameOperator operator, Piece piece){
        ArrayList<Pos> possibleMoves = piece.getMoveSet().getPossibleMoves();
        //find maxDistance to enemy
        int maxDistance = 0;
        for (   Pos pos : possibleMoves) {
            int distance = operator.distanceToEnemy(piece.getOwner().getOpponent(),pos);
            if(distance>maxDistance){
                maxDistance = distance;
            }
        }
        //find all possible moves with maxDistance to enemy
        ArrayList<Pos> possibleFleeing = new ArrayList<>();
        for(Pos pos : possibleMoves){
                int distance = operator.distanceToEnemy(piece.getOwner().getOpponent(),pos);
                if(distance==maxDistance){
                    possibleFleeing.add(pos);
                }
        }

        return possibleFleeing.get((int)(Math.random()*possibleMoves.size()));
    }

}
