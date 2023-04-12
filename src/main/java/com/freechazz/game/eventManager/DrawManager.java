package com.freechazz.game.eventManager;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class DrawManager {

    private static DrawManager instance = null;

    private ArrayList<DrawEvents> draws = new ArrayList<>();
    private DrawManager() {
    }

    public static DrawManager getInstance() {
        if (instance == null) {
            instance = new DrawManager();
        }
        return instance;
    }

    public void addEvent(Event event) {
        //log.info("Event added: " + event.getClass().getSimpleName());
        getLastDraw().addEvent(event);
    }

    public void addDraw() {
        draws.add(new DrawEvents());
    }

    public DrawEvents getLastDraw(){
        if(draws.size() > 0){
            return draws.get(draws.size() - 1);
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
