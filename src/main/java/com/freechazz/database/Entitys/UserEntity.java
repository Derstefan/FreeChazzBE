package com.freechazz.database.Entitys;

import java.util.Set;

public class UserEntity {

    private long uuid;

    private String username;

    private Set<FormationEntity> formations;

    private Set<PieceEntity> ownPieces;

}
