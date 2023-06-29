package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.eventManager.DrawEvent;
import com.freechazz.game.eventManager.Event;

import java.util.ArrayList;

public class DrawDTO {
    private ArrayList<Event> events;

    public DrawDTO(DrawEvent drawEvent){
        events = drawEvent.getEvents();
    }
    public ArrayList<Event> getEvents(){
        return events;
    }
}
