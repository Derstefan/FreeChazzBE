package com.freechazz.game.actions.acts.unitary.randomMove;

import com.freechazz.game.actions.acts.ForceActionAct;
import com.freechazz.game.actions.acts.ForceType;
import com.freechazz.game.actions.acts.PosAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
public class RandomActionPrefFleeAct extends ForceActionAct {


    public RandomActionPrefFleeAct() {
    }

    public RandomActionPrefFleeAct(ForceType forceType) {
        super(forceType);
    }

    @Override
    public void performWithoutChain(GameOperator operator, Pos pos) {
        if(!operator.isOnboard(pos) || operator.isFree(pos)){
            return;
        }
        Piece piece = operator.pieceAt(pos);
        if(piece==null){
            log.info("Piece is null at pos: "+pos);
            return;
        }
        operator.computePossibleMoves(piece);
        Pos randomPos = getRandomMovePrefAttack(operator,piece);
        if(randomPos==null)return;

        boolean topDown = piece.getOwner()== PieceType.TOPDOWN_PLAYER;
        Pos dPos = randomPos.minus(pos);
        if(topDown) dPos = dPos.invertY();


        if(piece.getPieceType().getActionMap().get(dPos)==null){
            log.info("action is null at dPos: "+dPos);
        }

        performChainAct(operator,piece.getPieceType().getActionMap().get(dPos).getAct(),pos,randomPos);
    }

    private Pos getRandomMovePrefAttack(GameOperator operator, Piece piece){

        Random random = new Random(piece.getPos().getX()*piece.getPos().getY() + operator.getAllPieces().size());
        ArrayList<Pos> possibleMoves = piece.getMoveSet().getPossibleMoves();

        if (possibleMoves.size()==0)return null;
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



        return possibleFleeing.get((int)(random.nextDouble()*possibleMoves.size()));
    }

}
