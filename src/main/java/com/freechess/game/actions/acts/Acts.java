package com.freechess.game.actions.acts;

import com.freechess.game.actions.acts.binary.*;

public class Acts {

    public static final MoveOrAttackAct MOVE_OR_ATTACK = new MoveOrAttackAct();

    public static final SwapPositionsAct SWAP_POSITIONS = new SwapPositionsAct();

    public static final RushAct RUSH_ACT = new RushAct();

    public static final CrossAttackAct CROSS_ATTACK_ACT = new CrossAttackAct();

    public static final ExplosionAct EXPLOSION_ACT = new ExplosionAct();

    public static final ZombieAttackAct ZOMBIE_ATTACK_ACT = new ZombieAttackAct();
}
