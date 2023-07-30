package com.freechazz.generators.piece;

import com.freechazz.game.actions.Action;
import com.freechazz.game.actions.Actions;
import com.freechazz.game.pieces.ActionMap;
import com.freechazz.game.pieces.PieceType;
import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.PieceTypeBuilder;
import com.freechazz.game.pieces.PieceTypeId;
import com.freechazz.generators.GeneratorHelper;
import com.freechazz.generators.action.ActionGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class PieceTypeGenerator {

    private GenConfig gc;
    private Random rand;

    private int lvl;

    public PieceType generate(int lvl, long seed,String generatorVersion){
        rand = new Random(seed);
        gc = new GenConfig(lvl);
        this.lvl = lvl;
        ActionMap map = generateActions();
        PieceType piece = new PieceTypeBuilder(lvl,seed,generatorVersion).actions(map).build();
        return piece;
    }

    public PieceType generate(PieceTypeId pieceTypeId){
        return generate(pieceTypeId.getLvl(),pieceTypeId.getSeed(),pieceTypeId.getGeneratorVersion());
    }


    private ActionMap generateActions() {
        ActionMap actions = new ActionMap();

        generateJumpActions(actions,rand);
        generateWalkActions(actions);
        generateRushActions(actions);

        return actions;
    }



    // array as list... input ?
    private int dice(List<Double> wsks) {
        return GeneratorHelper.dice(wsks,rand);
    }

    private Pos dicePosition(){
        ArrayList<Pos> posList = new ArrayList<>(gc.POSITION_WSK.keySet());
        Collections.sort(posList);
        double wsk = rand.nextDouble();
        for (int i = 0; i < posList.size(); i++) {
            if (wsk <= gc.POSITION_WSK.get(posList.get(i))) {
                return posList.get(i);
            }
            wsk -= gc.POSITION_WSK.get(posList.get(i));
        }
        return new Pos(0,0);
    }




//------------ jump actions ---------------------------------------------------

    private void  generateJumpActions(ActionMap map, Random r){
        int circleNumber = dice(gc.CIRCLES_WSKS);
        for (int i = 0; i < circleNumber; i++) {
            //int x = dice(gc.DISTANCE_WSKS);
            //int y = dice(gc.DISTANCE_WSKS);

            Pos p = dicePosition();

            int x = p.getX();
            int y = p.getY();

            Action type = generateActionType();
            if (!(x == 0 && y == 0)) {
                double mirrorWsk = r.nextDouble();
                if (mirrorWsk <= gc.MIRROR2_WSK) {
                    addToMap(map,getMirrors2(new Pos(x, y)), type);
                } else if (mirrorWsk - gc.MIRROR2_WSK <= gc.MIRROR4_WSK) {
                    addToMap(map,getMirrors4(new Pos(x, y)), type);
                } else {
                    addToMap(map,getMirrors8(new Pos(x, y)), type);
                }
            } else {
                i--; // TODO: Remove this
            }
        }
    }

    private Action generateActionType() {
        double wsk = rand.nextDouble();
        ArrayList<Action> actions = new ArrayList<>(gc.ACTION_WSKs.keySet());
        actions.remove(Actions.MOVE_OR_ATTACK_ACTION);
        for(Action a:actions){
            if(wsk<gc.ACTION_WSKs.get(a).get(lvl-1)){
                if(a==Actions.GENERATE_ACTION){
                    ActionGenerator ag = new ActionGenerator(rand.nextLong());
                    return ag.generate(5);
                }
                return a;
            }
            wsk-=gc.ACTION_WSKs.get(a).get(lvl-1);
        }
        return Actions.MOVE_OR_ATTACK_ACTION;
    }



    private void addToMap(ActionMap map, Set<Pos> positions, Action action) {
        for (Pos pos : positions) {
            map.put(pos, action);
        }
    }

    private Set<Pos> getMirrors8(Pos pos) {
        Set<Pos> set = new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        set.add(new Pos(x, y));
        set.add(new Pos(-x, y));
        set.add(new Pos(x, -y));
        set.add(new Pos(-x, -y));
        set.add(new Pos(y, x));
        set.add(new Pos(y, -x));
        set.add(new Pos(-y, x));
        set.add(new Pos(-y, -x));
        return set;
    }

    private Set<Pos> getMirrors4(Pos pos) {
        Set<Pos> set = new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        set.add(new Pos(x, y));
        set.add(new Pos(-x, y));
        set.add(new Pos(x, -y));
        set.add(new Pos(-x, -y));
        return set;
    }

    private Set<Pos> getMirrors2(Pos pos) {
        Set<Pos> list = new HashSet<>();
        int x = pos.getX();
        int y = pos.getY();
        list.add(new Pos(x, y));
        list.add(new Pos(-x, y));
        return list;
    }

//------------ line actions ------------------------------------------------------

    private void generateWalkActions(ActionMap actions){
        int number = dice(gc.MOVE_PATTERN_NUMBER_WSKS);
        for(int i=0;i<number;i++){

            EWalkType type = EWalkType.values()[dice(gc.MOVE_PATTERN_TYPE_WSKS)];
            int length = dice(gc.MOVE_PATTERN_LENGTH_WSKS);
            createLineActions(actions,type,length,Actions.WALK_AND_MOVE_OR_ATTACK);
        }
    }

    private void generateRushActions(ActionMap actions){
        int number = dice(gc.RUSH_PATTERN_NUMBER_WSKS);
        for(int i=0;i<number;i++){

            EWalkType type = EWalkType.values()[dice(gc.RUSH_PATTERN_TYPE_WSKS)];
            int length = dice(gc.RUSH_PATTERN_LENGTH_WSKS);
            createLineActions(actions,type,length,Actions.RUSH_ACTION);
        }
    }

    private void createLineActions(ActionMap actions, EWalkType type, int length, Action action){
        if(length<1 || length>8){
            return;
        }
        for(Pos pos:type.getdPos()){
           // System.out.println("diagonal to: " + pos.getX()*length+" , "+ pos.getY()*length);
            createDiagonal(actions,pos.add(pos.getX()*length,-pos.getY()*length),action);
        }
    }

    private void createDiagonal(ActionMap actions, final Pos pos, Action action){
        final int x = pos.getX();
        final int y = pos.getY();
        if(Math.abs(x)!=Math.abs(y)){
            if(x==0 && y==0){
                return;
            }
        }

        Pos walkPos = new Pos(0,0);
        for(int i=0;i<Math.max(Math.abs(x),Math.abs(y));i++){
            final int dx = pos.getX()!=0?pos.getX()/Math.abs(pos.getX()):0;
            final int dy = pos.getY()!=0?pos.getY()/Math.abs(pos.getY()):0;
            //System.out.println("added walkaction: " + dx + " , " + dy);
            walkPos = walkPos.add(dx,dy);
            actions.put(walkPos, action);
        }
    }
}
