package com.freechazz.database.Entitys;


import com.freechazz.game.player.User;
import com.freechazz.game.core.ESize;

import java.util.HashSet;
import java.util.UUID;

public class FormationEntity {
    private UUID uuid;
    private ESize size;
    private User user;

    private HashSet<PiecePosition> pieces;
}
