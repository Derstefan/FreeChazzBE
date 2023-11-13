package com.freechazz.generators.piece;

import com.freechazz.game.actions.Actions;
import com.freechazz.game.core.Pos;
import com.freechazz.game.actions.Action;

import java.util.*;

public class GenConfig {



    //list of actions with wsks for each lvl
    public Map<Action, List<Double>> ACTION_WSKs = new HashMap<Action, List<Double>>(){{
        put(Actions.MOVE_TO_ENEMY_POSITION,
                Arrays.asList(0.1,0.1,0.1,0.1,0.1));
        put(Actions.MOVE_TO_FREE_POSITION,
                Arrays.asList(0.1,0.1,0.2,0.15,0.1));
        put(Actions.LEGION_ATTACK_ACTION,
                Arrays.asList(0.0,0.0,0.2,0.2,0.1));
        put(Actions.SWAP_POSITIONS_ACTION,
                Arrays.asList(0.08,0.08,0.2,0.06,0.04));
        put(Actions.CROSS_ATTACK_ACTION,
                Arrays.asList(0.0,0.0,0.02,0.04,0.05));
        put(Actions.EXPLOSION_ATTACK_ACTION,
                Arrays.asList(0.0,0.0,0.02,0.04,0.0));
        put(Actions.RANGE_ATTACK_ACTION,
                Arrays.asList(0.02,0.02,0.04,0.07,0.07));
        put(Actions.ZOMBIE_ATTACK_ACTION,
                Arrays.asList(0.06,0.06,0.0,0.0,0.0));
        put(Actions.CONVERT_ACTION,
                Arrays.asList(0.0,0.0,0.02,0.04,0.08));
        put(Actions.GENERATE_ACTION,
                Arrays.asList(0.0,0.0,0.3,0.3,0.4));
        put(Actions.MOVE_OR_ATTACK_ACTION,
                Arrays.asList(1.0,1.0,1.0,1.0,1.0));//Rest
    }};
    public HashMap<Pos, Double> POSITION_WSK = new HashMap<>();
    public List<Double> CIRCLES_WSKS = Arrays.asList(0.01, 0.40, 0.4, 0.1, 0.19);
    public double MIRROR2_WSK = 0.4f;
    public double MIRROR4_WSK = 0.6f;
    public double MIRROR8_WSK = 0.3f;
    public List<Double> MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.6,0.4);
    public List<Double> MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.3,0.2666,0.2666,0.169,0.0,0.0);
    public List<Double> MOVE_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.2,0.2,0.2,0.2,0.2);
    public List<Double> RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.98,0.02);
    public List<Double> RUSH_PATTERN_TYPE_WSKS = Arrays.asList(0.8,0.0666,0.0666,0.0669,0.0,0.0);
    public List<Double> RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.5,0.5,0.0,0.0);

    public GenConfig(int lvl) {
        setLvl(lvl);
    }



    /*
3 |- - - -
2 |- - x -
1 |- - - -
0 |- x x -
  ----------
   0 1 2 3
 */
    public void setLvl(int lvl) {
        switch (lvl) {
            case 1:
                addPositionWskAtY(2,Arrays.asList(0.12, 0.1, 0.06));
                addPositionWskAtY(1,Arrays.asList(0.2, 0.2, 0.07));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.2, 0.05));
                CIRCLES_WSKS = Arrays.asList(0.01, 0.25,0.64,0.1);

                MIRROR2_WSK = 0.6f;
                MIRROR4_WSK = 0.3f;
                MIRROR8_WSK = 0.2f;

                break;
            case 2:
                addPositionWskAtY(2,Arrays.asList(0.15, 0.09, 0.07));
                addPositionWskAtY(1,Arrays.asList(0.15, 0.15, 0.09));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.15, 0.15));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0,0.5,0.4,0.1);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                break;
            case 3:
                addPositionWskAtY(3,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(2,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(1,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.066, 0.066, 0.066));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0, 0.8,0.2);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                break;
            case 4:
                addPositionWskAtY(3,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(2,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(1,Arrays.asList(0.066, 0.066, 0.066, 0.066));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.066, 0.066, 0.066));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0, 0.0, 0.7, 0.3);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;


                MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.2,0.4,0.4);
                MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.3,0.2,0.2,0.1,0.1,0.1);

                RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.7,0.2,0.1);
                RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.4,0.3,0.3,0.0);

            case 5:
                addPositionWskAtY(4, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(3, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(2, Arrays.asList(0.04, 0.04, 0.05, 0.04, 0.04));
                addPositionWskAtY(1, Arrays.asList(0.05, 0.05, 0.04, 0.04, 0.04));
                addPositionWskAtY(0, Arrays.asList(0.0, 0.05, 0.04, 0.04, 0.04));
                CIRCLES_WSKS = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.2, 0.6, 0.2);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

                MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.2,0.3,0.3,0.2);
                MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.0,0.0,0.3,0.3,0.2,0.2);

                RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.5,0.3,0.2);
                RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.3,0.3,0.2,0.2);
                break;
            default:
                addPositionWskAtY(4, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(3, Arrays.asList(0.04, 0.04, 0.04, 0.04, 0.04));
                addPositionWskAtY(2, Arrays.asList(0.04, 0.04, 0.05, 0.04, 0.04));
                addPositionWskAtY(1, Arrays.asList(0.05, 0.05, 0.04, 0.04, 0.04));
                addPositionWskAtY(0, Arrays.asList(0.0, 0.05, 0.04, 0.04, 0.04));
                CIRCLES_WSKS = Arrays.asList(0.05, 0.50, 0.2, 0.1, 0.15);

                MIRROR2_WSK = 0.4f;
                MIRROR4_WSK = 0.6f;
                MIRROR8_WSK = 0.3f;

        }
    }


    private void addPositionWskAtY(int y, List<Double> wsks) {
        for (int i = 0; i < wsks.size(); i++) {
            POSITION_WSK.put(new Pos(i, y), wsks.get(i));
        }
    }
}
