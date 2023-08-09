package com.freechazz.generators.action;

import com.freechazz.game.actions.Action;
import com.freechazz.game.actions.Actions;
import com.freechazz.game.actions.acts.*;
import com.freechazz.game.actions.acts.basic.DestroyPieceAct;
import com.freechazz.game.actions.acts.basic.MoveOrAttackAct;
import com.freechazz.game.actions.acts.basic.RangeAttackAct;
import com.freechazz.game.actions.acts.unitary.actChain.RangedAttackCrossAct;
import com.freechazz.game.actions.acts.unitary.randomMove.RandomActionAct;
import com.freechazz.game.actions.acts.unitary.randomMove.RandomActionPrefAttackAct;
import com.freechazz.game.actions.connector.Connector;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ActionGenerator {



    private static final float ACT_CHAIN_TO_POS_PROB = 0.5f;
    private Random random;


    //pool of actions
    public static Map<Action, Double> ACTIONs = new HashMap<Action, Double>(){{
        put(Actions.MOVE_OR_ATTACK_ACTION,0.5);
        put(Actions.RANGE_ATTACK_ACTION,0.5);
    }};



    // ------------------------------ ACTS ----------------------------
    public static Map<PosAct, Double> FREE_POS_ACTS = new HashMap<PosAct, Double>(){{
        put(Acts.EXPLOSION_ACT,1.0);
    }};

    public static Map<PosAct, Double> ENEMY_ACTS = new HashMap<PosAct, Double>(){{
        put(Acts.DESTROY_PIECE_ACT,0.4);
        put(new RandomActionAct(ForceType.ENEMY_PIECE),0.6);
    }};

    public static Map<PosAct, Double> OWN_ACTS = new HashMap<PosAct, Double>(){{
        put(new RangedAttackCrossAct(ForceType.OWN_PIECE),0.5);
        put(new RandomActionPrefAttackAct(ForceType.OWN_PIECE),0.5);
    }};

    public static Map<PosAct, Double> ACTS = new HashMap<PosAct, Double>(){{
       putAll(FREE_POS_ACTS);
       putAll(ENEMY_ACTS);
       putAll(OWN_ACTS);
    }};


    // --------------- POSETS --------------------
    public static Map<PosSet,Double> UNITARY_POS_SETs = new HashMap<PosSet, Double>(){{
        put(PosSet.pos,0.5);
        put(PosSet.posAround,0.5);
    }};

    public static Map<PosSet,Double> BINARY_POS_SETs = new HashMap<PosSet, Double>(){{
        put(PosSet.toPos,0.5);
        put(PosSet.toPosAround,0.3);
        put(PosSet.fromPos,0.1);
        put(PosSet.fromPosAround,0.1);
    }};



    // ------------------------------------------
    public ActionGenerator(long seed){
        random = new Random(seed);
    }
    public Action generate(int depth){

        Action action = getRandomInitialAction();

        generateNextStep(action.getAct(),depth);
        action.setSymbol(depth+"");
        return action;
    }


    private void generateNextStep(Act prevAct, int depth){

        if(depth == 0){
            return;
        }

        PosSet posSet = getRandomPosSet(prevAct);
        PosAct nextAct = generateUnitaryAct(prevAct,posSet);

        if(prevAct instanceof ForceActionAct && random.nextDouble() < ACT_CHAIN_TO_POS_PROB){
            Connector chainConnector = new Connector();
            chainConnector.addChain(posSet,nextAct);
            ((ForceActionAct) prevAct).setChainConnector(chainConnector);
            generateNextStep(nextAct,depth-1);
            return;
        }

        Connector connector = prevAct.createConnector();
        connector.addChain(posSet,nextAct);
        generateNextStep(nextAct,depth-1);
    }


    private PosSet getRandomPosSet(Act act){
        if(act instanceof PieceAct){
            return getRandomBinaryPosSet();
        }
        return getRandomUnitaryPosSet();

    }

    private  PosSet getRandomUnitaryPosSet(){
        double randomValue = random.nextDouble();
        for(PosSet posSet: UNITARY_POS_SETs.keySet()){
            randomValue -= UNITARY_POS_SETs.get(posSet);
            if(randomValue <= 0){
                return posSet;
            }
        }
        throw new RuntimeException("PosSet not found");
    }

    private  PosSet getRandomBinaryPosSet(){
        double randomValue = random.nextDouble();
        for(PosSet posSet: BINARY_POS_SETs.keySet()){
            randomValue -= BINARY_POS_SETs.get(posSet);
            if(randomValue <= 0){
                return posSet;
            }
        }
        throw new RuntimeException("PosSet not found");
    }

    private PosAct generateUnitaryAct(Act prevAct, PosSet posSet){

        if(prevAct instanceof DestroyPieceAct){
            if(posSet == PosSet.pos){
                return generateFreePosAct();
            }
        }

        if(prevAct instanceof RangeAttackAct ){
            if(posSet == PosSet.toPos){
                return generateFreePosAct();
            }
            if(posSet == PosSet.fromPos){
                return generateOwnPosAct();
            }
        }

        if(prevAct instanceof MoveOrAttackAct){
            if(posSet == PosSet.toPos){
                return generateOwnPosAct();
            }
            if(posSet == PosSet.fromPos){
                return generateFreePosAct();
            }
        }





        double randomValue = random.nextDouble()*3.0;
        for(PosAct act: ACTS.keySet()){
            randomValue -= ACTS.get(act);
            if(randomValue <= 0){
                return act.copy();
            }
        }
        throw new RuntimeException("Act not found");
    }

    private PosAct generateFreePosAct(){
        double randomValue = random.nextDouble();
        for(PosAct act: FREE_POS_ACTS.keySet()){
            randomValue -= FREE_POS_ACTS.get(act);
            if(randomValue <= 0){
                return act.copy();
            }
        }
        throw new RuntimeException("Act not found");
    }

    private PosAct generateEnemyPosAct(){
        double randomValue = random.nextDouble();
        for(PosAct act: ENEMY_ACTS.keySet()){
            randomValue -= ENEMY_ACTS.get(act);
            if(randomValue <= 0){
                return act.copy();
            }
        }
        throw new RuntimeException("Act not found");
    }

    private PosAct generateOwnPosAct(){
        double randomValue = random.nextDouble();
        for(PosAct act: OWN_ACTS.keySet()){
            randomValue -= OWN_ACTS.get(act);
            if(randomValue <= 0){
                return act.copy();
            }
        }
        throw new RuntimeException("Act not found");
    }



    private Action getRandomInitialAction(){

        double randomValue = random.nextDouble();
        for(Action action: ACTIONs.keySet()){
            randomValue -= ACTIONs.get(action);
            if(randomValue <= 0){
                return action.copy();
            }
        }
        throw new RuntimeException("Action not found");
    }
}
