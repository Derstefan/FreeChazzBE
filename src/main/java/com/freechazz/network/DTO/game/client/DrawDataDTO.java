package com.freechazz.network.DTO.game.client;

import com.freechazz.game.core.Pos;

public class DrawDataDTO {
    Pos fromPos;
    Pos toPos;

    public DrawDataDTO(Pos fromPos, Pos toPos) {
        this.fromPos = fromPos;
        this.toPos = toPos;
    }

    public Pos getFromPos() {
        return fromPos;
    }

    public void setFromPos(Pos fromPos) {
        this.fromPos = fromPos;
    }

    public Pos getToPos() {
        return toPos;
    }

    public void setToPos(Pos toPos) {
        this.toPos = toPos;
    }

    public String toString(){
        return fromPos.toString() + " -> " + toPos.toString();
    }
}
