package com.freechazz.game.actions.conditions.binary;

import com.freechazz.game.actions.conditions.Condition;
import com.freechazz.game.state.GameState;
import com.freechazz.game.core.Pos;

import java.util.Objects;

public class FriendAtPostionCondition extends Condition {

    @Override
    public boolean check(GameState board, Pos pos1, Pos pos2) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(pos1);
        Objects.requireNonNull(pos2);
        return !board.areEnemys(board.pieceAt(pos1),board.pieceAt(pos2));
    }
}
