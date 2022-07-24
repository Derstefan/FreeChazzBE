package com.freechess.server.repository;

import com.freechess.server.DTO.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Long> {
}
