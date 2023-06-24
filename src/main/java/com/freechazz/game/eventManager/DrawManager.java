package com.freechazz.game.eventManager;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class DrawManager {

    private ArrayList<DrawEvent> draws = new ArrayList<>();

    public void addEvent(Event event) {
        //log.info("Event added: " + event.getClass().getSimpleName());
        getLastDraw().addEvent(event);
    }

    public void addDraw() {
        draws.add(new DrawEvent());
    }

    public DrawEvent getLastDraw(){
        if(draws.size() > 0){
            return draws.get(draws.size() - 1);
        }
        return null;
    }

    public DrawEvent getDraw(int index){
        if(draws.size() > index){
            return draws.get(index);
        }
        return null;
    }

    public void removeLastDraw(){
        if(draws.size() > 0){
            draws.remove(draws.size() - 1);
        }
    }

    public int getDrawCount(){
        return draws.size();
    }
}
