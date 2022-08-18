package com.freechess.generators.piece.impl;

import com.freechess.game.actions.Actions;
import com.freechess.game.board.Position;
import com.freechess.game.actions.Action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenConfig {

    public HashMap<Position, Double> POSITION_WSK = new HashMap<>();


    public List<Double> CIRCLES_WSKS = Arrays.asList(0.01, 0.40, 0.4, 0.1, 0.19);

    public double MIRROR2_WSK = 0.4f;
    public double MIRROR4_WSK = 0.6f;
    public double MIRROR8_WSK = 0.3f;


    public Map<Action,Double> ACTION_WSKs = new HashMap<Action, Double>(){{
       put(Actions.MOVE_TO_ENEMY_POSITION,0.1);
        put(Actions.MOVE_TO_FREE_POSITION,0.1);
        put(Actions.LEGION_ATTACK_ACTION,0.25);
        put(Actions.SWAP_POSITIONS_ACTION,0.08);
        put(Actions.CROSS_ATTACK_ACTION,0.00);
        put(Actions.EXPLOSION_ATTACK_ACTION,0.00);
        put(Actions.RANGE_ATTACK_ACTION,0.02);
        put(Actions.ZOMBIE_ATTACK_ACTION,0.06);
        put(Actions.CONVERT_ACTION,0.00);
        put(Actions.MOVE_OR_ATTACK_ACTION,1.0);//Rest
    }};

    public List<Double> MOVE_PATTERN_NUMBER_WSKS = Arrays.asList(0.6,0.4);
    public List<Double> MOVE_PATTERN_TYPE_WSKS = Arrays.asList(0.3,0.2666,0.2666,0.1667,0.0,0.0);
    public List<Double> MOVE_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.2,0.2,0.2,0.2,0.2);

    public List<Double> RUSH_PATTERN_NUMBER_WSKS = Arrays.asList(0.98,0.02);

    public List<Double> RUSH_PATTERN_TYPE_WSKS = Arrays.asList(0.8,0.0666,0.0666,0.0666,0.0,0.0);

    public List<Double> RUSH_PATTERN_LENGTH_WSKS = Arrays.asList(0.0,0.0,0.5,0.5,0.0,0.0);

    public GenConfig(int lvl) {
        setLvl(lvl);
/*      addPositionWskAtY(2,Arrays.asList(0.1, 0.07, 0.06));
        addPositionWskAtY(1,Arrays.asList(0.2, 0.2, 0.07));
        addPositionWskAtY(0,Arrays.asList(0.0, 0.2, 0.1));*/

        /*
        3 |- - - -
        2 |- - x -
        1 |- - - -
        0 |- x x -
          ----------
           0 1 2 3
         */
    }

    public void setLvl(int lvl) {
        switch (lvl) {
            case 1:
                addPositionWskAtY(2,Arrays.asList(0.1, 0.07, 0.06));
                addPositionWskAtY(1,Arrays.asList(0.2, 0.2, 0.07));
                addPositionWskAtY(0,Arrays.asList(0.0, 0.2, 0.1));
                CIRCLES_WSKS = Arrays.asList(0.01, 0.35,0.54,0.1);

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


                ACTION_WSKs = new HashMap<Action, Double>(){{
                    put(Actions.MOVE_TO_ENEMY_POSITION,0.1);
                    put(Actions.MOVE_TO_FREE_POSITION,0.2);
                    put(Actions.LEGION_ATTACK_ACTION,0.00);
                    put(Actions.SWAP_POSITIONS_ACTION,0.08);
                    put(Actions.CROSS_ATTACK_ACTION,0.02);
                    put(Actions.EXPLOSION_ATTACK_ACTION,0.02);
                    put(Actions.RANGE_ATTACK_ACTION,0.04);
                    put(Actions.ZOMBIE_ATTACK_ACTION,0.00);
                    put(Actions.CONVERT_ACTION,0.02);
                    put(Actions.MOVE_OR_ATTACK_ACTION,1.0);//Rest
                }};
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

                ACTION_WSKs = new HashMap<Action, Double>(){{
                    put(Actions.MOVE_TO_ENEMY_POSITION,0.1);
                    put(Actions.MOVE_TO_FREE_POSITION,0.15);
                    put(Actions.LEGION_ATTACK_ACTION,0.00);
                    put(Actions.SWAP_POSITIONS_ACTION,0.06);
                    put(Actions.CROSS_ATTACK_ACTION,0.04);
                    put(Actions.EXPLOSION_ATTACK_ACTION,0.04);
                    put(Actions.RANGE_ATTACK_ACTION,0.07);
                    put(Actions.ZOMBIE_ATTACK_ACTION,0.00);
                    put(Actions.CONVERT_ACTION,0.04);
                    put(Actions.MOVE_OR_ATTACK_ACTION,1.0);//Rest
                }};

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

                ACTION_WSKs = new HashMap<Action, Double>(){{
                    put(Actions.MOVE_TO_ENEMY_POSITION,0.1);
                    put(Actions.MOVE_TO_FREE_POSITION,0.1);
                    put(Actions.LEGION_ATTACK_ACTION,0.00);
                    put(Actions.SWAP_POSITIONS_ACTION,0.04);
                    put(Actions.CROSS_ATTACK_ACTION,0.05);
                    put(Actions.EXPLOSION_ATTACK_ACTION,0.00);
                    put(Actions.RANGE_ATTACK_ACTION,0.07);
                    put(Actions.ZOMBIE_ATTACK_ACTION,0.00);
                    put(Actions.CONVERT_ACTION,0.08);
                    put(Actions.MOVE_OR_ATTACK_ACTION,1.0);//Rest
                }};

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
            POSITION_WSK.put(new Position(i, y), wsks.get(i));
        }
    }
}
