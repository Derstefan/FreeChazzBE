package com.freechazz.game.eventManager;

import java.util.ArrayList;

public class DrawEvents {

    private ArrayList<Event> events;

    public DrawEvents(){
        events = new ArrayList<>();
    }

    public void addEvent(Event event){
        events.add(event);
    }

    public Event getLastEvent(){
        if(events.size() > 0){
            return events.get(events.size() - 1);
        }
        return null;
    }

    public void removeLastEvent(){
        if(events.size() > 0){
            events.remove(events.size() - 1);
        }
    }

    public int getEventCount(){
        return events.size();
    }
}
