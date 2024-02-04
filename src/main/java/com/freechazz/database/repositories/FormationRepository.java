package com.freechazz.database.repositories;

import com.freechazz.database.entities.FormationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FormationRepository extends JpaRepository<FormationEntity, UUID> {
    // You can add custom query methods if needed
}

// Similar repository interfaces for FormationEntity, PieceEntity, and BoosterEntity
