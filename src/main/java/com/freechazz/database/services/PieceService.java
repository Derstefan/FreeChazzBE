package com.freechazz.database.services;

import com.freechazz.database.repositories.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PieceService {

    private final PieceRepository pieceRepository;

    @Autowired
    public PieceService(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

}

