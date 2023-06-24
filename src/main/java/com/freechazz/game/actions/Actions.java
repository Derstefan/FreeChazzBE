package com.freechazz.game.actions;






import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

import static com.freechazz.game.actions.acts.Acts.*;
import static com.freechazz.game.actions.conditions.Conditions.*;

public class Actions {

    public static Action MOVE_TO_FREE_POSITION = new Action(FREE_POSITION, MOVE_OR_ATTACK,"F");

    public static Action MOVE_TO_ENEMY_POSITION = new Action(ENEMY_AT_POSITION, MOVE_OR_ATTACK,"E");

    public static Action MOVE_OR_ATTACK_ACTION = new Action(ENEMY_AT_POSITION.OR(FREE_POSITION), MOVE_OR_ATTACK,"X");

    public static Action WALK_AND_MOVE_OR_ATTACK = new Action(CLEAR_PATH.AND(ENEMY_AT_POSITION.OR(FREE_POSITION)), MOVE_OR_ATTACK,"M");

    public static Action SWAP_POSITIONS_ACTION = new Action(FRIEND_AT_POSITION.AND(FREE_POSITION.NOT()), SWAP_POSITIONS,"S");

    public static Action RUSH_ACTION = new Action(ALWAYS, RUSH_ACT,"R");

    public static Action CROSS_ATTACK_ACTION = new Action(FREE_POSITION,CROSS_ATTACK_ACT,"C");

    public static Action EXPLOSION_ATTACK_ACTION = new Action(FREE_POSITION, EXPLODE_ACT,"Y");

    public static Action ZOMBIE_ATTACK_ACTION = new Action(ENEMY_AT_POSITION,ZOMBIE_ATTACK_ACT,"Z");

    public static Action RANGE_ATTACK_ACTION = new Action(ENEMY_AT_POSITION.AND(CLEAR_PATH),RANGE_ATTACK_ACT,"A");

    public static Action CONVERT_ACTION = new Action(ENEMY_AT_POSITION.AND(IS_NO_KING),CONVERT_ACT,"Q");

    public static Action LEGION_ATTACK_ACTION = new Action(ENEMY_AT_POSITION.OR(FREE_POSITION),LEGION_ATTACK_ACT,"L");




        public static List<Action> getAllStaticFields() {
            List<Action> staticFields = new ArrayList<>();

            try {
                Class<?> actionsClass = Actions.class;

                Field[] fields = actionsClass.getDeclaredFields();
                for (Field field : fields) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                        Object fieldValue = field.get(null);
                        if (fieldValue instanceof Action) {
                            Action action = (Action) fieldValue;
                            staticFields.add(action);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return staticFields;
        }


        public static Action getActionBySymbol(String symbol){
            for (Action action : getAllStaticFields()) {
                if (action.getSymbol().equals(symbol)){
                    return action;
                }
            }
            return null;
    }




}
