package com.freechazz.database.Entitys;

import com.freechazz.game.player.User;

import java.util.Set;

public class PieceEntity {

    private long uuid;

    private User user;
    private Set<FormationEntity> formations;
}
