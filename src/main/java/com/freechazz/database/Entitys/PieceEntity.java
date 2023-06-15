package com.freechazz.database.Entitys;

import com.freechazz.game.player.User;

import java.util.Set;
import java.util.UUID;

public class PieceEntity {

    private UUID uuid;

    private User user;
    private Set<FormationEntity> formations;
}
