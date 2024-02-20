package com.freechazz.network.DTO.game.server.log;

import java.util.ArrayList;

public class TurnLogDTO {

    private ArrayList<LogMessageDTO> logMessages = new ArrayList<>();


    public TurnLogDTO() {
    }


    public void addLogMessage(String message) {
        logMessages.add(new LogMessageDTO(message));
    }

    public void addLogMessages(ArrayList<LogMessageDTO> logMessages) {
        this.logMessages.addAll(logMessages);
    }

    public ArrayList<LogMessageDTO> getLogMessages() {
        return logMessages;
    }
}
