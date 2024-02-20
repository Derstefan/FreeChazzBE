package com.freechazz.network.DTO.game.server.log;

public class LogMessageDTO {

    private String message;
    //private Date timestamp;

    public LogMessageDTO(String message) {
        this.message = message;
        // this.timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

 /*   public Date getTimestamp() {
        return timestamp;
    }*/
}
