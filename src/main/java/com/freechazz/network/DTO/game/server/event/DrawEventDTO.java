package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.eventManager.DrawEvent;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.events.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class DrawEventDTO {
    private ArrayList<EventDTO> events;

    public DrawEventDTO(DrawEvent drawEvent){
        events = new ArrayList<>();
        for (Event event : drawEvent.getEvents()) {
            log.info("event: " + event.getType());
            if(event instanceof ChangeOwnerEvent){
                events.add(new ChangeOwnerEventDTO((ChangeOwnerEvent)event));
            } else if(event instanceof ChangeTypeEvent){
                events.add(new ChangeTypeEventDTO((ChangeTypeEvent)event));
            }else if(event instanceof DestroyEvent){
                events.add(new DestroyEventDTO((DestroyEvent)event));
            }else if(event instanceof MoveEvent){
                events.add(new MoveEventDTO((MoveEvent)event));
            }else if(event instanceof SwapEvent){
                events.add(new SwapEventDTO((SwapEvent)event));
            }else if(event instanceof ActEvent){
                events.add(new ActEventDTO((ActEvent)event));
            }else {
                //not known
            }
        }
    }
    public ArrayList<EventDTO> getEvents(){
        return events;
    }
}
