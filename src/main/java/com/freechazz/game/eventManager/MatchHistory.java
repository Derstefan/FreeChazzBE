package com.freechazz.game.eventManager;

import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class MatchHistory {

    private ArrayList<HistoryState> historyStates = new ArrayList<>();

    public MatchHistory(ArrayList<Piece> initialPieces) {
        addState(); // add firststate
        getLastState().setPieceDTOs(initialPieces);
    }

    private MatchHistory() {

    }

    public void addEvent(Event event) {
        //log.info("Event added: " + event.getClass().getSimpleName());
        getLastDraw().addEvent(event);
    }

    public void addState() {
        historyStates.add(new HistoryState());
    }

    public DrawEvent getLastDraw(){
        return getLastState().getDrawEvent();
    }

    public DrawEvent getDraw(int index){
        return getHistoryState(index).getDrawEvent();
    }

    public void removeLastState(){
        if(historyStates.size() > 0){
            historyStates.remove(historyStates.size() - 1);
        }
    }

    public HistoryState getLastState() {
        if(historyStates.size() > 0){
            return historyStates.get(historyStates.size() - 1);
        }
        return null;
    }

    public HistoryState getHistoryState(int index){
        if(historyStates.size() > index){
            return historyStates.get(index);
        }
        return null;
    }

    //copy this object and return it
    public MatchHistory copy(){

        MatchHistory copy = new MatchHistory();
        copy.addState(); // add firststate
        copy.getLastState().setPiecesByDTOS(getHistoryState(0).getPieceDTOs());
        for (int i = 0; i < historyStates.size(); i++) {
            HistoryState state = historyStates.get(i);
            HistoryState copyState = copy.getHistoryState(i);
           // copyState.getDrawEvent().setEvents(state.getDrawEvent().getEvents());
        }
        return copy;
    }
}
