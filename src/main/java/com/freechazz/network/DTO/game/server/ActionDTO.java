package com.freechazz.network.DTO.game.server;

import com.freechazz.game.core.Pos;

public class ActionDTO {
    public Pos vec;
    public String type;

    public ActionDTO(Pos vec, String type) {
        this.vec = vec;
        this.type = type;
    }
}