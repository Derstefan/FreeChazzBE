package com.freechess.game.actions.acts;

import com.freechess.game.actions.acts.basic.DestroyPieceAct;
import com.freechess.game.actions.acts.basic.MoveAct;
import com.freechess.game.actions.acts.basic.MoveOrAttackAct;
import com.freechess.game.actions.acts.basic.RangeAttackAct;
import com.freechess.game.actions.acts.binary.*;
import com.freechess.game.actions.acts.unitary.ExplosionAct;
import com.freechess.game.actions.acts.unitary.TeleportInverseAct;

public class Acts {

    public static final DestroyPieceAct DESTROY_PIECE_ACT = new DestroyPieceAct();
    public static final MoveAct MOVE_ACT = new MoveAct();



    public static final ExplosionAct EXPLOSION_ACT = new ExplosionAct();

    public static final MoveOrAttackAct MOVE_OR_ATTACK = new MoveOrAttackAct();

    public static final SwapPositionsAct SWAP_POSITIONS = new SwapPositionsAct();

    public static final RushAct RUSH_ACT = new RushAct();

    public static final CrossAttackAct CROSS_ATTACK_ACT = new CrossAttackAct();

    public static final ExplodeAct EXPLODE_ACT = new ExplodeAct();

    public static final ZombieAttackAct ZOMBIE_ATTACK_ACT = new ZombieAttackAct();

    public static final RangeAttackAct RANGE_ATTACK_ACT = new RangeAttackAct();

    public static final ConvertAct CONVERT_ACT = new ConvertAct();

    public static final LegionAttackAct LEGION_ATTACK_ACT = new LegionAttackAct();

    public static final TeleportInverseAct TELEPORT_INVERSE_ACT = new TeleportInverseAct();
}
