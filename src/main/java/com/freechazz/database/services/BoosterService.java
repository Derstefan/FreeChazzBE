package com.freechazz.database.services;

import com.freechazz.database.repositories.BoosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoosterService {

    private final BoosterRepository boosterRepository;

    @Autowired
    public BoosterService(BoosterRepository boosterRepository) {
        this.boosterRepository = boosterRepository;
    }

}

// Similar service classes for FormationEntity, PieceEntity, and BoosterEntity
