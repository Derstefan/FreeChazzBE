package com.freechazz.network.DTO.game.server.event;

import com.freechazz.game.eventManager.DrawEvent;
import com.freechazz.game.eventManager.Event;
import com.freechazz.game.eventManager.events.*;
import com.freechazz.network.DTO.game.server.event.archiv.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;


/*
@Slf4j
public class DrawEventDTO {
    private ArrayList<EventDTO> events;

    public DrawEventDTO(DrawEvent drawEvent){
        events = new ArrayList<>();
        for (Event event : drawEvent.getEvents()) {
            EventDTO eventDTO = new EventDTO(event);
            if(event instanceof ChangeOwnerEvent){
                eventDTO.setPieceId(((ChangeOwnerEvent)event).getPiece().getPieceId());
            } else if(event instanceof ChangeTypeEvent){
                eventDTO.setPieceId(((ChangeTypeEvent)event).getPiece().getPieceId());
                eventDTO.setNewPieceTypeId(((ChangeTypeEvent)event).getNewType().getPieceTypeId());
                eventDTO.setOldPieceTypeId(((ChangeTypeEvent)event).getOldType().getPieceTypeId());
            }else if(event instanceof DestroyEvent){
                eventDTO.setPieceId(((DestroyEvent)event).getPiece().getPieceId());
                eventDTO.setPos(((DestroyEvent)event).getPos());
            }else if(event instanceof MoveEvent){
                eventDTO.setPieceId(((MoveEvent)event).getPiece().getPieceId());
                eventDTO.setFromPos(((MoveEvent)event).getFromPos());
                eventDTO.setToPos(((MoveEvent)event).getToPos());
            }else if(event instanceof SwapEvent){
                eventDTO.setFromPos(((MoveEvent)event).getFromPos());
                eventDTO.setToPos(((MoveEvent)event).getToPos());
            }else if(event instanceof ActEvent){
                eventDTO.setActName(((ActEvent)event).getActName());
                eventDTO.setFromPos(((MoveEvent)event).getFromPos());
                eventDTO.setToPos(((MoveEvent)event).getToPos());
            }else {
                //not known
            }
            events.add(eventDTO);
            log.info("EventDTO: " + eventDTO.toString());
        }
    }
    public ArrayList<EventDTO> getEvents(){
        return events;
    }
}

*/

@Slf4j
public class DrawEventDTO {
    private ArrayList<EventDTO> events;

    public DrawEventDTO(DrawEvent drawEvent){
        events = new ArrayList<>();
        for (Event event : drawEvent.getEvents()) {
            if(event instanceof ChangeOwnerEvent){
                events.add(new ChangeOwnerEventDTO((ChangeOwnerEvent)event));
            } else if(event instanceof ChangeTypeEvent){
                events.add(new ChangeTypeEventDTO((ChangeTypeEvent)event));
            }else if(event instanceof DestroyEvent){
                events.add(new DestroyEventDTO((DestroyEvent)event));
            }else if(event instanceof MoveEvent){
                events.add(new MoveEventDTO((MoveEvent)event));
            }else if(event instanceof MoveAndDestroyEvent){
                events.add(new MoveAndDestroyEventDTO((MoveAndDestroyEvent)event));
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
