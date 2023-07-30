package com.freechazz.game.actions.acts.unitary.randomMove;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.ForceType;
import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.actions.acts.ForceActionAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.MoveSet;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;

public class RandomActionAct extends ForceActionAct {


    public RandomActionAct() {
    }

    public RandomActionAct(ForceType forceType) {
        super(forceType);
    }

    @Override
    public void performWithoutChain(GameOperator board, Pos pos) {
        if(!board.isFree(pos)){
            return;
        }
        Piece piece = board.pieceAt(pos);
        board.computePossibleMoves(piece);
        Pos randomPos = getRandomMove(piece.getMoveSet());


        this.performChainAct(board,piece.getPieceType().getActionMap().get(randomPos).getAct(),pos,randomPos);
    }

    private Pos getRandomMove(MoveSet moveSet){
        return moveSet.getPossibleMoves().get((int)(Math.random()*moveSet.getPossibleMoves().size()));
    }

}
