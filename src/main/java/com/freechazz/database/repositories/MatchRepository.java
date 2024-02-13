package com.freechazz.database.repositories;

import com.freechazz.database.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {
    List<MatchEntity> findByUser1UuidOrUser2Uuid(UUID userId, UUID userId1);

    List<MatchEntity> findByWatchUserUuid(UUID userId);
}

