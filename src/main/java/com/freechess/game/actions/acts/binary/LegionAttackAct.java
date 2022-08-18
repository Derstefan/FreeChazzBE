package com.freechess.game.actions.acts.binary;

import com.freechess.game.actions.Actions;
import com.freechess.game.actions.acts.Act;
import com.freechess.game.board.Board;
import com.freechess.game.board.Position;
import com.freechess.game.pieces.Piece;
import com.freechess.game.player.EPlayer;

public class LegionAttackAct extends Act {
    @Override
    public void perform(Board board, Position pos1, Position pos2) throws Exception {
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();
        Position diff = pos2.minus(pos1);

        for(Piece p: board.getAllPiecesFrom(owner)){
            if(p.getSymbol().equals(piece.getSymbol()) && board.isOnboard(p.getPosition().plus(diff))){
                piece.getPieceType().performAction(board,p.getPosition(),p.getPosition().plus(diff), Actions.MOVE_OR_ATTACK_ACTION);
            }
        }
    }
}
