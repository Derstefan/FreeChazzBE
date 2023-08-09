package com.freechazz.game.actions.acts.unitary.randomMove;

import com.freechazz.game.actions.acts.Acts;
import com.freechazz.game.actions.acts.ForceType;
import com.freechazz.game.actions.acts.PieceAct;
import com.freechazz.game.actions.acts.ForceActionAct;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.MoveSet;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RandomActionAct extends ForceActionAct {


    public RandomActionAct() {
    }

    public RandomActionAct(ForceType forceType) {
        super(forceType);
    }

    @Override
    public void performWithoutChain(GameOperator board, Pos pos) {
        if(!board.isOnboard(pos) || board.isFree(pos)){
            return;
        }
        Piece piece = board.pieceAt(pos);
        if(piece==null){
            log.info("piece is null at pos: "+pos);
            return;
        }
        board.computePossibleMoves(piece);
        Pos randomPos = getRandomMove(board,piece);
        if(randomPos==null)return;

        boolean topDown = piece.getOwner()== PieceType.TOPDOWN_PLAYER;
        Pos dPos = randomPos.minus(pos);
        if(topDown) dPos = dPos.invertY();
        if(piece.getPieceType().getActionMap().get(dPos)==null){
            log.info("action is null at dPos: "+dPos);
        }

        performChainAct(board,piece.getPieceType().getActionMap().get(dPos).getAct(),pos,randomPos);
    }

    private Pos getRandomMove(GameOperator operator, Piece piece){
        if (piece.getMoveSet().getPossibleMoves().size()==0)return null;
        Random random = new Random(piece.getPos().getX()*piece.getPos().getY() + operator.getAllPieces().size());
        return piece.getMoveSet().getPossibleMoves().get((int)(random.nextDouble()*piece.getMoveSet().getPossibleMoves().size()));
    }

}
