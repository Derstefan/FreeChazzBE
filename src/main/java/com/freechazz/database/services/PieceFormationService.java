package com.freechazz.database.services;

import com.freechazz.database.repositories.PieceFormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PieceFormationService {

    private final PieceFormationRepository pieceFormationRepository;

    @Autowired
    public PieceFormationService(PieceFormationRepository pieceFormationRepository) {
        this.pieceFormationRepository = pieceFormationRepository;
    }

}
