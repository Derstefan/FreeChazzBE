package com.freechazz.game.actions.conditions;

import com.freechazz.game.actions.conditions.binary.ClearPathCondition;
import com.freechazz.game.actions.conditions.binary.EnemyAtPostionCondition;
import com.freechazz.game.actions.conditions.binary.FriendAtPostionCondition;
import com.freechazz.game.actions.conditions.operations.TrivCondition;
import com.freechazz.game.actions.conditions.unitary.FreePostionCondition;
import com.freechazz.game.actions.conditions.unitary.IsNoKingCondition;

public class Conditions {

    public final static FreePostionCondition FREE_POSITION = new FreePostionCondition();

    public final static EnemyAtPostionCondition ENEMY_AT_POSITION = new EnemyAtPostionCondition();

    public final static FriendAtPostionCondition FRIEND_AT_POSITION = new FriendAtPostionCondition();

    public final static ClearPathCondition CLEAR_PATH = new ClearPathCondition();

    public final static IsNoKingCondition IS_NO_KING = new IsNoKingCondition();

    public final static TrivCondition ALWAYS = new TrivCondition();

}
