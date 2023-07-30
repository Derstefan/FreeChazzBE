package com.freechazz.game.actions.acts.unitary.randomMove;

import com.freechazz.game.actions.acts.ForceActionAct;
import com.freechazz.game.actions.acts.ForceType;
import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;

import java.util.ArrayList;

public class RandomActionPrefAttackAct extends ForceActionAct {


    public RandomActionPrefAttackAct(ForceType forceType) {
        super(forceType);
    }

    public RandomActionPrefAttackAct() {
        super();
    }

    @Override
    public void performWithoutChain(GameOperator operator, Pos pos) {
        if(!operator.isFree(pos)){
            return;
        }
        Piece piece = operator.pieceAt(pos);
        operator.computePossibleMoves(piece);
        Pos randomPos = getRandomMovePrefAttack(operator,piece);
        piece.getPieceType().performWithoutChain(operator,pos,randomPos);
        this.performChainAct(operator,piece.getPieceType().getActionMap().get(randomPos).getAct(),pos,randomPos);
    }

    private Pos getRandomMovePrefAttack(GameOperator operator, Piece piece){
        ArrayList<Pos> possibleMoves = piece.getMoveSet().getPossibleMoves();

        ArrayList<Pos> possibleAttacks = new ArrayList<>();
        for(Pos pos : possibleMoves){
            if(operator.isFree(pos))continue;
            Piece target = operator.pieceAt(pos);
            if(operator.areEnemys(piece,target)){
                possibleAttacks.add(pos);
            }
        }
        //prefer random attacks
        if(possibleAttacks.size()>0){
            return possibleAttacks.get((int)(Math.random()*possibleAttacks.size()));
        }
        //move randomly instead
        return possibleMoves.get((int)(Math.random()*possibleMoves.size()));
    }

}
