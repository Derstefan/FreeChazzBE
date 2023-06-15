package com.freechazz.database.Entitys;

import java.util.Set;
import java.util.UUID;

public class UserEntity {

    private UUID uuid;

    private String username;

    private int money;

    private Set<FormationEntity> formations;

    private Set<PieceEntity> ownPieces;

    private Set<BoosterEntity> boosters;

}
