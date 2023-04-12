package com.freechazz.game.actions.conditions.unitary;

import com.freechazz.game.actions.conditions.UnitaryCondition;
import com.freechazz.GameState;
import com.freechazz.game.core.Pos;

import java.util.Objects;

public class FreePostionCondition  extends UnitaryCondition {

    @Override
    public boolean check(GameState board, Pos pos) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(pos);
        return board.pieceAt(pos)==null;
    }
}
