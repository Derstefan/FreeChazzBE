package com.freechazz.game.eventManager;

import com.freechazz.game.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class MatchHistory {

    private ArrayList<HistoryState> historyStates = new ArrayList<>();

    private boolean isBotCopy = false;

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

    public DrawEvent getLastDraw() {
        HistoryState lastState = getLastState();
        // if (lastState == null) {
        //     throw new IllegalStateException("No state found");
        // }
        return getLastState().getDrawEvent();
    }

    public DrawEvent getDraw(int index) {
        return getHistoryState(index).getDrawEvent();
    }

    public void removeLastState() {
        if (historyStates.size() > 0) {
            historyStates.remove(historyStates.size() - 1);
        }
    }

    public HistoryState getLastState() {
        if (historyStates.size() > 0) {
            return historyStates.get(historyStates.size() - 1);
        }
        return null;
    }

    public HistoryState getHistoryState(int index) {
        // log.info("getHistoryState: " + index + " /  " + historyStates.size());
        if (historyStates.size() > index) {
            return historyStates.get(index);
        }
        return null;
    }

    public int getSize() {
        return historyStates.size();
    }

    public void setBotCopy(boolean botCopy) {
        isBotCopy = botCopy;
    }

    public boolean isBotCopy() {
        return isBotCopy;
    }

    //copy this object and return it
    public MatchHistory copy() {

        MatchHistory copy = new MatchHistory();
        copy.addState(); // add firststate
        copy.getLastState().setPiecesByDTOS(getHistoryState(0).getPieceDTOs());
        for (int i = 0; i < historyStates.size(); i++) {
            HistoryState state = historyStates.get(i);
            HistoryState copyState = copy.getHistoryState(i);
            // copyState.getDrawEvent().setEvents(state.getDrawEvent().getEvents());
        }
        copy.setBotCopy(true);
        return copy;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < historyStates.size(); i++) {
            sb.append("State " + i + ":");
            sb.append(historyStates.get(i).toString());
        }


        return sb.toString();
    }


    public void reconnectEvents(ArrayList<Piece> pieces) {
        for (int i = 0; i < historyStates.size(); i++) {
            HistoryState state = historyStates.get(i);
            state.getDrawEvent().reconnectEvent(pieces);
        }
    }

    public int getLastMatchStateIndexWithPieces(int turn) {
        for (int i = turn; i >= 0; i--) {
            HistoryState state = historyStates.get(i);
            if (state.getPieceDTOs().size() > 0) {
                return i;
            }
        }
        log.info("UpdateDataDTO: " + turn + " -1");
        return -1;
    }
}
