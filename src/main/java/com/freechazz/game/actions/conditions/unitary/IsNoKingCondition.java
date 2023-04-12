package com.freechazz.game.actions.conditions.unitary;

import com.freechazz.game.actions.conditions.UnitaryCondition;
import com.freechazz.game.pieces.Piece;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

import java.util.Objects;

public class IsNoKingCondition extends UnitaryCondition {

    @Override
    public boolean check(GameState board, Pos pos) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(pos);
        Piece p = board.pieceAt(pos);
        if(board.pieceAt(pos)==null){
            return false;
        }
        return !p.isKing();

    }
}
