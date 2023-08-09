package com.freechazz.game.actions.acts;

import com.freechazz.game.actions.connector.Connector;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.state.GameOperator;
import lombok.extern.slf4j.Slf4j;


/**
 * A PosAct that forces a Piece to do some of his Actions
 */
@Slf4j
public abstract class ForceActionAct extends PosAct {

    private Connector chainConnector;

    private ForceType forceType; //determines if the force is applied to enemy pieces, own pieces or any piece

    public ForceActionAct(){
        this.forceType = ForceType.ANY_PIECE;
    }

    public ForceActionAct(ForceType forceType){
        this.forceType = forceType;
    }


    public void performChainAct(GameOperator board, PieceAct pieceAct, Pos fromPos, Pos toPos){
        Piece piece = board.pieceAt(fromPos);
        if(piece==null){
            return;
        }
        if(ForceType.ENEMY_PIECE.equals(forceType) && piece.getOwner().equals(board.getPlayersTurn())){
            return;
        }
        if(ForceType.OWN_PIECE.equals(forceType) && !piece.getOwner().equals(board.getPlayersTurn())){
            return;
        }
        pieceAct.copy().setConnector(chainConnector);
        pieceAct.performWithoutChain(board,fromPos,toPos);
    }

    public void setChainConnector(Connector chainConnector) {
        this.chainConnector = chainConnector;
    }



    public String toString(){
        if(chainConnector!=null){
            return this.getClass().getSimpleName() + " ~" + chainConnector.toString();
        }
        if(getConnector()!=null){
            return this.getClass().getSimpleName() + " " + this.getConnector().toString();
        }
        return this.getClass().getSimpleName();
    }
}



