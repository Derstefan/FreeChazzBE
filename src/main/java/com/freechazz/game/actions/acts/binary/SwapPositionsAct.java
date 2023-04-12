package com.freechazz.game.actions.acts.binary;

import com.freechazz.game.actions.acts.Act;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.Piece;
import com.freechazz.game.core.EPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapPositionsAct extends Act {
    @Override
    public void perform(GameState board, Pos pos1, Pos pos2) {
        Piece piece = board.pieceAt(pos1);
        EPlayer owner = piece.getOwner();

        Piece targetPiece = board.pieceAt(pos2);
        if (targetPiece != null && piece!=null) {
            if (targetPiece.getOwner().equals(owner)) {
                board.swapPieces(pos1,pos2);
            }
        }
        //log.info(this.getClass().getSimpleName() + " performed.");
    }
}
